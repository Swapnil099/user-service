package com.ubi.userservice.config;


import com.ubi.userservice.dto.user.UserPermissionsDto;
import com.ubi.userservice.entity.User;
import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.error.Result;
import com.ubi.userservice.mapper.UserMapper;
import com.ubi.userservice.service.UserService;
import com.ubi.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ") && !request.getRequestURI().equals("/authenticate")) {

            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (Exception e) {
                throw new CustomException(
                        HttpStatusCode.INVALID_TOKEN.getCode(),
                        HttpStatusCode.INVALID_TOKEN,
                        HttpStatusCode.INVALID_TOKEN.getMessage(),
                        new Result<>());
            }
        }

        if (requestTokenHeader != null && !requestTokenHeader.startsWith("Bearer ")){
            throw new CustomException(
                    HttpStatusCode.TOKEN_FORMAT_INVALID.getCode(),
                    HttpStatusCode.TOKEN_FORMAT_INVALID,
                    HttpStatusCode.TOKEN_FORMAT_INVALID.getMessage(),
                    new Result<>());
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.getUserEntityByUsername(username);
            UserPermissionsDto userPermissionsDto = userMapper.userPermissionsDto(user);
            Collection<? extends GrantedAuthority> permissions = jwtUtil.getAuthorities(user.getRole().getPermissions());

            try {
                if (jwtUtil.validateToken(jwtToken, user)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userPermissionsDto, null, permissions);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    System.out.println(SecurityContextHolder.getContext().toString());
                }
            } catch (Exception e) {
                throw new CustomException(
                        HttpStatusCode.TOKEN_EXPIRED.getCode(),
                        HttpStatusCode.TOKEN_EXPIRED,
                        HttpStatusCode.TOKEN_EXPIRED.getMessage(),
                        new Result<>());
            }
        }

        if(username == null && (request.getRequestURI().matches("/user/*") || request.getRequestURI().matches("/role/*"))) {
            throw new CustomException(
                    HttpStatusCode.TOKEN_NOT_FOUND.getCode(),
                    HttpStatusCode.TOKEN_NOT_FOUND,
                    HttpStatusCode.TOKEN_NOT_FOUND.getMessage(),
                    new Result<>());
        }
        filterChain.doFilter(request, response);
    }
}



