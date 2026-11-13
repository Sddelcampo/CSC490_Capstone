package com.politicalpioneer.Party;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.politicalpioneer.AuthenticatedNameModel;
import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.Announcement.Announcement;
import com.politicalpioneer.Announcement.AnnouncementService;
import com.politicalpioneer.ForumPost.ForumPost;
import com.politicalpioneer.ForumPost.ForumPostService;
import com.politicalpioneer.Party.PartyController.GeoJsonPoint;
import com.politicalpioneer.Party.PartyAlignment.PartyAlignment;
import com.politicalpioneer.PartyMember.PartyMember;
import com.politicalpioneer.PartyMember.PartyMemberService;
import com.politicalpioneer.User.User;
import com.politicalpioneer.User.UserService;
import org.springframework.ui.Model;

// import ch.qos.logback.core.model.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.web.csrf.CsrfToken;
import jakarta.servlet.http.HttpServletRequest;

import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class PartyController {


    public record GeoJsonPoint(String type, List<Double> coordinates) {}
    private User user;
    private PartyMember pm;
    

    public record PartyResponse(
        Long partyId,
        String partyName,
        String description,
        PartyAlignment partyAlignment,
        String status,
        GeoJsonPoint location,
        List<PartyMember> partyMembers,
        List<Announcement> ann,
        List<ForumPost> forumPost
    ) {}

    public record PartyDistanceResponse(
        Long partyId,
        String partyName,
        String description,
        PartyAlignment partyAlignment,
        String status,
        GeoJsonPoint location,
        List<PartyMember> partyMembers,
        List<Announcement> ann,
        List<ForumPost> forumPost,
        Double distanceMeters
    ) {}

    public record PartyCreateRequest(
        String partyName,
        String description,
        String status,
        Double longitude,
        Double latitude
        
    ) {}

    private final PartyService partyService;
    private final UserService userService;
    private final PartyMemberService memService;
    private final ForumPostService forumPostService;
    private final AnnouncementService announcementService;

    public PartyController(
            PartyService partyService,
            UserService userService,
            PartyMemberService memService,
            ForumPostService forumPostService,
            AnnouncementService announcementService) {
        this.partyService = partyService;
        this.userService = userService;
        this.memService = memService;
        this.forumPostService = forumPostService;
        this.announcementService = announcementService;
    }

    @GetMapping("/party")
    public ResponseEntity<List<PartyResponse>> getAllParties() {
        List<Party> parties = partyService.getAllParties();
        return ResponseEntity.ok(parties.stream().map(this::toResponse).collect(Collectors.toList()));
    }

  
    
    @GetMapping("/party/status/{status}")
    public ResponseEntity<List<PartyResponse>> getPartyByStatus(@PathVariable("status") String status) {
        List<Party> party = partyService.getPartyByStatus(status);
        return ResponseEntity.ok(party.stream().map(this::toResponse).collect(Collectors.toList()));
    }


    @GetMapping("/createParty")
    public String createParty(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("admin"));
        boolean isOwner = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("owner"));
        boolean isUser = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("user"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isUser", isUser);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "createParty";
    }

    @PostMapping("/makeParty")
    public Object makeParty( @ModelAttribute PartyController.PartyCreateRequest party, Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());
        Long userId = user.getUserId();
        partyService.addParty(party, userId);
        return "redirect:/home";
    }

   
    
    @GetMapping("/partyDetail/{id}")
    public String partyDetail(
            @PathVariable("id") Long id,
            Model model,
            Authentication authentication) {
        Party party = partyService.getPartyById(id);
        User partyOwner = party.getUser();
        String lastName = partyOwner.getLastName();
        List<Map<String, Object>> announcements = party.getAnn().stream()
                .map(this::toAnnouncementView)
                .toList();
        boolean isUser = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("user"));
        boolean isOwner = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("owner"));
        User currentUser = isUser || isOwner
                ? userService.getUserByUserName(authentication.getName())
                : null;
        boolean isMember = isUser && memService.getPMByUserId(currentUser.getUserId()).stream()
                .anyMatch(member -> id.equals(member.getPartyId()));
        boolean canViewForumPosts = isMember || (isOwner
                && currentUser.getUserId().equals(partyOwner.getUserId()));
        List<ForumPost> visibleForumPosts = canViewForumPosts
                ? party.getForumPost().stream()
                        .filter(post -> !Boolean.FALSE.equals(post.getVisiblity()))
                        .sorted(Comparator.comparing(
                                ForumPost::getCreatedTime,
                                Comparator.nullsLast(Comparator.reverseOrder())))
                        .toList()
                : List.of();
        List<Map<String, Object>> recentForumPosts = visibleForumPosts.stream()
                .limit(4)
                .map(this::toPartyForumPostView)
                .toList();

        model.addAttribute("lastName", lastName);
        model.addAttribute("announcements", announcements);
        model.addAttribute("announcementCount", announcements.size());
        model.addAttribute("isMember", isMember);
        model.addAttribute("canJoinParty", isUser && !isMember);
        model.addAttribute("canViewForumPosts", canViewForumPosts);
        model.addAttribute("forumPosts", recentForumPosts);
        model.addAttribute("forumPostCount", visibleForumPosts.size());
        // model.addAttribute("owner", partyOwner);
      
        model.addAttribute("party", party);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        return "partyPage";
    }


