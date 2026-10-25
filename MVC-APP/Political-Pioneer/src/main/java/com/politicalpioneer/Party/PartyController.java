package com.politicalpioneer.Party;

import java.util.List;
import java.util.stream.Collectors;

import com.politicalpioneer.Announcement.Announcement;
import com.politicalpioneer.ForumPost.ForumPost;
import com.politicalpioneer.Party.PartyAlignment.PartyAlignment;
import com.politicalpioneer.PartyMember.PartyMember;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class PartyController {
    public record GeoJsonPoint(String type, List<Double> coordinates) {}

    public record PartyResponse(
        Long partyId,
        String partyName,
        String description,
        PartyAlignment partyAlignment,
        String status,
        GeoJsonPoint location,
        List<PartyMember> partyMembers,
        List<Announcement> ann,
        List<ForumPost> forumPost
    ) {}

    public record PartyCreateRequest(
        String partyName,
        String description,
        String status,
        Double latitude,
        Double longitude
    ) {}

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping("/party")
    public ResponseEntity<List<PartyResponse>> getAllParties() {
        List<Party> parties = partyService.getAllParties();
        return ResponseEntity.ok(parties.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/party/{id}")
    public ResponseEntity<PartyResponse> getPartyById(@PathVariable("id") Long id) {
        Party party = partyService.getPartyById(id);
        return ResponseEntity.ok(toResponse(party));
    }
    
    @GetMapping("/party/status/{status}")
    public ResponseEntity<List<PartyResponse>> getPartyByStatus(@PathVariable("status") String status) {
        List<Party> party = partyService.getPartyByStatus(status);
        return ResponseEntity.ok(party.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("/party")
    public ResponseEntity<PartyResponse> addParty(@RequestBody PartyCreateRequest party, @RequestParam Long userID) {
        Party newParty = partyService.addParty(party, userID);
        return ResponseEntity.ok(toResponse(newParty));
    }

    @DeleteMapping("/party/delete/{id}")
    public ResponseEntity<Void> deletePartyById(@PathVariable("id") Long id){
        Party party = partyService.getPartyById(id);
        partyService.deletePartyById(id);
        return ResponseEntity.noContent().build();

    }

   @PutMapping("/party/{id}")
        public ResponseEntity<PartyResponse> updatePartyById(@PathVariable("id") Long partyId, @RequestBody Party updatedParty) {
        Party savedParty = partyService.updateParty(partyId, updatedParty);
        return ResponseEntity.ok(toResponse(savedParty));
    }

    @GetMapping("/party/search/location")
    public ResponseEntity<List<PartyResponse>> searchPartiesByLocation(
        @RequestParam double lng, 
        @RequestParam double lat, 
        @RequestParam(required = false, defaultValue = "1000000") double radius // Default radius is 1,000,000 meters (1,000 km, or ~621 miles)
    ) {
        List<Party> partiesNearby = partyService.getPartiesByLocation(lng, lat, radius);
        return ResponseEntity.ok(partiesNearby.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping ("party/search/alignment")
    public ResponseEntity<List<PartyResponse>> searchPartiesByAlignment(
        @RequestParam long userID
    ) {
        List<Party> partiesByAlignment = partyService.getPartiesByAlignment(userID);
        return ResponseEntity.ok(partiesByAlignment.stream().map(this::toResponse).collect(Collectors.toList()));
    }

     @GetMapping ("party/search")
    public ResponseEntity<List<PartyResponse>> searchPartiesDefault(
        @RequestParam long userID,
        @RequestParam double lng, 
        @RequestParam double lat, 
        @RequestParam(required = false, defaultValue = "1000000") double radius // Default radius is 1,000,000 meters (1,000 km, or ~621 miles)
    ) {
        List<Party> partiesDefault = partyService.getPartiesDefault(userID, lng, lat, radius);
        return ResponseEntity.ok(partiesDefault.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    private PartyResponse toResponse(Party party) {
        Point point = party.getLocation();
        GeoJsonPoint location = point == null
            ? null
            : new GeoJsonPoint("Point", List.of(point.getX(), point.getY()));

        return new PartyResponse(
            party.getPartyId(),
            party.getPartyName(),
            party.getDescription(),
            party.getPartyAlignment(),
            party.getStatus(),
            location,
            party.getPartyMembers(),
            party.getAnn(),
            party.getForumPost()
        );
    }
}
