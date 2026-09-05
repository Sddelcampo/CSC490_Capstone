package com.politicalpioneer.PartyMember;
import java.io.DataOutput;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.auditing.DateTimeProvider;

import com.politicalpioneer.Party.Party;
import com.politicalpioneer.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import tools.jackson.databind.cfg.DateTimeFeature;

//Creates composite primary key for partymembers
@Embeddable
class PartyMemberId implements Serializable {
    private Long userId;
    private Long partyId;

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
    @Id
    private PartyMemberId id;

    //Passes / Maps User and Party Id to the composite id
    //of the partymember class
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("partyId")
    @JoinColumn(name = "party_id")
    private Party party;

    @Column(name="date_join", nullable = false)
    private LocalDateTime joinDate;

    @Column
    private String status;


    public PartyMember(PartyMemberId id, User user, Party party, LocalDateTime joinDate, String status) {
        this.id = id;
        this.user = user;
        this.party = party;
        this.joinDate = joinDate;
        this.status = status;
       
    }

    public PartyMemberId getId() {
        return id;
    }

    public void setId(PartyMemberId id) {
        this.id = id;
    }


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
