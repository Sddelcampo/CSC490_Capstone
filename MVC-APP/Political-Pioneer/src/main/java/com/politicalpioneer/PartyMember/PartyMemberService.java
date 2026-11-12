package com.politicalpioneer.PartyMember;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;
import com.politicalpioneer.Party.Party;
import com.politicalpioneer.User.User;

@Service
public class PartyMemberService {
    private final PartyMemberRepository memberRepo;
    private PartyMember partyMember;

    public PartyMember savePM(PartyMember partyMember) {
        return memberRepo.save(partyMember);
    }

    public PartyMemberService(PartyMemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public List<PartyMember> getAllPartyMembers() {
        return memberRepo.findAll();
    }

    public List<PartyMember> getPMByPartyId(Long partyId) {
        // memberRepo.findByIdPartyId(partyId);
        if (memberRepo.findByIdPartyId(partyId) == null) {
            throw new ResourceNotFoundException("Party member does not exist");
        }
        return memberRepo.findByIdPartyId(partyId);
    }

    public List<PartyMember> getPMByUserId(Long userId) {
        return memberRepo.findByIdUserId(userId);
    }

    public PartyMember getPMById(Long userId, Long partyId) {
        PartyMemberId id = new PartyMemberId(userId, partyId);
        if (!memberRepo.existsById(id)) {
            throw new ResourceNotFoundException("Party Members not found");
        }
        return memberRepo.findById(id).orElse(null);

    }

   

    public PartyMember addPartyMember(User user, Party party) {
        PartyMemberId id = new PartyMemberId(user.getUserId(), party.getPartyId());
        if (memberRepo.existsById(id)) {
            throw new BadRequestException("User is already a member of this party");
        }
        return memberRepo.save(new PartyMember(user, party));
    }

    public void deletePartyMemberById(Long userId, Long partyId) {
        PartyMemberId id = new PartyMemberId(userId, partyId);
        if (!memberRepo.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Party Member with userId " + userId + " and partyId " + partyId + " not found");
        }
        memberRepo.deleteById(id);

    }

}
