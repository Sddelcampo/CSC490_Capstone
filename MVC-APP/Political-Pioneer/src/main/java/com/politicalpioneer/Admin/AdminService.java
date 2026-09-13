package com.politicalpioneer.Admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.ResourceNotFoundException;

@Service
public class AdminService {
    
    private final AdminRepository adminRepo;

    public Admin saveAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    public AdminService(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    public List<Admin> getAllAdmin() {
        return adminRepo.findAll();
    }

    public Admin getAdminById(Long id) {
        if (!adminRepo.existsById(id)) {
                throw new ResourceNotFoundException("Party with ID " + id + " not found");
        }   
        return adminRepo.findById(id).orElse(null);
    }

    public Admin addAdmin(Admin admin) {
        if(admin == null) {
            throw new ResourceNotFoundException("Admin not found");
        }
        return adminRepo.save(admin);
    }

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
