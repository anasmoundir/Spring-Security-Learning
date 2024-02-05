package com.example.spring.Controller;

import com.example.spring.model.Dto.AuthRequestDTO;
import com.example.spring.model.Dto.JwtResponseDTO;
import com.example.spring.service.JwtService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class authController {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtService jwtService;
    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String generatedToken = jwtService.generateToken(authRequestDTO.getUsername());
                return JwtResponseDTO.builder().accessToken(generatedToken).build();
            } else {
                throw new UsernameNotFoundException("Invalid user credentials");
            }
        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid user credentials", e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping")
    public String test() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Authorities: " + authentication.getAuthorities());
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
