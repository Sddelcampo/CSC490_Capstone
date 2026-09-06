package com.politicalpioneer.Party;

import java.util.ArrayList;
import java.util.List;

import com.politicalpioneer.Announcement.Announcement;
import com.politicalpioneer.ForumPost.ForumPost;
import com.politicalpioneer.PartyMember.PartyMember;
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
public class Party {
    @Id
    @Column(name="party_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "\"user\"", nullable = false)
    private User user;

    @Column
    private String partyName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "party_ideology", nullable = false)
    private Float party_ideology;

    @Column
    private String status;

    @OneToMany(mappedBy="party")
    private List<PartyMember> partyMember = new ArrayList<>();

    @OneToMany(mappedBy="party")
    private List<Announcement> ann = new ArrayList<>();

    //Form post are made by the owner but through the party
    @OneToMany(mappedBy="party")
    private List<ForumPost> forumPost = new ArrayList<>();

    public Party() {};
   
    public Party(Long id, User user, String partyName, String description, Float partyIdeology,
        String status, List<PartyMember> partyMembers, List<Announcement> ann, List<ForumPost> forumPost
     ) {
        this.id = id;
        this.user = user;
        this.partyName = partyName;
        this.description = description;
        this.party_ideology = partyIdeology;
        this.status = status;
        this.partyMember = partyMembers;
        this.ann = ann;
        this.forumPost = forumPost;
    }


    public Long getPartyId() {
        return id;
    }

    public void setPartyId(Long id) {
        this.id = id;
    }


     public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPartyIdeology() {
        return party_ideology;
    }

    public void setPartyIdeology(Float partyIdeology) {
        this.party_ideology = partyIdeology;
    }


    public List<PartyMember> getPartyMembers() {
        return partyMember;
    }

    public void setPartyMember(List<PartyMember> partyMembers) {
        this.partyMember = partyMembers;
    }


    public List<Announcement> getAnn() {
        return ann;
    }

    public void setAnn(List<Announcement> ann) {
        this.ann = ann;
    }

     public List<ForumPost> getForumPost() {
        return forumPost;
    }

    public void setForumPost(List<ForumPost> forumPost) {
        this.forumPost = forumPost;
    }

}
