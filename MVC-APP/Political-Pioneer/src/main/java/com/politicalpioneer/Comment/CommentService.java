package com.politicalpioneer.Comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;

@Service
public class CommentService {
    
    private final CommentRepository commentRepo;
 
    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }
 
    public Comment saveComment(Comment comment) {
        return commentRepo.save(comment);
    }
 
    public List<Comment> getAllComments() {
        if (commentRepo.findAll() == null) {
            throw new ResourceNotFoundException("Comments not found");
        }
        return commentRepo.findAll();
    }
 
    public Comment getCommentById(Long id) {
        if (!commentRepo.existsById(id)) {
            throw new ResourceNotFoundException("Comment with ID " + id + " not found");
        }
 
        return commentRepo.findById(id).orElse(null);
    }
 
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepo.findByForumPostId(postId);
    }
 
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepo.findByUserId(userId);
    }
 
    public Comment addCommentBy(Comment newComment) {
        if (newComment == null) {
            throw new BadRequestException("Comment object null");
        }
        return commentRepo.save(newComment);
    }
 
    public Comment updateComment(Long id, Comment updatedComment) {
        Comment existComment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
 
        existComment.setContent(updatedComment.getContent());
     
       
        return commentRepo.save(existComment);
    }
 
    public void deleteCommentById(Long id) {
        if (!commentRepo.existsById(id)) {
            throw new ResourceNotFoundException("Comment with ID " + id + " not found");
        }
        commentRepo.deleteById(id);
    }
}
