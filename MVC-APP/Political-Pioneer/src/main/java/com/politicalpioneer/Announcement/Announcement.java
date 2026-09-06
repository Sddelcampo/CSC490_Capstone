package com.politicalpioneer.Announcement;

import jakarta.persistence.Entity;

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
    @Column(name="annn_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name="party", nullable = false)
    private Party party;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdDate;
    
    @Column(nullable = false)
    private String title;

    protected Announcement() {}

    public Announcement(Long id,Party party, String description, LocalDateTime createdDate, String title) {
        this.id = id;
        this.party = party;
        this.description = description;
        this.createdDate = createdDate;
        this.title = title;
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
