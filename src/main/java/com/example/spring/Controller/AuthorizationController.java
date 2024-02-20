package com.example.spring.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
@CrossOrigin("*")
public class AuthorizationController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String test() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Authorities: " + authentication.getAuthorities());
            return "Welcome  Admin";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/test")
    @PreAuthorize("hasRole('USER')")
    public String testUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Authorities: " + authentication.getAuthorities());
            return "Welcome  User";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
