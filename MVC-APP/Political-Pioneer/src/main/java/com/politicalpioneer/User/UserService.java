package com.politicalpioneer.User;

import java.util.List;


import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;

//Still need to implement
//DTO, Security, Annotations for constraints, Exception Handling
@Service 
public class UserService {
    
    private final UserRepository userRepo;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public UserService(UserRepository userRep) {
        this.userRepo = userRep;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<User> getUserByStatus(String status) {
        return userRepo.findByRole(status);
    }

    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public User addUser(User user) {
        if (user == null) {
            throw new BadRequestException("User object null");
        }
        return userRepo.save(user);
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepo.save(existingUser);
    }


    
    public void deleteUserById(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }
        userRepo.deleteById(userId);
    }
}
