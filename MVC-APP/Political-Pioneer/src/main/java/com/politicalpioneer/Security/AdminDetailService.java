package com.politicalpioneer.Security;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.politicalpioneer.Admin.Admin;
import com.politicalpioneer.Admin.AdminRepository;
import com.politicalpioneer.User.UserRepository;

@Service
public class AdminDetailService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByUserName(userName);
        if (admin == null) {
            throw new UsernameNotFoundException(userName + " not found");
        }

        ArrayList<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(admin.getRole()));
        return new org.springframework.security.core.userdetails.User(
                admin.getUserName(), admin.getPassword(), authList);
    }
}
