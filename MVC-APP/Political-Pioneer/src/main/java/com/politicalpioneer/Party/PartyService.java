package com.politicalpioneer.Party;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;
import com.politicalpioneer.User.*;

@Service
public class PartyService {
    
    private final PartyRepository partyRepo;
    private final UserRepository userRepository;

    public Party saveParty(Party party) {
        return partyRepo.save(party);
    }

    public PartyService(PartyRepository partyRepo, UserRepository userRepository) {
        this.partyRepo = partyRepo;
        this.userRepository = userRepository;
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

    public List<Party> getPartiesDefault(Long userID) {
        User user = userRepository.findById(userID).orElse(null);
        List<Party> parties = getAllParties();
        if (parties.isEmpty()) {
            throw new ResourceNotFoundException("No parties found");
        }
        else {
            parties.sort((p1, p2) -> {
                float p1Similarity;
                float p2Similarity;
                float p1IdeologySimilarity = p1.getPartyIdeology() - user.getUserIdeology();
                float p2IdeologySimilarity = p2.getPartyIdeology() - user.getUserIdeology();
                // Add section for weighting location
                if (p1IdeologySimilarity < p2IdeologySimilarity) {
                        return -1;
                    }
                    else if (p1IdeologySimilarity > p2IdeologySimilarity) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                });
            return parties;
        }
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
