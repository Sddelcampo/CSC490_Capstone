package com.politicalpioneer.PartyMember;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.*;
import com.politicalpioneer.Party.Party;
import com.politicalpioneer.User.User;

import jakarta.persistence.*;

@Embeddable
class PartyMemberId implements Serializable {
    private Long userId;
    private Long partyId;

    public PartyMemberId() {

    }

    public PartyMemberId(Long userId, Long partyId ) {
        this.userId = userId;
        this.partyId = partyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PartyMemberId)) return false;
        PartyMemberId that = (PartyMemberId) o;
        return Objects.equals(userId, that.userId)
            && Objects.equals(partyId, that.partyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, partyId);
    }
}



@Entity
@Table(name = "party_member")
public class PartyMember {
   
    @JsonIgnore
    @EmbeddedId
    private PartyMemberId id;

    @JsonBackReference("user-member")
    @ManyToOne
    @MapsId("userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "\"user\"")
    private User user;

    @JsonBackReference("party-member")
    @ManyToOne
    @MapsId("partyId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "party")
    private Party party;

    @Column(name = "date_join", nullable = false)
    private LocalDateTime joinDate;

    @Column
    private String status;

    public PartyMember() {}

    

    public PartyMember(PartyMemberId id, User user, Party party, LocalDateTime joinDate, String status) {
        this.id = id;
        this.user = user;
        this.party = party;
        this.joinDate = joinDate;
        this.status = status;
       
    }

    public PartyMember(User user, Party party) {
    this.id = new PartyMemberId(user.getUserId(), party.getPartyId());
    this.user = user;
    this.party = party;
    this.joinDate = LocalDateTime.now();
    this.status = "active";
    }

    public PartyMemberId getId() {
        return id;
    }

    public void setId(PartyMemberId id) {
        this.id = id;
    }

    public Long getUserId() { return user.getUserId(); }
    public Long getPartyId() { return party.getPartyId() ; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

     public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public LocalDateTime getDate() {
        return joinDate;
    }

    public void setDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }


}
