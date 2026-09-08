package com.politicalpioneer.Party;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController 
public class PartyController {
    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping("/party")
    public ResponseEntity<List<Party>> getAllParties() {
        List<Party> parties = partyService.getAllParties();
        return ResponseEntity.ok(parties);
    }

    @GetMapping("/party/{id}")
    public ResponseEntity<Party> getPartyById(@PathVariable("id") Long id) {
        Party party = partyService.getPartyById(id);
        if (party == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(party);
    }

    @GetMapping("/party/status/{status}")
    public ResponseEntity<List<Party>> getPartyByStatus(@PathVariable("status") String status) {
        List<Party> party = partyService.getPartyByStatus(status);
        if (party == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(party);
    }

    @PostMapping("/party")
    public ResponseEntity<Party> addParty(@RequestBody Party party) {
        Party newParty = partyService.addParty(party);
        return ResponseEntity.ok(newParty);
    }

    @DeleteMapping("/party/delete/{id}")
    public ResponseEntity<Void> deletePartyById(@PathVariable("id") Long id){
        Party party = partyService.getPartyById(id);

        if (party == null) {
            return ResponseEntity.notFound().build();
        }

        partyService.deletePartyById(id);
        return ResponseEntity.noContent().build();

    }

   @PutMapping("/party/{id}")
public ResponseEntity<Party> updatePartyById(@PathVariable("id") Long partyId, @RequestBody Party updatedParty) {
    Party savedParty = partyService.updateParty(partyId, updatedParty);
    return ResponseEntity.ok(savedParty);
}
}