@PostMapping("/joinParty/{id}")
public String joinParty(@PathVariable("id") Long id, Authentication authentication) {
    User user = userService.getUserByUserName(authentication.getName());
    Party party = partyService.getPartyById(id);
    try {
        memService.addPartyMember(user, party);

    } catch (BadRequestException e) {
        System.out.println("User can not join a party more than once");
    }
    return "redirect:/partyDetail/" + party.getPartyId();
}

//I will pull just the first party for now
 @GetMapping("/partyManage") 
    public Object manageParty(Model model, Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());
        Long userId = user.getUserId();
        Optional<Party> partyResult = partyService.findPartyByUserId(userId);
        if (partyResult.isEmpty()) {
            return "redirect:/createParty";
        }
        Party party = partyResult.get();
        String partyName = party.getPartyName();
        String description = party.getDescription();
        String status = party.getStatus();
        String lastName = user.getLastName();
        List<PartyMember> pm = memService.getPMByPartyId(party.getPartyId());
        List<Map<String, Object>> forumPosts = party.getForumPost().stream()
                .map(post -> toForumPostView(post, user))
                .toList();
        List<Map<String, Object>> announcements = party.getAnn().stream()
                .map(this::toAnnouncementView)
                .toList();


       
        boolean isOwner = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("owner"));
        model.addAttribute("isOwner", isOwner);
        boolean isUser = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("user"));


        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isUser", isUser);   
        
        
        model.addAttribute("status", status);
        model.addAttribute("lastName",lastName);
        model.addAttribute("partyName", partyName);
        model.addAttribute("description", description);
        model.addAttribute("pm", pm);
        model.addAttribute("memberCount", pm.size());
        model.addAttribute("forumPosts", forumPosts);
        model.addAttribute("forumPostCount", forumPosts.size());
        model.addAttribute("hiddenForumPostCount", forumPosts.stream()
                .filter(post -> Boolean.FALSE.equals(post.get("visible")))
                .count());
        model.addAttribute("announcements", announcements);
        model.addAttribute("announcementCount", announcements.size());
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);

        
        // System.out.println(party.getPartyName());
        return "partyManage";
    }

    @GetMapping("/createannouncement")
    public String createAnnouncementForm(Model model, Authentication authentication) {
        Party ownerParty = getOwnerParty(authentication);
        addAnnouncementFormAttributes(model, ownerParty, authentication);
        return "createannouncement";
    }

    @PostMapping("/partyManage/announcements/create")
    public String createAnnouncement(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            Model model,
            Authentication authentication) {
        Party ownerParty = getOwnerParty(authentication);
        String normalizedTitle = title == null ? "" : title.trim();
        String normalizedDescription = description == null ? "" : description.trim();
        String error = validateAnnouncement(normalizedTitle, normalizedDescription);

        if (error != null) {
            addAnnouncementFormAttributes(model, ownerParty, authentication);
            model.addAttribute("announcementTitle", title == null ? "" : title);
            model.addAttribute("announcementDescription", description == null ? "" : description);
            model.addAttribute("announcementError", error);
            return "createannouncement";
        }

        Announcement announcement = new Announcement(
                null,
                ownerParty,
                normalizedDescription,
                LocalDateTime.now(),
                normalizedTitle);
        announcementService.saveAnn(announcement);
        return "redirect:/partyManage";
    }

    @GetMapping("/partyManage/announcements/{announcementId}/edit")
    public String editAnnouncementForm(
            @PathVariable Long announcementId,
            Model model,
            Authentication authentication) {
        Party ownerParty = getOwnerParty(authentication);
        Announcement announcement = getOwnedAnnouncement(announcementId, ownerParty);
        addAnnouncementFormAttributes(model, ownerParty, authentication);
        model.addAttribute("announcementId", announcement.getId());
        model.addAttribute("announcementTitle", announcement.getTitle());
        model.addAttribute("announcementDescription", announcement.getDescription());
        return "editannouncement";
    }

    @PostMapping("/partyManage/announcements/{announcementId}/edit")
    public String updateAnnouncement(
            @PathVariable Long announcementId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            Model model,
            Authentication authentication) {
        Party ownerParty = getOwnerParty(authentication);
        Announcement announcement = getOwnedAnnouncement(announcementId, ownerParty);
        String normalizedTitle = title == null ? "" : title.trim();
        String normalizedDescription = description == null ? "" : description.trim();
        String error = validateAnnouncement(normalizedTitle, normalizedDescription);

        if (error != null) {
            addAnnouncementFormAttributes(model, ownerParty, authentication);
            model.addAttribute("announcementId", announcementId);
            model.addAttribute("announcementTitle", title == null ? "" : title);
            model.addAttribute("announcementDescription", description == null ? "" : description);
            model.addAttribute("announcementError", error);
            return "editannouncement";
        }

        announcement.setTitle(normalizedTitle);
        announcement.setDescription(normalizedDescription);
        announcementService.saveAnn(announcement);
        return "redirect:/partyManage";
    }

    private String validateAnnouncement(String title, String description) {
        if (title.isEmpty()) {
            return "Enter an announcement title.";
        }
        if (title.length() > 120) {
            return "The title must be 120 characters or fewer.";
        }
        if (description.isEmpty()) {
            return "Enter an announcement message.";
        }
        if (description.length() > 5000) {
            return "The message must be 5,000 characters or fewer.";
        }
        return null;
    }

    private void addAnnouncementFormAttributes(
            Model model,
            Party party,
            Authentication authentication) {
        boolean isOwner = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("owner"));
        boolean isUser = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("user"));
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("admin"));

        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isUser", isUser);
        model.addAttribute("isAdmin", isAdmin);
        AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
        model.addAttribute("partyName", party.getPartyName());
        model.addAttribute("memberCount", memService.getPMByPartyId(party.getPartyId()).size());
    }

    private Announcement getOwnedAnnouncement(Long announcementId, Party ownerParty) {
        Announcement announcement = announcementService.getAnnById(announcementId);
        if (!ownerParty.getPartyId().equals(announcement.getParty().getPartyId())) {
            throw new AccessDeniedException("Cannot edit an announcement from another party");
        }
        return announcement;
    }

    private Map<String, Object> toForumPostView(ForumPost forumPost, User owner) {
        Map<String, Object> post = new LinkedHashMap<>();
        post.put("id", forumPost.getFormPostId());
        post.put("title", forumPost.getTitle());
        post.put("author", forumPost.getUser().getUserName());
        post.put("canEdit", owner.getUserId().equals(forumPost.getUser().getUserId()));
        post.put("createdAt", formatDate(forumPost.getCreatedTime()));
        post.put("visible", forumPost.getVisiblity());
        post.put("visibility", Boolean.TRUE.equals(forumPost.getVisiblity()) ? "visible" : "hidden");
        return post;
    }

    private Map<String, Object> toAnnouncementView(Announcement announcement) {
        Map<String, Object> announcementView = new LinkedHashMap<>();
        announcementView.put("id", announcement.getId());
        announcementView.put("title", announcement.getTitle());
        announcementView.put("description", announcement.getDescription());
        announcementView.put("createdAt", formatDate(announcement.getCreatedDate()));
        return announcementView;
    }

    private Map<String, Object> toPartyForumPostView(ForumPost forumPost) {
        Map<String, Object> postView = new LinkedHashMap<>();
        postView.put("id", forumPost.getFormPostId());
        postView.put("title", forumPost.getTitle());
        postView.put("author", forumPost.getUser().getUserName());
        postView.put("createdAt", formatDate(forumPost.getCreatedTime()));
        return postView;
    }

    private String formatDate(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
    }

    @PostMapping("/partyManage/members/{memberUserId}/remove")
    public String removePartyMember(
            @PathVariable Long memberUserId,
            Authentication authentication) {
        User owner = userService.getUserByUserName(authentication.getName());
        Party party = partyService.getPartyByUserId(owner.getUserId());
        memService.deletePartyMemberById(memberUserId, party.getPartyId());
        return "redirect:/partyManage";
    }

    @PostMapping("/partyManage/forum-posts/{postId}/delete")
    public String deleteForumPost(@PathVariable Long postId, Authentication authentication) {
        Party ownerParty = getOwnerParty(authentication);
        ForumPost forumPost = forumPostService.getForumPostById(postId);
        if (!ownerParty.getPartyId().equals(forumPost.getParty().getPartyId())) {
            throw new AccessDeniedException("Cannot delete a forum post from another party");
        }

        forumPostService.deleteForumPostById(postId);
        return "redirect:/partyManage";
    }

    @PostMapping("/partyManage/announcements/{announcementId}/delete")
    public String deleteAnnouncement(
            @PathVariable Long announcementId,
            Authentication authentication) {
        Party ownerParty = getOwnerParty(authentication);
        Announcement announcement = announcementService.getAnnById(announcementId);
        if (!ownerParty.getPartyId().equals(announcement.getParty().getPartyId())) {
            throw new AccessDeniedException("Cannot delete an announcement from another party");
        }

        announcementService.deleteAnnById(announcementId);
        return "redirect:/partyManage";
    }

    private Party getOwnerParty(Authentication authentication) {
        User owner = userService.getUserByUserName(authentication.getName());
        return partyService.getPartyByUserId(owner.getUserId());
    }


    @PostMapping("/party")
    public ResponseEntity<PartyResponse> addParty(@RequestBody PartyCreateRequest party, @RequestParam Long userID) {
        Party newParty = partyService.addParty(party, userID);
        return ResponseEntity.ok(toResponse(newParty));
    }

    @DeleteMapping("/party/delete/{id}")
    public ResponseEntity<Void> deletePartyById(@PathVariable("id") Long id){
        partyService.deletePartyById(id);
        return ResponseEntity.noContent().build();

    }

   @PutMapping("/party/{id}")
        public ResponseEntity<PartyResponse> updatePartyById(@PathVariable("id") Long partyId, @RequestBody Party updatedParty) {
        Party savedParty = partyService.updateParty(partyId, updatedParty);
        return ResponseEntity.ok(toResponse(savedParty));
    }

    @GetMapping("/party/search/location")
    public ResponseEntity<List<PartyResponse>> searchPartiesByLocation(
        @RequestParam double lng, 
        @RequestParam double lat, 
        @RequestParam(required = false, defaultValue = "1000000") double radius // Default radius is 1,000,000 meters (1,000 km, or ~621 miles)
    ) {
        List<Party> partiesNearby = partyService.getPartiesByLocation(lng, lat, radius);
        return ResponseEntity.ok(partiesNearby.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping ("party/search/alignment")
    public ResponseEntity<List<PartyResponse>> searchPartiesByAlignment(
        Authentication authentication
    ) {
        User user = userService.getUserByUserName(authentication.getName());
        List<Party> partiesByAlignment = partyService.getPartiesByAlignment(user.getUserId());
        return ResponseEntity.ok(partiesByAlignment.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping ("party/search")
    public ResponseEntity<List<PartyResponse>> searchPartiesDefault(
        @RequestParam long userID,
        @RequestParam double lng, 
        @RequestParam double lat, 
        @RequestParam(required = false, defaultValue = "1000000") double radius // Default radius is 1,000,000 meters (1,000 km, or ~621 miles)
    ) {
        List<Party> partiesDefault = partyService.getPartiesDefault(userID, lng, lat, radius);
        return ResponseEntity.ok(partiesDefault.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    private PartyResponse toResponse(Party party) {
        Point point = party.getLocation();
        GeoJsonPoint location = point == null
            ? null
            : new GeoJsonPoint("Point", List.of(point.getX(), point.getY()));

        return new PartyResponse(
            party.getPartyId(),
            party.getPartyName(),
            party.getDescription(),
            party.getPartyAlignment(),
            party.getStatus(),
            location,
            party.getPartyMembers(),
            party.getAnn(),
            party.getForumPost()
        );
    }

    private PartyDistanceResponse toDistanceResponse(PartyDistance partyDistance) {
        Party party = partyService.getPartyById(partyDistance.getPartyId());
        Point point = party.getLocation();
        GeoJsonPoint location = point == null
            ? null
            : new GeoJsonPoint("Point", List.of(point.getY(), point.getX()));

        return new PartyDistanceResponse(
            party.getPartyId(),
            party.getPartyName(),
            party.getDescription(),
            party.getPartyAlignment(),
            party.getStatus(),
            location,
            party.getPartyMembers(),
            party.getAnn(),
            party.getForumPost(),
            partyDistance.getDistanceMeters()
        );
    }



}
