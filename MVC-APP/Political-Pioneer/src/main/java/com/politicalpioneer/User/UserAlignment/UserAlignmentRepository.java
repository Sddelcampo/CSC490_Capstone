package com.politicalpioneer.User.UserAlignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAlignmentRepository extends JpaRepository<UserAlignment, Long>{
    List<UserAlignment> findByUserId(Long userId);
    
}
