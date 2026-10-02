package com.politicalpioneer.Security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.politicalpioneer.User.User;
import com.politicalpioneer.User.UserRepository;

@Service
public class UserDetailService implements UserDetailsService{
   
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       
        User user = userRepo.findByUserName(userName);
        if(user == null) {
            throw new UsernameNotFoundException(userName + "not found");
        }
                
        ArrayList<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(
               user.getUserName(), user.getPassword(), authList);
    }
}
