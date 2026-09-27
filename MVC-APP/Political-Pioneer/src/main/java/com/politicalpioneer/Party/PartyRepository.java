package com.politicalpioneer.Party;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByPartyIdAndUserId(Long partyId, Long userId);
    List<Party> findByStatus(String status);
    List<Party> findByPartyName(String partyName);
    Party findByUserId(Long userId);
    
    // Custom query to find parties within a certain radius of the user's location, calculated using PostGIS functions
    // @Query(value ="""
    //     SELECT p.*, ST_Distance(
    //         ST_SetSRID(ST_MakePoint(:lng, :lat), 4326),
    //         ST_SetSRID(p.location, 4326)
    //     ) AS distance
    //     FROM Party p 
    //     WHERE ST_DWithin(
    //         ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), 
    //         ST_SetSRID(p.location, 4326), 
    //         :radius
    //     )
    //     ORDER BY distance
    // """, nativeQuery = true)
    // List<Party> findByLocationWithin(double lat, double lng, double radius);
}
