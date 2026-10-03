package com.politicalpioneer.Party.PartyAlignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyAlignmentRepository extends JpaRepository<PartyAlignment, Long>{
    List<PartyAlignment> findByPartyId(Long partyId);
    
}
