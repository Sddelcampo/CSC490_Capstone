package com.politicalpioneer.ForumPost;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ForumPostController {
    
    private final ForumPostService forumService;

    public ForumPostController(ForumPostService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/forumpost-preview")
    public String showForumPostPreview(Model model) {
        model.addAttribute("post", Map.of(
                "id", 1,
                "partyId", 1,
                "partyName", "Riverbend Neighbors Party",
                "title", "Welcome to the Riverbend Community Forum",
                "body", "This is a sample forum post so you can preview the page layout.",
                "authorName", "Taylor Morgan",
                "createdAt", "Nov 12, 2026 1:00 PM"));
        model.addAttribute("comments", List.of(Map.of(
                "id", 1,
                "authorName", "Jordan Lee",
                "body", "Thanks for starting the discussion!",
                "createdAt", "Nov 12, 2026 1:15 PM")));
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

    
    
}
