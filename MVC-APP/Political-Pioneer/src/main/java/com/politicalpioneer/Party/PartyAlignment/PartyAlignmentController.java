package com.politicalpioneer.Party.PartyAlignment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
public class PartyAlignmentController {
    private final PartyAlignmentService partyAlignmentService;

    public PartyAlignmentController(PartyAlignmentService partyAlignmentService) {
        this.partyAlignmentService = partyAlignmentService;
    }

    @GetMapping("/parties/{id}/alignment")
    public ResponseEntity<PartyAlignment> getPartyAlignment(@PathVariable("id") Long id) {
        PartyAlignment alignment = partyAlignmentService.getPartyAlignmentById(id);
        return ResponseEntity.ok(alignment);
    }

    @PutMapping("/party/{partyId}/alignment")
    public ResponseEntity<PartyAlignment> updatePartyAlignment(@PathVariable("partyId") Long partyId, @RequestBody PartyAlignment updatedPartyAlignment) {
        PartyAlignment savedPartyAlignment = partyAlignmentService.updatePartyAlignment(partyId, updatedPartyAlignment);
        return ResponseEntity.ok(savedPartyAlignment);
    }
}
