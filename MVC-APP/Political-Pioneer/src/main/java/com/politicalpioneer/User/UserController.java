package com.politicalpioneer.User;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller 
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/role/{status}")
    public ResponseEntity<List<User>> getUserByRole(@PathVariable("status") String status) {
        List<User> users = userService.getUserByStatus(status);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/userName/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable("userName") String userName) {
        User users = userService.getUserByUserName(userName);
        return ResponseEntity.ok(users);
    }
   
    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User users) {
        User user = userService.addUser(users);
        return ResponseEntity.ok(user);
     }

    @PutMapping("/user/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody User updatedUser) {
    User savedUser = userService.updateUser(userId, updatedUser);
    return ResponseEntity.ok(savedUser);
 }


@DeleteMapping("/user/{userId}")
public ResponseEntity<Void> deleteUserById(@PathVariable("userId") Long userId) {
    userService.deleteUserById(userId);
    return ResponseEntity.noContent().build();
}


@PostMapping("/userSignup")
    public Object userSignUp(@ModelAttribute User user, Model model) {
        try {
             userService.addUser(user);
        }
        catch(Exception e) {
            System.out.print("User name already exists");
        }
       
        return "redirect:/acctlogin";   
}



}
