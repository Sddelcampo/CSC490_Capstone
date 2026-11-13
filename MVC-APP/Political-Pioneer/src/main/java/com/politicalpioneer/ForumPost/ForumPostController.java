package com.politicalpioneer.ForumPost;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.politicalpioneer.AuthenticatedNameModel;
import com.politicalpioneer.Comment.Comment;
import com.politicalpioneer.Comment.CommentService;
import com.politicalpioneer.Party.Party;
import com.politicalpioneer.Party.PartyService;
import com.politicalpioneer.PartyMember.PartyMemberService;
import com.politicalpioneer.ResourceNotFoundException;
import com.politicalpioneer.User.User;
import com.politicalpioneer.User.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForumPostController {
    
    private final ForumPostService forumService;
    private final CommentService commentService;
    private final PartyService partyService;
    private final UserService userService;
    private final PartyMemberService partyMemberService;

    public ForumPostController(
            ForumPostService forumService,
            CommentService commentService,
            PartyService partyService,
            UserService userService,
            PartyMemberService partyMemberService) {
        this.forumService = forumService;
        this.commentService = commentService;
        this.partyService = partyService;
        this.userService = userService;
        this.partyMemberService = partyMemberService;
    }

    @GetMapping("/forum")
    public String showForum(Model model, Authentication authentication) {
        boolean isOwner = isOwner(authentication);
        User currentUser = getAuthenticatedUser(authentication);
        Optional<Party> ownerParty = isOwner
                ? partyService.findPartyByUserId(currentUser.getUserId())
                : Optional.empty();
        Set<Long> associatedPartyIds = getAssociatedPartyIds(currentUser, isOwner, ownerParty);
        List<Map<String, Object>> posts = forumService.getAllForumPosts().stream()
                .filter(post -> !Boolean.FALSE.equals(post.getVisiblity()))
                .filter(post -> associatedPartyIds.contains(post.getParty().getPartyId()))
                .sorted(Comparator.comparing(
                        ForumPost::getCreatedTime,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .map(post -> toForumListView(post, authentication))
                .toList();

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("posts", posts);
        model.addAttribute("partyGroups", groupPostsByParty(posts));
        model.addAttribute("postCount", posts.size());

        ownerParty.ifPresent(party -> {
            model.addAttribute("ownerPartyId", party.getPartyId());
            model.addAttribute("ownerPartyName", party.getPartyName());
            model.addAttribute("canCreatePost", true);
        });
        return "forum";
    }

    private List<Map<String, Object>> groupPostsByParty(List<Map<String, Object>> posts) {
        Map<Long, List<Map<String, Object>>> postsByParty = posts.stream()
                .collect(Collectors.groupingBy(
                        post -> (Long) post.get("partyId"),
                        LinkedHashMap::new,
                        Collectors.toList()));

        return postsByParty.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> group = new LinkedHashMap<>();
                    Map<String, Object> firstPost = entry.getValue().get(0);
                    group.put("partyId", entry.getKey());
                    group.put("partyName", firstPost.get("partyName"));
                    group.put("posts", entry.getValue());
                    return group;
                })
                .toList();
    }

    @GetMapping("/createFP")
    public String showCreateForumPost(Model model, Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());
        Optional<Party> party = partyService.findPartyByUserId(user.getUserId());
        if (party.isEmpty()) {
            return "redirect:/createParty";
        }

        model.addAttribute("partyName", party.get().getPartyName());
        model.addAttribute("authorName", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("recentPosts", forumService.getRecentForumPostsByPartyIdAndOwnerId(
                        party.get().getPartyId(), user.getUserId()).stream()
                .map(this::toRecentPostView)
                .toList());
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "createFP";
    }

    @PostMapping("/forum/post")
    public String createForumPost(
            @RequestParam String title,
            @RequestParam String content,
            Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());
        Party party = partyService.findPartyByUserId(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "A party must exist before its owner can create a forum post"));

        ForumPost post = new ForumPost(null, party, user, title, content, LocalDateTime.now(), true);
        ForumPost savedPost = forumService.addForumPostBy(post);
        return "redirect:/forum/post/" + savedPost.getFormPostId();
    }

    @GetMapping("/forum/post/{id}")
    public String showForumPost(
            @PathVariable("id") Long id,
            Model model,

            Authentication authentication,
            @RequestParam(defaultValue = "false") boolean edit) {
        ForumPost forumPost = forumService.getForumPostById(id);
        ensureCanViewPost(forumPost, authentication);
        boolean isOwner = isOwner(authentication);
        boolean canEditPost = canEditPost(forumPost, authentication);
        model.addAttribute("post", toPostView(forumPost));
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("canEditPost", canEditPost);
        model.addAttribute("editPostOpen", canEditPost && edit);
        model.addAttribute("comments", commentService.getCommentsByPostId(id).stream()
                .sorted(Comparator.comparing(Comment::createdDate,
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .map(this::toCommentView)
                .toList());
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "forumpost";
    }

    @PostMapping("/forum/post/{id}/comment")
    public String addComment(
            @PathVariable("id") Long id,
            @RequestParam(required = false) String body,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        ForumPost forumPost = forumService.getForumPostById(id);
        ensureCanViewPost(forumPost, authentication);
        String normalizedBody = body == null ? "" : body.trim();
        if (normalizedBody.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Enter a comment before posting.");
            redirectAttributes.addFlashAttribute("commentBody", body == null ? "" : body);
            return "redirect:/forum/post/" + id;
        }
        if (normalizedBody.length() > 2000) {
            redirectAttributes.addFlashAttribute("error", "Comments must be 2,000 characters or fewer.");
            redirectAttributes.addFlashAttribute("commentBody", body);
            return "redirect:/forum/post/" + id;
        }
        User commenter = userService.getUserByUserName(authentication.getName());
        Comment comment = new Comment(
                null,
                forumPost,
                commenter,
                normalizedBody,
                LocalDateTime.now(),
                "active",
                true);
        commentService.addCommentBy(comment);
        redirectAttributes.addFlashAttribute("commentPosted", true);
        return "redirect:/forum/post/" + id;
    }

    @PostMapping("/forum/post/{id}/edit")
    public String updateForumPost(
            @PathVariable("id") Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        ForumPost forumPost = getEditablePost(id, authentication);

        String normalizedTitle = title == null ? "" : title.trim();
        String normalizedContent = content == null ? "" : content.trim();
        String error = validateForumPost(normalizedTitle, normalizedContent);
        if (error != null) {
            redirectAttributes.addFlashAttribute("editError", error);
            redirectAttributes.addFlashAttribute("editTitle", title == null ? "" : title);
            redirectAttributes.addFlashAttribute("editContent", content == null ? "" : content);
            return "redirect:/forum/post/" + id;
        }

        ForumPost updatedPost = new ForumPost(
                id,
                forumPost.getParty(),
                forumPost.getUser(),
                normalizedTitle,
                normalizedContent,
                forumPost.getCreatedTime(),
                forumPost.getVisiblity());
        forumService.updateForumPost(id, updatedPost);
        return "redirect:/forum/post/" + id;
    }

    @GetMapping("/fp")
    public ResponseEntity<List<ForumPost>> getAllFP(Authentication authentication) {
        List<ForumPost> fp = getVisiblePostsFor(authentication);
        return ResponseEntity.ok(fp);
    }

    @GetMapping("/fp/{id}")
    public ResponseEntity<ForumPost> getFPById(
            @PathVariable("id") Long id,
            Authentication authentication) {
        ForumPost fp = forumService.getForumPostById(id);
        ensureCanViewPost(fp, authentication);
        return ResponseEntity.ok(fp);
    }

    @GetMapping("/fp/user/{id}")
    public ResponseEntity<List<ForumPost>> getFPByUserId(
            @PathVariable("id") Long id,
            Authentication authentication) {
        Set<Long> associatedPartyIds = getAssociatedPartyIds(authentication);
        List<ForumPost> fp = forumService.getForumPostByUserId(id).stream()
                .filter(post -> !Boolean.FALSE.equals(post.getVisiblity()))
                .filter(post -> associatedPartyIds.contains(post.getParty().getPartyId()))
                .toList();
        return ResponseEntity.ok(fp);
    }

    @PostMapping("/fp")
    public ResponseEntity<ForumPost> addFP(@RequestBody ForumPost newFp) {
        forumService.addForumPostBy(newFp);
        return ResponseEntity.ok(newFp);
    }

    @PutMapping("/fp/{id}")
    public ResponseEntity<ForumPost> updateFPById(
            @PathVariable("id") Long id,
            @RequestBody ForumPost fp,
            Authentication authentication) {
        getEditablePost(id, authentication);
        ForumPost newFP = forumService.updateForumPost(id, fp);
        return ResponseEntity.ok(newFP);
    }

    @DeleteMapping("/fp/{id}")
    public ResponseEntity<Void> deleteFPById(@PathVariable("id") Long id) {
        forumService.deleteForumPostById(id);
        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> toPostView(ForumPost forumPost) {
        Map<String, Object> post = new LinkedHashMap<>();
        post.put("id", forumPost.getFormPostId());
        post.put("partyId", forumPost.getParty().getPartyId());
        post.put("partyName", forumPost.getParty().getPartyName());
        post.put("title", forumPost.getTitle());
        post.put("body", forumPost.getContent());
        post.put("authorName", forumPost.getUser().getFirstName() + " " + forumPost.getUser().getLastName());
        post.put("createdAt", formatDateTime(forumPost.getCreatedTime()));
        return post;
    }

    private Map<String, Object> toForumListView(
            ForumPost forumPost,
            Authentication authentication) {
        Map<String, Object> post = new LinkedHashMap<>();
        String content = forumPost.getContent() == null ? "" : forumPost.getContent().trim();
        String excerpt = content.length() > 200 ? content.substring(0, 200).stripTrailing() + "..." : content;

        post.put("id", forumPost.getFormPostId());
        post.put("partyId", forumPost.getParty().getPartyId());
        post.put("partyName", forumPost.getParty().getPartyName());
        post.put("title", forumPost.getTitle());
        post.put("excerpt", excerpt);
        post.put("authorName", forumPost.getUser().getFirstName() + " " + forumPost.getUser().getLastName());
        post.put("createdAt", formatDateTime(forumPost.getCreatedTime()));
        post.put("commentCount", commentService.getCommentsByPostId(forumPost.getFormPostId()).size());
        post.put("canEdit", canEditPost(forumPost, authentication));
        return post;
    }

    private Map<String, Object> toCommentView(Comment comment) {
        Map<String, Object> commentView = new LinkedHashMap<>();
        commentView.put("id", comment.getCommentId());
        commentView.put("authorName", comment.getUser().getFirstName() + " " + comment.getUser().getLastName());
        commentView.put("body", comment.getContent());
        commentView.put("createdAt", formatDateTime(comment.createdDate()));
        return commentView;
    }

    private Map<String, Object> toRecentPostView(ForumPost forumPost) {
        Map<String, Object> post = new LinkedHashMap<>();
        post.put("id", forumPost.getFormPostId());
        post.put("title", forumPost.getTitle());
        post.put("authorName", forumPost.getUser().getUserName());
        post.put("createdAt", formatDateTime(forumPost.getCreatedTime()));
        return post;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a"));
    }

    private boolean isOwner(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("owner"));
    }

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null) {
            throw new AccessDeniedException("Sign in to view forum posts");
        }
        return userService.getUserByUserName(authentication.getName());
    }

    private Set<Long> getAssociatedPartyIds(Authentication authentication) {
        boolean owner = isOwner(authentication);
        User user = getAuthenticatedUser(authentication);
        Optional<Party> ownerParty = owner
                ? partyService.findPartyByUserId(user.getUserId())
                : Optional.empty();
        return getAssociatedPartyIds(user, owner, ownerParty);
    }

    private Set<Long> getAssociatedPartyIds(
            User user,
            boolean owner,
            Optional<Party> ownerParty) {
        if (owner) {
            return ownerParty.map(party -> Set.of(party.getPartyId())).orElseGet(Set::of);
        }
        return partyMemberService.getPMByUserId(user.getUserId()).stream()
                .map(member -> member.getParty().getPartyId())
                .collect(Collectors.toSet());
    }

    private List<ForumPost> getVisiblePostsFor(Authentication authentication) {
        Set<Long> associatedPartyIds = getAssociatedPartyIds(authentication);
        return forumService.getAllForumPosts().stream()
                .filter(post -> !Boolean.FALSE.equals(post.getVisiblity()))
                .filter(post -> associatedPartyIds.contains(post.getParty().getPartyId()))
                .toList();
    }

    private void ensureCanViewPost(ForumPost post, Authentication authentication) {
        if (Boolean.FALSE.equals(post.getVisiblity())
                || !getAssociatedPartyIds(authentication).contains(post.getParty().getPartyId())) {
            throw new AccessDeniedException("You cannot view this forum post");
        }
    }

    private boolean canEditPost(ForumPost post, Authentication authentication) {
        return isOwner(authentication)
                && authentication.getName().equals(post.getUser().getUserName());
    }

    private ForumPost getEditablePost(Long id, Authentication authentication) {
        ForumPost forumPost = forumService.getForumPostById(id);
        if (!getAssociatedPartyIds(authentication).contains(forumPost.getParty().getPartyId())) {
            throw new AccessDeniedException("You cannot edit a post outside your party");
        }
        if (!isOwner(authentication)) {
            throw new AccessDeniedException("Only owners can edit forum posts");
        }

        User owner = userService.getUserByUserName(authentication.getName());
        if (!owner.getUserId().equals(forumPost.getUser().getUserId())) {
            throw new AccessDeniedException("Owners can only edit their own forum posts");
        }
        return forumPost;
    }

    private String validateForumPost(String title, String content) {
        if (title.isEmpty()) {
            return "Enter a title for the forum post.";
        }
        if (title.length() > 255) {
            return "Post titles must be 255 characters or fewer.";
        }
        if (content.isEmpty()) {
            return "Enter the forum post content.";
        }
        if (content.length() > 10000) {
            return "Forum posts must be 10,000 characters or fewer.";
        }
        return null;
    }
}
