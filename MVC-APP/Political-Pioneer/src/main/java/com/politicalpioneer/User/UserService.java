package com.politicalpioneer.User;

import java.util.List;


import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;


//Still need to implement
//DTO, Security, Annotations for constraints, Exception Handling
@Service 
public class UserService {
    
    @Autowired 
    private  UserRepository userRepo;

    @Autowired 
    private  PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    // public UserService(UserRepository userRep) {
    //     this.userRepo = userRep;
    // }

    //Throw nothing [] not an error
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    //Fix status issue
    public List<User> getUserByStatus(String status) {
        if(userRepo.findByRole(status) == null) {
            throw new ResourceNotFoundException("User not found with");
        }
        return userRepo.findByRole(status);
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

    //Add protectionns for similar username****
    public User addUser(User user) {
        if (user == null) {
            throw new BadRequestException("User object null");
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


    
    public void deleteUserById(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }
        userRepo.deleteById(userId);
    }
}
