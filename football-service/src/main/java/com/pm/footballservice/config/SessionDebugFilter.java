package com.pm.footballservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SessionDebugFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("\n=== REQUEST: " + request.getMethod() + " " + request.getRequestURI() + " ===");

        // Look for SESSION cookie instead of JSESSIONID
        String sessionCookie = getSessionCookie(request);
        System.out.println("SESSION Cookie: " + sessionCookie);

        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("Session ID: " + session.getId());
            System.out.println("Session is new: " + session.isNew());

            Object secContext = session.getAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
            );
            System.out.println("Security Context in session: " + (secContext != null));

            if (secContext != null) {
                SecurityContext sc = (SecurityContext) secContext;
                Authentication auth = sc.getAuthentication();
                System.out.println("Auth in session context: " +
                        (auth != null ? auth.getName() : "null"));
            }
        } else {
            System.out.println("⚠️ No session found");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current Authentication in SecurityContextHolder: " +
                (auth != null ? auth.getName() + " (authenticated: " + auth.isAuthenticated() + ")" : "null"));

        filterChain.doFilter(request, response);

        System.out.println("Response Status: " + response.getStatus());
        System.out.println("==================\n");
    }

    private String getSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "Not found";
    }
}