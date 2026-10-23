package com.politicalpioneer.Party;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;
import com.politicalpioneer.User.*;

import org.locationtech.jts.geom.*;

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

    
    public List<Party> getPartiesDefault(Long userID, double lng, double lat, double radius) {
        User user = userRepository.findById(userID).orElse(null);
        List<Party> parties = getPartiesByLocation(lng, lat, radius);
        if (parties.isEmpty()) {
            throw new ResourceNotFoundException("No parties found");
        }
        else {
            // Sort the parties based on alignment difference and their index in the list
            parties.sort((p1, p2) -> {
                float p1Weight = (float) (p1.getPartyAlignment().getAlignmentDifference(user.getUserAlignment()) * 0.7) + 
                                 (float) (parties.indexOf(p1) * 0.3);
                float p2Weight = (float) (p2.getPartyAlignment().getAlignmentDifference(user.getUserAlignment()) * 0.7) +
                                 (float) (parties.indexOf(p2) * 0.3);
                return Float.compare(p1Weight, p2Weight);
            });
            return parties;
        }
    }
    

    public List<Party> getPartiesByLocation(double lng, double lat, double radius) {
        List<Party> parties = getAllParties();
        if (parties.isEmpty()) {
            throw new ResourceNotFoundException("No parties found");
        }
        else {
            if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
                throw new BadRequestException("Invalid latitude or longitude values");
            }
            // Sort the parties based on alignment difference and their index in the list
            GeometryFactory geoFactory = new GeometryFactory(new PrecisionModel(), 4326);
            Point userLocation = geoFactory.createPoint(new Coordinate(lng, lat));
            parties.removeIf(party -> party.getLocation().distance(userLocation) > radius);
            if (parties.isEmpty()) {
                throw new ResourceNotFoundException("No parties found within the specified radius");
            }
            else {
                parties.sort((p1, p2) -> {
                    float p1Distance = (float) (p1.getLocation().distance(userLocation));
                    float p2Distance = (float) (p2.getLocation().distance(userLocation));
                    return Float.compare(p1Distance, p2Distance);
                });
            }
            return parties;
        }
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


    public Party addParty(PartyController.PartyCreateRequest request, Long userID) {
        if (request == null || request.latitude() == null || request.longitude() == null) {
            throw new BadRequestException("Party latitude and longitude are required");
        }

        double longitude = request.longitude();
        double latitude = request.latitude();
        if (!Double.isFinite(longitude) || !Double.isFinite(latitude)
                || longitude < -180 || longitude > 180
                || latitude < -90 || latitude > 90) {
            throw new BadRequestException("Party location has invalid longitude or latitude");
        }
        if (request.description() == null) {
            throw new BadRequestException("Party description is required");
        }

        User user = userRepository.findById(userID)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        Party party = new Party(user, request.partyName(), request.description(), location);
        if (request.status() != null) {
            party.setStatus(request.status());
        }

        return partyRepo.save(party);
    }


}
