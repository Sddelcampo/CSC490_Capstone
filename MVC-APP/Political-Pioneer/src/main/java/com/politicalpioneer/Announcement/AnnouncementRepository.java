package com.politicalpioneer.Announcement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>{
    List<Announcement> findByPartyId(Long partyId);
    List<Announcement> findByTitle(String title);
}
