package com.politicalpioneer.ForumPost;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.politicalpioneer.Comment.Comment;
import com.politicalpioneer.Comment.CommentService;
import com.politicalpioneer.Party.Party;
import com.politicalpioneer.Party.PartyService;
import com.politicalpioneer.ResourceNotFoundException;
import com.politicalpioneer.User.User;
import com.politicalpioneer.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    public ForumPostController(
            ForumPostService forumService,
            CommentService commentService,
            PartyService partyService,
            UserService userService) {
        this.forumService = forumService;
        this.commentService = commentService;
        this.partyService = partyService;
        this.userService = userService;
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
    public String showForumPost(@PathVariable("id") Long id, Model model) {
        ForumPost forumPost = forumService.getForumPostById(id);
        model.addAttribute("post", toPostView(forumPost));
        model.addAttribute("comments", commentService.getCommentsByPostId(id).stream()
                .sorted(Comparator.comparing(Comment::createdDate,
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .map(this::toCommentView)
                .toList());
        return "forumpost";
    }

    @GetMapping("/fp")
    public ResponseEntity<List<ForumPost>> getAllFP() {
        List<ForumPost> fp = forumService.getAllForumPosts();
        return ResponseEntity.ok(fp);
    }

    @GetMapping("/fp/{id}")
    public ResponseEntity<ForumPost> getFPById(@PathVariable("id") Long id) {
        ForumPost fp = forumService.getForumPostById(id);
        return ResponseEntity.ok(fp);
    }

    @GetMapping("/fp/user/{id}")
    public ResponseEntity<List<ForumPost>> getFPByUserId(@PathVariable("id") Long id) {
        List<ForumPost> fp =forumService.getForumPostByUserId(id);
        return ResponseEntity.ok(fp);
    }

    @PostMapping("/fp")
    public ResponseEntity<ForumPost> addFP(@RequestBody ForumPost newFp) {
        forumService.addForumPostBy(newFp);
        return ResponseEntity.ok(newFp);
    }

    @PutMapping("/fp/{id}")
    public ResponseEntity<ForumPost> updateFPById(@PathVariable("id") Long id, @RequestBody ForumPost fp) {
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
}
