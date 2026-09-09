package com.politicalpioneer.Announcement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;

//Need to check if already exists
@Service
public class AnnouncementService {
    
    private final AnnouncementRepository annRepo;

    public Announcement saveAnn(Announcement ann) {
        return annRepo.save(ann);
    }

    public AnnouncementService(AnnouncementRepository annRepo) {
        this.annRepo = annRepo;
    }

    public List<Announcement> getAllAnn() {
        return annRepo.findAll();
    }

    public Announcement getAnnById(Long annId) {
       return annRepo.findById(annId).orElse(null);
    }

    public void addAnn(Announcement newAnn) {
        
        if(newAnn == null) {
            throw new BadRequestException("Announcement object null");
        }
        annRepo.save(newAnn);
    }

    public List<Announcement> getByTitle(String annTitle) {
        return annRepo.findByTitle(annTitle);
    }

    public void deleteAnnById(Long annId) {
        if(!annRepo.existsById(annId)) {
            throw new ResourceNotFoundException("Announcement does not exist");
        }
        annRepo.deleteById(annId);
    }

    public Announcement updateAnnById(Long id, Announcement updatedAnn) {
        Announcement ann = annRepo.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Announcement with ID " + id + " not found"));

        ann.setDescription(updatedAnn.getDescription());
        ann.setTitle(updatedAnn.getTitle());
       
        return annRepo.save(ann);
    }

}
