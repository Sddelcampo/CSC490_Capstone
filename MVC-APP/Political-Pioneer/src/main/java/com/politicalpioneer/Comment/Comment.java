package com.politicalpioneer.Comment;
import java.time.LocalDateTime;

import com.politicalpioneer.ForumPost.ForumPost;
import com.politicalpioneer.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="post", nullable = false)
    private ForumPost forumPost;

    @ManyToOne
    @JoinColumn(name= "\"user\"", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdDate;
    
    private String status;

    @Column(nullable = false)
    private boolean visibility;

    protected Comment() {}

    public Comment(Long id, ForumPost forumPost, User user, String content, LocalDateTime createdDate, 
        String status, boolean visibility
    ) {
        this.id = id;
        this.forumPost = forumPost;
        this.user = user;
        this.content = content;
        this.createdDate = createdDate;
        this.status = status;
        this.visibility = visibility;
    }

    public Long getCommentId() {
        return id;
    }

    public void setCommentId(Long id) {
        this.id = id;
    }

    public ForumPost getForumPost() {
        return forumPost;
    }

    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime createdDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getVisiblity() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }


    
}
