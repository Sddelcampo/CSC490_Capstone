package com.politicalpioneer.PartyMember;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyMemberRepository extends JpaRepository<PartyMember, PartyMemberId>{
    List<PartyMember> findByIdUserId(Long userId);
    List<PartyMember> findByIdPartyId(Long partyId);
    
}
