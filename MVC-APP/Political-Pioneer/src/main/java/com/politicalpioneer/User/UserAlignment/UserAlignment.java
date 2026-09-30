package com.politicalpioneer.User.UserAlignment;

import com.politicalpioneer.User.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "\"user-alignment\"")
public class UserAlignment {
    @Id
    @Column(name = "alignment_id")
    private Long id;
    
    @JsonBackReference("user_alignment")
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "freemarket_alignment", nullable = false)
    private int freemarketAlignment;

    @Column(name = "government_involvement_alignment", nullable = false)
    private int governmentInvolvementAlignment;

    @Column(name = "social_freedom_alignment", nullable = false)
    private int socialFreedomAlignment;

    @Column(name = "foreign_involvement_alignment", nullable = false)
    private int foreignInvolvementAlignment;

    @Column(name = "gun_control_alignment", nullable = false)
    private int gunControlAlignment;

    @Column(name = "environmental_alignment", nullable = false)
    private int environmentalAlignment;

    @Column(name = "technological_advancement_alignment", nullable = false)
    private int technologicalAdvancementAlignment;

    public UserAlignment() {
        this.freemarketAlignment = 0;
        this.governmentInvolvementAlignment = 0;
        this.socialFreedomAlignment = 0;
        this.foreignInvolvementAlignment = 0;
        this.gunControlAlignment = 0;
        this.environmentalAlignment = 0;
        this.technologicalAdvancementAlignment = 0;
    };

    public UserAlignment(int freemarketAlignment, int governmentInvolvementAlignment, int socialFreedomAlignment, int foreignInvolvementAlignment, int gunControlAlignment, int environmentalAlignment, int technologicalAdvancementAlignment) {
        this.freemarketAlignment = freemarketAlignment;
        this.governmentInvolvementAlignment = governmentInvolvementAlignment;
        this.socialFreedomAlignment = socialFreedomAlignment;
        this.foreignInvolvementAlignment = foreignInvolvementAlignment;
        this.gunControlAlignment = gunControlAlignment;
        this.environmentalAlignment = environmentalAlignment;
        this.technologicalAdvancementAlignment = technologicalAdvancementAlignment;
    }

    public int getFreemarketAlignment() {
        return freemarketAlignment;
    }

    public void setFreemarketAlignment(int freemarketAlignment) {
        this.freemarketAlignment = freemarketAlignment;
    }

    public void addFreemarketAlignment(int questionResponse) {
        this.freemarketAlignment += questionResponse;
    }

    public int getGovernmentInvolvementAlignment() {
        return governmentInvolvementAlignment;
    }

    public void setGovernmentInvolvementAlignment(int governmentInvolvementAlignment) {
        this.governmentInvolvementAlignment = governmentInvolvementAlignment;
    }

    public void addGovernmentInvolvementAlignment(int questionResponse) {
        this.governmentInvolvementAlignment += questionResponse;
    }

    public int getSocialFreedomAlignment() {
        return socialFreedomAlignment;
    }

    public void setSocialFreedomAlignment(int socialFreedomAlignment) {
        this.socialFreedomAlignment = socialFreedomAlignment;
    }

    public void addSocialFreedomAlignment(int questionResponse) {
        this.socialFreedomAlignment += questionResponse;
    }

    public int getForeignInvolvementAlignment() {
        return foreignInvolvementAlignment;
    }

    public void setForeignInvolvementAlignment(int foreignInvolvementAlignment) {
        this.foreignInvolvementAlignment = foreignInvolvementAlignment;
    }

    public void addForeignInvolvementAlignment(int questionResponse) {
        this.foreignInvolvementAlignment += questionResponse;
    }

    public int getGunControlAlignment() {
        return gunControlAlignment;
    }

    public void setGunControlAlignment(int gunControlAlignment) {
        this.gunControlAlignment = gunControlAlignment;
    }

    public void addGunControlAlignment(int questionResponse) {
        this.gunControlAlignment += questionResponse;
    }

    public int getEnvironmentalAlignment() {
        return environmentalAlignment;
    }

    public void setEnvironmentalAlignment(int environmentalAlignment) {
        this.environmentalAlignment = environmentalAlignment;
    }

    public void addEnvironmentalAlignment(int questionResponse) {
        this.environmentalAlignment += questionResponse;
    }

    public int getTechnologicalAdvancementAlignment() {
        return technologicalAdvancementAlignment;
    }

    public void setTechnologicalAdvancementAlignment(int technologicalAdvancementAlignment) {
        this.technologicalAdvancementAlignment = technologicalAdvancementAlignment;
    }

    public void addTechnologicalAdvancementAlignment(int questionResponse) {
        this.technologicalAdvancementAlignment += questionResponse;
    } 
}

