package com.politicalpioneer.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    List<User> findByRole(String role);
    List<User> findByFirstName(String firstName);
    List<User> findByUserName(String userName);
    
}
