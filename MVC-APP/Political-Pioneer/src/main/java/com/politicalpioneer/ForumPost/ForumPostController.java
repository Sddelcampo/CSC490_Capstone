package com.politicalpioneer.ForumPost;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class ForumPostController {
    
    private final ForumPostService forumService;

    public ForumPostController(ForumPostService forumService) {
        this.forumService = forumService;
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
