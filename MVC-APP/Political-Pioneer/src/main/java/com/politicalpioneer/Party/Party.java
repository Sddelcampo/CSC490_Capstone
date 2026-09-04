package com.politicalpioneer.Party;

import com.politicalpioneer.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Party {
    @Id
    @Column
    private Long party_id;

    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

    @Column
    private String description;

    @Column
    private Float party_ideology;

    @Column
    private String status;

    public Party() {};
   
    public Party(Long party_id, User user, String description, Float partyIdeology,
        String status
     ) {
        this.party_id = party_id;
        this.user = user;
        this.description = description;
        this.party_ideology = partyIdeology;
        this.status = status;
    }


    public Long getPartyId() {
        return party_id;
    }

    public void setPartyId(Long partyId) {
        this.party_id = partyId;
    }


     public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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


    

    



}
