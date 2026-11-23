package com.politicalpioneer.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.politicalpioneer.*;
import com.politicalpioneer.User.UserAlignment.UserAlignment;


@Service 
public class UserService {
    
    @Autowired 
    private  UserRepository userRepo;

    @Autowired 
    private  PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<User> getUserByStatus(String status) {
        if(userRepo.findByRole(status) == null) {
            throw new ResourceNotFoundException("User not found with");
        }
        return userRepo.findByRole(status);
    }

    public List<User> getUserByRole(String role) {
        if(userRepo.findByRole(role) == null) { 
            throw new ResourceNotFoundException("User not found with");
        }
        return userRepo.findByRole(role);
    }



    public User getUserById(Long userId) {
        if(!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return userRepo.findById(userId).orElse(null);
    }

    
    public User getUserByUserName(String userName) {
        if(userRepo.findByUserName(userName) == null) {
            throw new ResourceNotFoundException("User not found with id: " + userName);
        }
        return userRepo.findByUserName(userName);
    }

    public User addUser(User user) {
        if (user == null) {
            throw new BadRequestException("User object null");
        }

       
            if (userRepo.findByUserName(user.getUserName()) != null) {
            throw new BadRequestException("Username is already taken");
            }
        
        
        if (user.getUserAlignment() == null) {
            user.setUserAlignment(new UserAlignment());
        } else {
            user.getUserAlignment().setUser(user);
        }

        User newUser = userRepo.save(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(newUser);
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

    public User updateProfile(Long userId, String firstName, String lastName, String email) {
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new BadRequestException("First name and last name are required");
        }

        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));
        existingUser.setFirstName(firstName.trim());
        existingUser.setLastName(lastName.trim());
        existingUser.setEmail(email == null || email.isBlank() ? null : email.trim());
        return userRepo.save(existingUser);
    }


    
    public void deleteUserById(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }
        userRepo.deleteById(userId);
    }
}
