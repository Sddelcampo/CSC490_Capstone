package com.politicalpioneer.User.UserAlignment;

import java.util.List;


import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;

@Service 
public class UserAlignmentService {
    
    private final UserAlignmentRepository userAlignmentRepo;

    public UserAlignmentService(UserAlignmentRepository userAlignmentRep) {
        this.userAlignmentRepo = userAlignmentRep;
    }

    public UserAlignment saveUserAlignment(UserAlignment user) {
        return userAlignmentRepo.save(user);
    }

    //Throw nothing [] not an error
    public List<UserAlignment> getAllUserAlignments() {
        return userAlignmentRepo.findAll();
    }

    public UserAlignment getUserAlignmentById(Long userId) {
        if(!userAlignmentRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return userAlignmentRepo.findById(userId).orElse(null);
    }

    public UserAlignment addUserAlignment(UserAlignment userAlignment) {
        if (userAlignment == null) {
            throw new BadRequestException("UserAlignment object null");
        }
        return userAlignmentRepo.save(userAlignment);
    }

    public UserAlignment updateUserAlignment(Long userId, UserAlignment updatedUserAlignment) {
        UserAlignment existingUserAlignment = userAlignmentRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserAlignment with ID " + userId + " not found"));
        return userAlignmentRepo.save(existingUserAlignment);
    }
    
    public void deleteUserAlignmentById(Long userId) {
        if (!userAlignmentRepo.existsById(userId)) {
            throw new ResourceNotFoundException("UserAlignment with ID " + userId + " not found");
        }
        userAlignmentRepo.deleteById(userId);
    }
}
