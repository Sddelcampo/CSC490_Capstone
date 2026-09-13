package com.politicalpioneer.PartyMember;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.politicalpioneer.Party.Party;
import com.politicalpioneer.User.User;

import jakarta.persistence.*;

//Creates composite primary key for partymembers
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
   
    //References PartyMemberId class
    @JsonIgnore
    @EmbeddedId
    private PartyMemberId id;

    //Passes / Maps User and Party Id to the composite id
    //of the partymember class

    @JsonBackReference("user-member")
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "\"user\"")
    private User user;

    @JsonBackReference("party-member")
    @ManyToOne
    @MapsId("partyId")
    @JoinColumn(name = "party")
    private Party party;

    @Column(name = "date_join", nullable = false)
    private LocalDateTime joinDate;

    @Column
    private String status;

    protected PartyMember() {}

    

    public PartyMember(PartyMemberId id, User user, Party party, LocalDateTime joinDate, String status) {
        this.id = id;
        this.user = user;
        this.party = party;
        this.joinDate = joinDate;
        this.status = status;
       
    }

    //Allows jackson to parse through composite key
    //get and return the user and party info
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
