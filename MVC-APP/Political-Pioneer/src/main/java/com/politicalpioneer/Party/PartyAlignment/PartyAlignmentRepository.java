package com.politicalpioneer.Party.PartyAlignment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

public interface PartyAlignmentRepository extends JpaRepository<PartyAlignment, Long>{
    List<PartyAlignment> findByPartyId(Long partyId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select alignment from PartyAlignment alignment where alignment.id = :partyId")
    Optional<PartyAlignment> findByIdForUpdate(@Param("partyId") Long partyId);
    
}
