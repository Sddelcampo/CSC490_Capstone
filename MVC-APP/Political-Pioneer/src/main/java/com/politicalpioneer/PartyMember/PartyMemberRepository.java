package com.politicalpioneer.PartyMember;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.politicalpioneer.Party.Party;
import com.politicalpioneer.User.User;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long>{
    List<PartyMember> findByUserId(Long userId);
    List<PartyMember> findByPartyId(Long partyId);
}
