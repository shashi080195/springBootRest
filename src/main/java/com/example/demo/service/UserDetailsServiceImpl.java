package com.example.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.controller.customannotations.ExecutionLog;
import com.example.demo.error.RedundantUserException;
import com.example.demo.repositories.*;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository applicationUserRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    public int checkUserAvailibility(com.example.demo.models.User users) {
        Optional<com.example.demo.models.User> user = applicationUserRepository.findByUsername(users.getUsername());
        if (user.isPresent())
            return -1;
        user = applicationUserRepository.findByEmail(users.getEmail());
        if (user.isPresent())
            return 0;

        return 1;
    }

    public boolean isValidUser(String username) {
        Optional<com.example.demo.models.User> applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser.isPresent() && applicationUser.get().getIsVerified() == true) {
            return true;
        }
        return false;
    }

    @ExecutionLog
    public boolean isUserAvailable(com.example.demo.models.User user) {
        Optional<com.example.demo.models.User> applicationUser = applicationUserRepository
                .findByUsername(user.getUsername());
        if (applicationUser.isPresent() && applicationUser.get().getIsVerified() == false
                && (user.getEmail().equals(applicationUser.get().getEmail()))) {
            return true;
        }
        return false;
    }

    @ExecutionLog
    public List<com.example.demo.models.User> getAllUser() {
        return applicationUserRepository.findAll();
    }
}