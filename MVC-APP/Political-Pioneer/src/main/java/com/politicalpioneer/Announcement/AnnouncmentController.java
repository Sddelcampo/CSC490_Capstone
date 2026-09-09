package com.politicalpioneer.Announcement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnouncmentController {
    private final AnnouncementService annService;

    public AnnouncmentController(AnnouncementService annService) {
        this.annService = annService;
    }

    @GetMapping("/ann")
    public ResponseEntity<List<Announcement>> getAllAnn() {
        List<Announcement> ann = annService.getAllAnn();
        return ResponseEntity.ok(ann);
    }


    @GetMapping("/ann/{id}")
    public ResponseEntity<Announcement> getAnnById(@PathVariable("id") Long id) {
        Announcement ann = annService.getAnnById(id);
        if (ann == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ann);
    }   


    @GetMapping("/ann/title/{title}")
    public ResponseEntity<List<Announcement>> getAnnByTitle(@PathVariable("title") String title) {
        List<Announcement> ann = annService.getByTitle(title);

        if (ann == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ann);
    }

    @DeleteMapping("/ann/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        annService.deleteAnnById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ann/{id}")
    public ResponseEntity<Announcement> updateAnn(@PathVariable("id") Long id, @RequestBody Announcement updatedAnn) {
        Announcement ann = annService.updateAnnById(id, updatedAnn);

        return ResponseEntity.ok(ann);

    }

    @PostMapping("/ann")
    public ResponseEntity<Announcement> addAnn(@RequestBody Announcement ann) {
        Announcement newAnn = annService.saveAnn(ann);

        return ResponseEntity.ok(newAnn);
    }







}
