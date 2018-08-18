package com.example.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.error.RedundantUserException;
import com.example.demo.repositories.*;
import static java.util.Collections.emptyList;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.demo.models.User> applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        com.example.demo.models.User user = applicationUser.get();
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }

    public boolean checkUserAvailibility(com.example.demo.models.User users) {
        Optional<com.example.demo.models.User> user = applicationUserRepository.findByUsername(users.getUsername());
        if (user.isPresent())
            throw new RedundantUserException("username not available - " + users.getUsername());
        user = applicationUserRepository.findByEmail(users.getEmail());
        if (user.isPresent())
            throw new RedundantUserException("email not available - " + users.getEmail());

        return true;
    }
}