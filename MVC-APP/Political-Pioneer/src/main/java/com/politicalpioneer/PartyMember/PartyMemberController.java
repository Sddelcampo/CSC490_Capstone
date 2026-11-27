package com.politicalpioneer.PartyMember;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController 
public class PartyMemberController {
    private final PartyMemberService memService;

    public PartyMemberController(PartyMemberService memService) {
        this.memService = memService;
    }

 
    @DeleteMapping("/members/{userId}/{partyId}")
    public ResponseEntity<Void> deletePMById(@PathVariable("userId") Long userId, @PathVariable("partyId") Long partyId) {
        memService.deletePartyMemberById(userId, partyId);
        return ResponseEntity.noContent().build();
    }

}
