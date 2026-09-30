package com.politicalpioneer.User.UserAlignment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
public class UserAlignmentController {
    private final UserAlignmentService userAlignmentService;

    public UserAlignmentController(UserAlignmentService userAlignmentService) {
        this.userAlignmentService = userAlignmentService;
    }

    @GetMapping("/users/{id}/alignment")
    public ResponseEntity<UserAlignment> getUserAlignment(@PathVariable("id") Long id) {
        UserAlignment alignment = userAlignmentService.getUserAlignmentById(id);
        return ResponseEntity.ok(alignment);
    }

    @PutMapping("/user/{userId}/alignment")
    public ResponseEntity<UserAlignment> updateUserAlignment(@PathVariable("userId") Long userId, @RequestBody UserAlignment updatedUserAlignment) {
        UserAlignment savedUserAlignment = userAlignmentService.updateUserAlignment(userId, updatedUserAlignment);
        return ResponseEntity.ok(savedUserAlignment);
    }
}
