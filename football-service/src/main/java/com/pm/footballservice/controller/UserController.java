package com.pm.footballservice.controller;

import com.pm.footballservice.model.User;
import com.pm.footballservice.repository.UserRepository;
import com.pm.footballservice.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
public class UserController {
    //private UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;


    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        //this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        User user1=userRepository.findByUsername(user.getUsername());
        if(user1!=null){
            return ResponseEntity.badRequest().body("User already exist.");
        }
        try {
            User user2 = new User(user.getUsername(),passwordEncoder.encode(user.getPassword()));
            userRepository.save(user2);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            // ✅ DETAILED LOGGING
            System.out.println("\n=== LOGIN RESPONSE ===");
            System.out.println("Session ID: " + session.getId());
            System.out.println("Session Max Inactive Interval: " + session.getMaxInactiveInterval());
            System.out.println("User authenticated: " + authentication.getName());

            // Check response headers
            System.out.println("\nResponse Headers:");
            for (String headerName : response.getHeaderNames()) {
                System.out.println(headerName + ": " + response.getHeader(headerName));
            }

            // Check if Set-Cookie is being sent
            String setCookie = response.getHeader("Set-Cookie");
            System.out.println("\nSet-Cookie header: " + setCookie);
            System.out.println("======================\n");

            return ResponseEntity.ok()
                    .body(userRepository.findByUsername(user.getUsername()) + " signed in.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth(HttpServletRequest request) {
        // ✅ ADD LOGS HERE
        HttpSession session = request.getSession(false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("=== AUTH CHECK ===");
        if (session != null) {
            System.out.println("Session ID: " + session.getId());
            System.out.println("Session creation time: " + new Date(session.getCreationTime()));
            System.out.println("Last accessed: " + new Date(session.getLastAccessedTime()));
            System.out.println("Security Context in session: " +
                    session.getAttribute("SPRING_SECURITY_CONTEXT"));
        } else {
            System.out.println("⚠️ No session found!");
        }

        if (auth != null && auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken)) {
            System.out.println("✅ Authenticated as: " + auth.getName());
            System.out.println("Authorities: " + auth.getAuthorities());
            return ResponseEntity.ok("Authenticated as: " + auth.getName());
        } else {
            System.out.println("❌ Not authenticated or anonymous");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
    }
}
