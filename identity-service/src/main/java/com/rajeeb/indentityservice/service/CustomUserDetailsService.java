package com.rajeeb.indentityservice.service;

import com.rajeeb.indentityservice.entity.Admin;
import com.rajeeb.indentityservice.entity.CustomUserDetails;
import com.rajeeb.indentityservice.entity.Role;
import com.rajeeb.indentityservice.entity.UserEntity;
import com.rajeeb.indentityservice.repository.AdminRepository;
import com.rajeeb.indentityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByUsername(username);
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (admin != null) {
            return new CustomUserDetails(admin.getUsername(), admin.getPassword(),
                    Role.ADMIN.getAuthorities(),
                    true, true, true, true);
        }
        if (user.isPresent()) {
            return new CustomUserDetails(user.get().getUsername(), user.get().getPassword(),
                    Role.USER.getAuthorities(),
                    true, true, true, true);
        }

        throw new UsernameNotFoundException("User not found");

    }
}
