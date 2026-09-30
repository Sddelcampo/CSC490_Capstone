package com.politicalpioneer.Party;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.politicalpioneer.Announcement.Announcement;
import com.politicalpioneer.ForumPost.ForumPost;
import com.politicalpioneer.PartyMember.PartyMember;
import com.politicalpioneer.User.User;
import com.politicalpioneer.Party.PartyAlignment.*;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.*;

@Entity
public class Party {
    @Id
    @Column(name="party_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference("user-parties")
    @ManyToOne
    @JoinColumn(name = "\"user\"", nullable = false)
    private User user;

    @Column
    private String partyName;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonManagedReference("party_alignment")
    @OneToOne(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private PartyAlignment partyAlignment;
    
    @Column
    private String status;

    // @Column(name = "location", columnDefinition = "geography(Point, 4326)", nullable = false)
    // private Point location;
   
    @JsonManagedReference("party-member")
    @OneToMany(mappedBy="party")
    private List<PartyMember> partyMember = new ArrayList<>();

    @JsonManagedReference("party-ann")
    @OneToMany(mappedBy="party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Announcement> ann = new ArrayList<>();

    //Forum post are made by the owner but through the party
   
    @JsonManagedReference("party-forum")
    @OneToMany(mappedBy="party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForumPost> forumPost = new ArrayList<>();

    public Party() {};
   
    public Party(Long id, User user, String partyName, String description, PartyAlignment partyAlignment,
        String status, //Point location
         List<PartyMember> partyMembers, List<Announcement> ann, List<ForumPost> forumPost
     ) {
        this.id = id;
        this.user = user;
        this.partyName = partyName;
        this.description = description;
        // this.location = location;
        this.partyAlignment = partyAlignment;
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

    public PartyAlignment getPartyAlignment() {
        return partyAlignment;
    }

    public void setPartyAlignment(PartyAlignment partyAlignment) {
        this.partyAlignment = partyAlignment;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
