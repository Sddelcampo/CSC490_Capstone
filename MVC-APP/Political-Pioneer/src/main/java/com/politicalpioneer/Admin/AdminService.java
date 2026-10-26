package com.politicalpioneer.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.politicalpioneer.ResourceNotFoundException;
import com.politicalpioneer.User.User;
import com.politicalpioneer.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepo;

    private String nonAdmin = "user";
    private final AdminRepository adminRepo;


    @Autowired 
    private  PasswordEncoder passwordEncoder;


    public List<User> getAllNonAdminUsers() {
        return userRepo.findByRole(nonAdmin);
    }



    

    public Admin saveAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    

    public AdminService(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    public List<Admin> getAllAdmin() {
        return adminRepo.findAll();
    }

    public Admin getByUserName(String userName) {
        if(adminRepo.findByUserName(userName) == null) {
            throw new ResourceNotFoundException("Admin not found");
        }
        return adminRepo.findByUserName(userName);
    }
    public Admin getAdminById(Long id) {
        if (!adminRepo.existsById(id)) {
                throw new ResourceNotFoundException("Admin with ID " + id + " not found");
        }   
        return adminRepo.findById(id).orElse(null);
    }

    

    public Admin addAdmin(Admin admin) {
  
        if(admin == null) {
            throw new ResourceNotFoundException("Admin not found");
        }
        Admin newAdmin = adminRepo.save(admin);
        
        newAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));

      
        return adminRepo.save(newAdmin);
    }

    //   User newUser = userRepo.save(user);
    //     newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    //     return userRepo.save(newUser);

    public void deleteAdminById(Long id) {
        Admin admin = adminRepo.findById(id).orElse(null);
        if(admin == null) {
            throw new ResourceNotFoundException("Admin not found");
        }
        adminRepo.deleteById(id);
    }

    public Admin updateAdminById(Long id, Admin admin) {
        if(!adminRepo.existsById(id)) {
            throw new ResourceNotFoundException("Admin not found");
        }
        Admin existAdmin = adminRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin not found with id " + id));
        // Admin admin2 = adminRepo.findById(id).orElse(null);
        existAdmin.setFirstName(admin.getFirstName());
        existAdmin.setLastName(admin.getFirstName());
        existAdmin.setUserName(admin.getUserName());

        return adminRepo.save(existAdmin);
    }

    public void deletePartyById(Long id) {
        if(adminRepo.findById(id) == null) {
            throw new ResourceNotFoundException("Admin not found");
        }
        adminRepo.deleteById(id);
    }



}
