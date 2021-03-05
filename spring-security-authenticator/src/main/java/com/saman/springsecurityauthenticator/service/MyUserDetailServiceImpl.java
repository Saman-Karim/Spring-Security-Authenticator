package com.saman.springsecurityauthenticator.service;

import com.saman.springsecurityauthenticator.dto.AuthenticationRequestDto;
import com.saman.springsecurityauthenticator.model.UserRepository;
import com.saman.springsecurityauthenticator.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Saman Karim
 */
@Service
@Slf4j
public class MyUserDetailServiceImpl implements UserDetailsService, MyUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Method loadUserByUsername called with username %s", username);
        com.saman.springsecurityauthenticator.model.User user;
        user = userRepository.findUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(Constants.INVALID_USERNAME);
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public void saveUser(AuthenticationRequestDto requestDto) {
       log.info("Method saveUser called with Username: ", requestDto.getUsername());
        try {
            com.saman.springsecurityauthenticator.model.User user = new com.saman.springsecurityauthenticator.model.User();
            user.setUsername(requestDto.getUsername());
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            userRepository.save(user);
        } catch (Exception e)
        {
            log.error("Exception occurred while saving user", e.getMessage());
            if (e.getClass().equals(DataIntegrityViolationException.class)) {
                throw new UsernameNotFoundException(Constants.USERNAME_ALREADY_EXISTS);
            }
            throw e;
        }

    }

    @Override
    public List<String> viewRegisteredUsers() {
        List<com.saman.springsecurityauthenticator.model.User> users = userRepository.findAll();
        return users.stream().map(user -> user.getUsername()).collect(Collectors.toList());
    }


}
