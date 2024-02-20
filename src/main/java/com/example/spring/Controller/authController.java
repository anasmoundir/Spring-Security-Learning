    package com.example.spring.Controller;
    import com.example.spring.model.Dto.AuthRequestDTO;
    import com.example.spring.model.Dto.JwtResponseDTO;
    import com.example.spring.model.Dto.RegistrationDto;
    import com.example.spring.service.JwtService;
    import com.example.spring.service.RegisterService;
    import jakarta.annotation.Resource;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.AuthenticationException;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/auth")
    @CrossOrigin("*")
    public class authController {
        @Autowired
        private RegisterService registrationService;
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
                System.out.println("Authentication:  " + authentication.getAuthorities());
                if (authentication.isAuthenticated()) {
                    String role = authentication.getAuthorities().toString();
                    String generatedToken = jwtService.generateToken(authRequestDTO.getUsername(), role);
                    return JwtResponseDTO.builder().accessToken(generatedToken).build();
                } else {
                    throw new UsernameNotFoundException("Invalid user credentials");
                }
            } catch (AuthenticationException e) {
                throw new UsernameNotFoundException("Invalid user credentials", e);
            }
        }
        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody RegistrationDto registrationDTO) {
            registrationService.registerUser(registrationDTO);
            return ResponseEntity.ok("User registered successfully");
        }
        // logout
        @GetMapping("/logout")
        public ResponseEntity<String> logout() {
            return ResponseEntity.ok("User logged out successfully");
        }
    }
