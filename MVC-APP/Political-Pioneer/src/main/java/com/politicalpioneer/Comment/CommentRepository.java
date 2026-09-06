package com.politicalpioneer.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByForumPostId(Long postId);
    List<Comment> findByStatus(String status);
    List<Comment> findByVisibility(boolean visibility);
}
