package com.politicalpioneer.Party.PartyAlignment;

import java.util.List;


import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;

@Service 
public class PartyAlignmentService {
    
    private final PartyAlignmentRepository partyAlignmentRepo;

    public PartyAlignmentService(PartyAlignmentRepository partyAlignmentRep) {
        this.partyAlignmentRepo = partyAlignmentRep;
    }

    public PartyAlignment savePartyAlignment(PartyAlignment party) {
        return partyAlignmentRepo.save(party);
    }

    //Throw nothing [] not an error
    public List<PartyAlignment> getAllPartyAlignments() {
        return partyAlignmentRepo.findAll();
    }

    public PartyAlignment getPartyAlignmentById(Long partyId) {
        if(!partyAlignmentRepo.existsById(partyId)) {
            throw new ResourceNotFoundException("Party not found with id: " + partyId);
        }
        return partyAlignmentRepo.findById(partyId).orElse(null);
    }

    public PartyAlignment addPartyAlignment(PartyAlignment partyAlignment) {
        if (partyAlignment == null) {
            throw new BadRequestException("PartyAlignment object null");
        }
        return partyAlignmentRepo.save(partyAlignment);
    }

    public PartyAlignment updatePartyAlignment(Long partyId, PartyAlignment updatedPartyAlignment) {
        PartyAlignment existingPartyAlignment = partyAlignmentRepo.findById(partyId)
                .orElseThrow(() -> new ResourceNotFoundException("PartyAlignment with ID " + partyId + " not found"));
        return partyAlignmentRepo.save(existingPartyAlignment);
    }
    
    public void deletePartyAlignmentById(Long partyId) {
        if (!partyAlignmentRepo.existsById(partyId)) {
            throw new ResourceNotFoundException("PartyAlignment with ID " + partyId + " not found");
        }
        partyAlignmentRepo.deleteById(partyId);
    }
}
