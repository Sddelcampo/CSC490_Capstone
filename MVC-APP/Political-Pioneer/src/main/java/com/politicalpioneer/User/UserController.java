package com.politicalpioneer.User;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
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
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/role/{status}")
    public ResponseEntity<List<User>> getUserByRole(@PathVariable("status") String status) {
        List<User> users = userService.getUserByStatus(status);

        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

   
    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User users) {
        User user = userService.addUser(users);
        return ResponseEntity.ok(user);
     }

    @PutMapping("/user/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody User updatedUser) {
    User user = userService.getUserById(userId);

    if (user == null) {
        return ResponseEntity.notFound().build();
    }

    user.setFirstName(updatedUser.getFirstName());
    user.setLastName(updatedUser.getLastName());
    user.setPassword(updatedUser.getPassword());
    user.setEmail(updatedUser.getEmail());

    User savedUser = userService.saveUser(user);
    return ResponseEntity.ok(savedUser);
}

@DeleteMapping("/user/{userId}")
public ResponseEntity<Void> deleteUserById(@PathVariable("userId") Long userId) {
    User user = userService.getUserById(userId);

    if (user == null) {
        return ResponseEntity.notFound().build();
    }

    userService.deleteUserById(userId);
    return ResponseEntity.noContent().build();
}



}
