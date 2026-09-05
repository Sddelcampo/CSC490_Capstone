package com.politicalpioneer.ForumPost;

import java.time.LocalDateTime;

import com.politicalpioneer.Party.Party;
import com.politicalpioneer.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class ForumPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formPostId;

    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    private String content;

    private LocalDateTime createdTime;

    private boolean visibility;


    public ForumPost(Long formPostId, Party party, User user, String title, String content,
        LocalDateTime createdTime, boolean visibility
    ) {
        this.formPostId = formPostId;
        this.party = party;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.visibility = visibility;
    }

    public Long getFormPostId() {
        return formPostId;
    }

    public void setFormPostId(Long formPostId) {
        this.formPostId = formPostId;
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
