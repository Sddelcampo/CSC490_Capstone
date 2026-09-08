package com.politicalpioneer.Party;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;
import com.politicalpioneer.User.UserRepository;

@Service
public class PartyService {
    
    private final PartyRepository partyRepo;

    public Party saveParty(Party party) {
        return partyRepo.save(party);
    }

    public PartyService(PartyRepository partyRepo) {
        this.partyRepo = partyRepo;
    }

    public List<Party> getAllParties() {
        return partyRepo.findAll();
    }

    public Party getPartyById(Long partyId) {
        return partyRepo.findById(partyId).orElse(null);
    }

    public List<Party> getPartyByStatus(String status) {
        return partyRepo.findByStatus(status);
    }

    public Party getPartyByUserId(Long userId) {
        return partyRepo.findByUserId(userId);
    }

    public Party addParty(Party party) {
        if (party == null) {
            throw new BadRequestException("Party object null");
        }

        return partyRepo.save(party);
    }

    public Party updateParty(Long partyId, Party updatedParty) {
        Party existingParty = partyRepo.findById(partyId).orElseThrow(() ->
        new ResourceNotFoundException("Party with ID " + partyId + " not found"));

        existingParty.setPartyIdeology(updatedParty.getPartyIdeology());
        existingParty.setDescription(updatedParty.getDescription());
        existingParty.setStatus(updatedParty.getStatus());

        return partyRepo.save(existingParty);

    }
    
    public void deletePartyById(Long id) {
        if (!partyRepo.existsById(id)) {
            throw new ResourceNotFoundException("Party does not exist");
        }
        partyRepo.deleteById(id);
    }





}
