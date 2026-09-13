package com.politicalpioneer.PartyMember;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class PartyMemberController {
    private final PartyMemberService memService;

    public PartyMemberController(PartyMemberService memService) {
        this.memService = memService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<PartyMember>> getAllPM() {
        List<PartyMember> members = memService.getAllPartyMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/members/{userId}/{partyId}")
    public ResponseEntity<PartyMember> getAllPM(@PathVariable Long userId, @PathVariable Long partyId) {
        PartyMember pm = memService.getPMById(userId, partyId);
        return ResponseEntity.ok(pm);
    }

    @GetMapping("/members/{partyId}")
    public ResponseEntity<List<PartyMember>> getByPMId(@PathVariable("partyId") Long partyId) {
        List<PartyMember> pm = memService.getPMByPartyId(partyId);
        return ResponseEntity.ok(pm);
    }

    @DeleteMapping("/members/{userId}/{partyId}")
    public ResponseEntity<Void> deletePMById(@PathVariable("userId") Long userId, @PathVariable("partyId") Long partyId) {
        memService.deletePartyMemberById(userId, partyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/members")
    public ResponseEntity<PartyMember> addPM(@RequestBody PartyMember newPm) {
        memService.addPartyMember(newPm);
        return ResponseEntity.ok(newPm);
    }

}
