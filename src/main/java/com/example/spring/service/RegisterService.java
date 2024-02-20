package com.example.spring.service;

import com.example.spring.model.Dto.RegistrationDto;
import com.example.spring.model.UserRole;
import com.example.spring.repository.UserRepository;
import com.example.spring.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.spring.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Component
public class RegisterService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public User registerUser(RegistrationDto registrationDTO) {
        User newUser = new User();
        newUser.setUsername(registrationDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        newUser.setEmail(registrationDTO.getEmail());
        newUser.setFirstName(registrationDTO.getFirstName());
        newUser.setLastName(registrationDTO.getLastName());
        newUser.setDateOfBirth(registrationDTO.getDateOfBirth());
        newUser.setAddress(registrationDTO.getAddress());
        newUser.setCity(registrationDTO.getCity());
        newUser.setPostalCode(registrationDTO.getPostalCode());
        newUser.setPhoneNumber(registrationDTO.getPhoneNumber());

        UserRole defaultRole = userRoleRepository.findByName("USER");
        if(defaultRole == null){
            defaultRole = new UserRole();
            defaultRole.setName("USER");
            userRoleRepository.save(defaultRole);
        }
        Set<UserRole> roles = new HashSet<>();
        roles.add(defaultRole);
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }
    public void logout() {
    }
}
