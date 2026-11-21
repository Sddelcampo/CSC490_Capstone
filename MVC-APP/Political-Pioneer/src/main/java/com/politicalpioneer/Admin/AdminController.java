package com.politicalpioneer.Admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.politicalpioneer.AuthenticatedNameModel;
import com.politicalpioneer.User.User;
import com.politicalpioneer.User.UserService;


@Controller
public class AdminController {
    private final AdminService adminService;


    private final UserService userService;
    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
        
    }

    @GetMapping("/admin")
    public String adminHome(Model model) {
        String ownerRole = "owner";
        String userRole = "user";
        List<User> user = userService.getUserByRole(userRole);
        List<User> owner = userService.getUserByRole(ownerRole);
        
        model.addAttribute("user", user);
        model.addAttribute("owner", owner);
        return "admin";
    }




    // @GetMapping("/admin")
    // public ResponseEntity<List<Admin>> getAllUsers() {
    //     List<Admin> admin = adminService.getAllAdmin();
    //    return ResponseEntity.ok(admin);
    // }


    

    @GetMapping("/getAllAdmin")
    public ResponseEntity<List<Admin>> getAllAdmin(){
        List<Admin> admins = adminService.getAllAdmin();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/adminUsername/{userName}")
    public ResponseEntity<Admin> getAdminByUserName(@PathVariable("userName") String userName) {
        Admin admin = adminService.getByUserName(userName);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Long id) {
        Admin admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/createAdmin")
    public Object adminSignUp(@ModelAttribute Admin admin, Model model) {
        adminService.addAdmin(admin);
        return "redirect:/home";   
        
    }

   

    @PutMapping("/updateAdmin/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") Long id, @RequestBody Admin updatedAdmin) {
        Admin admin = adminService.updateAdminById(id, updatedAdmin);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<Void> deleteAdminById(@PathVariable("id") Long id) {
        adminService.deleteAdminById(id);
        return ResponseEntity.noContent().build();
    }
    
  
}
