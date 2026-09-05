package com.politicalpioneer.Announcement;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.politicalpioneer.Party.Party;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long annId;

    @ManyToOne()
    @JoinColumn(name="party_id", nullable = false)
    private Party party;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdDate;
    
    @Column(nullable = false)
    private String title;

    public Announcement(Party party, String description, LocalDateTime createdDate, String title) {
        this.party = party;
        this.description = description;
        this.createdDate = createdDate;
        this.title = title;
    } 

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

     public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

       public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }


}
