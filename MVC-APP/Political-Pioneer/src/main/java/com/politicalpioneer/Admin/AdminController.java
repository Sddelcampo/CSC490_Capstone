package com.politicalpioneer.Admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.politicalpioneer.AuthenticatedNameModel;
import com.politicalpioneer.ForumPost.ForumPost;
import com.politicalpioneer.Party.Party;
import com.politicalpioneer.Party.PartyService;
import com.politicalpioneer.User.User;
import com.politicalpioneer.User.UserService;


@Controller
public class AdminController {
    private final AdminService adminService;
    private final PartyService partyService;
    private final UserService userService;
 
    public AdminController(AdminService adminService, UserService userService, PartyService partyService) {
        this.adminService = adminService;
        this.userService = userService;
        this.partyService = partyService;
        
        
    }

    @GetMapping("/admin")
    public String adminHome(Model model, Authentication authentication) {
    String ownerRole = "owner";
    String userRole = "user";

    List<User> owner = userService.getUserByRole(ownerRole);
    List<User> user = userService.getUserByRole(userRole);
    List<Party> parties = partyService.getAllParties();
    Map<String, String> partyNameByOwnerId = new HashMap<>();

    for (User ownerUser : owner) {
        String partyName = ownerUser.getParty().stream()
            .findFirst()
            .map(Party::getPartyName)
            .orElse("N/A");

       partyNameByOwnerId.put(String.valueOf(ownerUser.getUserId()), partyName);
    }

    addNavigationRoleAttributes(model, authentication);
    AuthenticatedNameModel.addNameIfAuthenticated(model, authentication);
    model.addAttribute("user", user);
    model.addAttribute("partyNameByOwnerId", partyNameByOwnerId);
    model.addAttribute("party", parties);
    model.addAttribute("owner", owner);
    return "admin";
    }


    @PostMapping("/admin/owner/{userId}/delete")
    public Object deleteOwner(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) {
             throw new AccessDeniedException("Cannot delete user, user not found");
        }
    
        userService.deleteUserById(userId);
        return "redirect:/admin";
    }

    
    @PostMapping("/admin/user/{userId}/delete")
    public Object deleteUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) {
             throw new AccessDeniedException("Cannot delete user, user not found");
        }
       
        userService.deleteUserById(userId);
        return "redirect:/admin";
    }


    @PostMapping("/createAdmin")
    public Object adminSignUp(@ModelAttribute Admin admin, Model model) {
        adminService.addAdmin(admin);
        return "redirect:/home";   
        
    }


     private void addNavigationRoleAttributes(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("admin"));

        model.addAttribute("isAdmin", isAdmin);
    }

    
  
}
