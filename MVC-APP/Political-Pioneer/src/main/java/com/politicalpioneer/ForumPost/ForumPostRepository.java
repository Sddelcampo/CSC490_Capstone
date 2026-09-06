package com.politicalpioneer.ForumPost;

import java.text.Normalizer.Form;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.politicalpioneer.User.User;


public interface ForumPostRepository extends JpaRepository<ForumPost, Long>{
    List<ForumPost> findByUserId(Long user);
    List<ForumPost> findByUserIdAndPartyId(Long userId, Long partyId);

    List<ForumPost> findByPartyId(Long party);
    List<ForumPost> findByTitle(String title);
    
}

