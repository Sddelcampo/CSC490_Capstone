package com.politicalpioneer.Comment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController 
public class CommentController {
     private final CommentService commentService;
 
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
 
    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") Long id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/comments/post/{id}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable("id") Long id) {
        List<Comment> comments = commentService.getCommentsByPostId(id);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comments/user/{id}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable("id") Long id) {
        List<Comment> comments = commentService.getCommentsByUserId(id);
        return ResponseEntity.ok(comments);
    }
 
    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@RequestBody Comment newComment) {
        commentService.addCommentBy(newComment);
        return ResponseEntity.ok(newComment);
    }
 
    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateCommentById(@PathVariable("id") Long id, @RequestBody Comment comment) {
        Comment newComment = commentService.updateComment(id, comment);
        return ResponseEntity.ok(newComment);
    }


    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable("id") Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.noContent().build();
    }
}
