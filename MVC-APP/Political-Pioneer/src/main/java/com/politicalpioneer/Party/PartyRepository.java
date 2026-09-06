package com.politicalpioneer.Party;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.politicalpioneer.User.User;

public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByPartyIdAndUserId(Long partyId, Long userId);
    List<Party> findByStatus(String status);
    List<Party> findByPartyName(String partyName);
    List<Party> findByUserId(Long userId);
}
