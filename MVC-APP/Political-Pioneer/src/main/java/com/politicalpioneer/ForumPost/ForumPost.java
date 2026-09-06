package com.politicalpioneer.ForumPost;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.politicalpioneer.Comment.Comment;
import com.politicalpioneer.Party.Party;
import com.politicalpioneer.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class ForumPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="column_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "party", nullable = false)
    private Party party;

    @ManyToOne
    @JoinColumn(name = "\"user\"", nullable = false)
    private User user;

    @OneToMany(mappedBy="forumPost")
    private List<Comment> comment = new ArrayList<>();
    
    private String title;

    private String content;

    private LocalDateTime createdTime;

    private boolean visibility;

    protected ForumPost() {}

    public ForumPost(Long id, Party party, User user, String title, String content,
        LocalDateTime createdTime, boolean visibility
    ) {
        this.id = id;
        this.party = party;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.visibility = visibility;
    }

    public Long getFormPostId() {
        return id;
    }

    public void setFormPostId(Long id) {
        this.id = id;
    }

    public Party getParty () {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

     public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public boolean getVisiblity() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
