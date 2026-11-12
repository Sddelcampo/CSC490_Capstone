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

    public boolean hasPartyByUserId(Long userId) {
        return partyRepo.existsByUserId(userId);
    }

    
    public List<Party> getPartiesDefault(Long userID, double lng, double lat, double radius) {
        User user = userRepository.findById(userID).orElse(null);
        List<PartyDistance> partiesDistance = partyRepo.findByLocationWithin(lng, lat, radius);
        if (partiesDistance.isEmpty()) {
            throw new ResourceNotFoundException("No parties found");
        }
        else {
            // Sort the parties based on alignment difference and their index in the list
            partiesDistance.sort((p1, p2) -> {
                Party party1 = partyRepo.findById(p1.getPartyId()).orElse(null);
                Party party2 = partyRepo.findById(p2.getPartyId()).orElse(null);
                float p1Weight = (float) ((party1.getPartyAlignment().getAlignmentDifference(user.getUserAlignment()) / 140) * 0.7) + 
                                 (float) ((p1.getDistanceMeters() / radius) * 0.3);
                float p2Weight = (float) ((party2.getPartyAlignment().getAlignmentDifference(user.getUserAlignment()) / 140) * 0.7) +
                                 (float) ((p2.getDistanceMeters() / radius) * 0.3);
                return Float.compare(p1Weight, p2Weight);
            });
            return partiesDistance.stream().map(partyDistance -> {
                Party party = partyRepo.findById(partyDistance.getPartyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Party not found"));
                return party;
            }).toList();
            /*return partiesDistance;*/
        }
    }
    

    public List<Party> getPartiesByLocation(double lng, double lat, double radius) {
        List<PartyDistance> partiesDistance = partyRepo.findByLocationWithin(lng, lat, radius);
        if (partiesDistance.isEmpty()) {
            throw new ResourceNotFoundException("No parties found");
        }
        else {
            return partiesDistance.stream().map(partyDistance -> {
                Party party = partyRepo.findById(partyDistance.getPartyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Party not found"));
                return party;
            }).toList();
            /*return partiesDistance;*/
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
