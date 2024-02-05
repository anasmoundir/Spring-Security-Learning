package com.example.spring.service;

import com.example.spring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.example.spring.model.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering login by username");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        logger.info("User authenticated successfully: " + username);
        return new CustomUserDetails(user);
    }
}
