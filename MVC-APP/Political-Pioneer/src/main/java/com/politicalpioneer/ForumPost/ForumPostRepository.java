package com.politicalpioneer.ForumPost;

import java.text.Normalizer.Form;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.politicalpioneer.User.User;


public interface ForumPostRepository extends JpaRepository<ForumPost, Long>{
    List<ForumPost> findByUserId(Long user);
    List<ForumPost> findByUserIdAndPartyId(Long userId, Long partyId);

    ForumPost findByPartyId(Long party);
    List<ForumPost> findByTitle(String title);

    @Query("""
            SELECT post FROM ForumPost post
            WHERE post.party.id = :partyId AND post.party.user.id = :ownerId
            ORDER BY post.createdTime DESC
            """)
    List<ForumPost> findRecentByPartyIdAndOwnerId(
            @Param("partyId") Long partyId,
            @Param("ownerId") Long ownerId,
            Pageable pageable);
    
}
