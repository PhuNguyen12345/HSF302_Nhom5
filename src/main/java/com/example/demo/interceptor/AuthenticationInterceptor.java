package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        // Allow access to public pages
        if (isPublicPage(requestURI)) {
            return true;
        }
        
        // Check if user is logged in
        if (loggedInUser == null) {
            response.sendRedirect("/users/login");
            return false;
        }
        
        // Check admin/librarian access
        if (isAdminPage(requestURI)) {
            MembershipRole userRole = loggedInUser.getMembershipRole();
            if (userRole != MembershipRole.ADMIN && userRole != MembershipRole.LIBRARIAN) {
                response.sendRedirect("/dashboard");
                return false;
            }
        }
        
        // Check admin-only access
        if (isAdminOnlyPage(requestURI)) {
            MembershipRole userRole = loggedInUser.getMembershipRole();
            if (userRole != MembershipRole.ADMIN) {
                response.sendRedirect("/dashboard");
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isPublicPage(String requestURI) {
        return requestURI.equals("/") || 
               requestURI.equals("/users/login") || 
               requestURI.equals("/users/register") ||
               requestURI.startsWith("/css/") ||
               requestURI.startsWith("/js/") ||
               requestURI.startsWith("/images/") ||
               requestURI.equals("/favicon.ico") ||
               requestURI.startsWith("/error");
    }
    
    private boolean isAdminPage(String requestURI) {
        return requestURI.startsWith("/admin/") ||
               requestURI.startsWith("/users/manage") ||
               requestURI.startsWith("/users/edit/") ||
               requestURI.equals("/statistics") ||
               requestURI.startsWith("/books/manage") ||
               requestURI.startsWith("/borrowings/manage");
    }
    
    private boolean isAdminOnlyPage(String requestURI) {
        return requestURI.startsWith("/users/delete/") ||
               requestURI.startsWith("/books/delete/") ||
               requestURI.startsWith("/system/");
    }
}
