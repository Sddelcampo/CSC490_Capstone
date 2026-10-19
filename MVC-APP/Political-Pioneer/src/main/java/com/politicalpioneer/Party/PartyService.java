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
        if(!partyRepo.existsById(partyId)) {
            throw new  ResourceNotFoundException("Party not found");
        }
        return partyRepo.findById(partyId).orElse(null);
    }

    public List<Party> getPartyByStatus(String status) {
        if(partyRepo.findByStatus(status) == null) {
            throw new ResourceNotFoundException("Party not found");
        }

        return partyRepo.findByStatus(status);
    }

    public Party getPartyByUserId(Long userId) {
        if(partyRepo.findByUserId(userId) == null) {
            throw new ResourceNotFoundException("Party not found");
        }

        return partyRepo.findByUserId(userId);
    }

    /*
    public List<Party> getPartiesDefault(Long userID) {
        User user = userRepository.findById(userID).orElse(null);
        List<Party> parties = getAllParties(); // Need to update this to filter by location
        if (parties.isEmpty()) {
            throw new ResourceNotFoundException("No parties found");
        }
        else {
            parties.sort((p1, p2) -> {
                
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
    */

    public List<Party> getPartiesByLocation(double lat, double lng, double radius) {
        return partyRepo.findByLocationWithin(lat, lng, radius);
    }

    public List<Party> getPartiesByAlignment(Long userID) {
        User user = userRepository.findById(userID)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Party> parties = getAllParties();
        if (parties.isEmpty()) {
            throw new ResourceNotFoundException("No parties found");
        }
        else {
            parties.sort((p1, p2) -> {
                /* float p1FreemarketSimilarity = Math.abs(
                    p1.getPartyAlignment().getFreemarketAlignment() - user.getUserAlignment().getFreemarketAlignment());
                float p2FreemarketSimilarity = Math.abs(
                    p2.getPartyAlignment().getFreemarketAlignment() - user.getUserAlignment().getFreemarketAlignment());
                float p1GovernmentInvolvementSimilarity = Math.abs(
                    p1.getPartyAlignment().getGovernmentInvolvementAlignment() - user.getUserAlignment().getGovernmentInvolvementAlignment());
                float p2GovernmentInvolvementSimilarity = Math.abs(
                    p2.getPartyAlignment().getGovernmentInvolvementAlignment() - user.getUserAlignment().getGovernmentInvolvementAlignment());
                float p1SocialFreedomSimilarity = Math.abs(
                    p1.getPartyAlignment().getSocialFreedomAlignment() - user.getUserAlignment().getSocialFreedomAlignment());
                float p2SocialFreedomSimilarity = Math.abs(
                    p2.getPartyAlignment().getSocialFreedomAlignment() - user.getUserAlignment().getSocialFreedomAlignment());
                float p1ForeignInvolvementSimilarity = Math.abs(
                    p1.getPartyAlignment().getForeignInvolvementAlignment() - user.getUserAlignment().getForeignInvolvementAlignment());
                float p2ForeignInvolvementSimilarity = Math.abs(
                    p2.getPartyAlignment().getForeignInvolvementAlignment() - user.getUserAlignment().getForeignInvolvementAlignment());
                float p1GunControlSimilarity = Math.abs(
                    p1.getPartyAlignment().getGunControlAlignment() - user.getUserAlignment().getGunControlAlignment());
                float p2GunControlSimilarity = Math.abs(
                    p2.getPartyAlignment().getGunControlAlignment() - user.getUserAlignment().getGunControlAlignment());
                float p1EnvironmentalSimilarity = Math.abs(
                    p1.getPartyAlignment().getEnvironmentalAlignment() - user.getUserAlignment().getEnvironmentalAlignment());
                float p2EnvironmentalSimilarity = Math.abs(
                    p2.getPartyAlignment().getEnvironmentalAlignment() - user.getUserAlignment().getEnvironmentalAlignment());
                float p1TechnologicalAdvancementSimilarity = Math.abs(
                    p1.getPartyAlignment().getTechnologicalAdvancementAlignment() - user.getUserAlignment().getTechnologicalAdvancementAlignment());
                float p2TechnologicalAdvancementSimilarity = Math.abs(
                    p2.getPartyAlignment().getTechnologicalAdvancementAlignment() - user.getUserAlignment().getTechnologicalAdvancementAlignment());
                
                float p1AlignmentSimilarity = p1FreemarketSimilarity + p1GovernmentInvolvementSimilarity + p1SocialFreedomSimilarity
                + p1ForeignInvolvementSimilarity + p1GunControlSimilarity + p1EnvironmentalSimilarity + p1TechnologicalAdvancementSimilarity;
                float p2AlignmentSimilarity = p2FreemarketSimilarity + p2GovernmentInvolvementSimilarity + p2SocialFreedomSimilarity
                + p2ForeignInvolvementSimilarity + p2GunControlSimilarity + p2EnvironmentalSimilarity + p2TechnologicalAdvancementSimilarity;
                return Float.compare(p1AlignmentSimilarity, p2AlignmentSimilarity);*/
                return Float.compare(p1.getPartyAlignment().getAlignmentDifference(user.getUserAlignment()), 
                                     p2.getPartyAlignment().getAlignmentDifference(user.getUserAlignment()));
            });
        }
        return parties;
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

        existingParty.setPartyAlignment(updatedParty.getPartyAlignment());
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
