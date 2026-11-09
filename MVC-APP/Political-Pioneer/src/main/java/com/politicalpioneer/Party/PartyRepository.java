package com.politicalpioneer.Party;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartyRepository extends JpaRepository<Party, Long> {    
    List<Party> findByPartyIdAndUserId(Long partyId, Long userId);
    List<Party> findByStatus(String status);
    List<Party> findByPartyName(String partyName);
    Party findByUserId(Long userId);
    
    // Custom query to find parties within a certain radius of the user's location, calculated using PostGIS functions
    @Query(value = """
            SELECT p.party_id   AS "partyId",
                   p.party_name  AS "partyName",
                   p.description AS "description",
                   p.status      AS "status",
                   ST_Distance(
                       ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography,
                       p.location
                   ) AS "distanceMeters"
            FROM Party p
            WHERE ST_DWithin(
                ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography,
                p.location,
                :radius
            )
            ORDER BY 5
            """, nativeQuery = true)
    List<PartyDistance> findByLocationWithin(@Param("lng") double lng,
                                             @Param("lat") double lat,
                                             @Param("radius") double radius);
}
