package com.politicalpioneer.PartyMember;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;

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
        return memberRepo.findByIdPartyId(partyId);
    }

    public PartyMember getPMById(Long userId, Long partyId) {
        PartyMemberId id = new PartyMemberId(userId, partyId);
        if(!memberRepo.existsById(id)) {
            throw new ResourceNotFoundException("Party Members not found");
        }
        return memberRepo.findById(id).orElse(null);

    }

    //Work on passing original composite key
    public PartyMember addPartyMember(PartyMember newPm) {

        // PartyMemberId id = new PartyMemberId(
        //     partyMember.getPartyId(),
        //     partyMember.getUserId()
        // );

        // if (memberRepo.existsById(id)) {
        //     throw new IllegalStateException("This user already exisits");
        // }

        return memberRepo.save(newPm);
    }

   public void deletePartyMemberById(Long userId, Long partyId) {
    PartyMemberId id = new PartyMemberId(userId, partyId);
    if (!memberRepo.existsById(id)) {
        throw new ResourceNotFoundException("Party Member with userId " + userId + " and partyId " + partyId + " not found");
    }
    memberRepo.deleteById(id);


}

}
