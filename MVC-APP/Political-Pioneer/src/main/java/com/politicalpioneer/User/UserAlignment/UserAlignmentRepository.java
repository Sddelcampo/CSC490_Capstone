package com.politicalpioneer.User.UserAlignment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;

public interface UserAlignmentRepository extends JpaRepository<UserAlignment, Long>{
    List<UserAlignment> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select alignment from UserAlignment alignment where alignment.id = :userId")
    Optional<UserAlignment> findByIdForUpdate(@Param("userId") Long userId);
    
}
