package com.ubi.userservice.util;


import com.ubi.userservice.entity.Permission;
import com.ubi.userservice.entity.User;
import com.ubi.userservice.model.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {
//    @Value("${app.SECRET_KEY}")
    private String secretKey="secret";


    public String extractUsername(String token) throws Exception {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) throws Exception {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) throws Exception {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = getAllClaims(user);
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Boolean validateToken(String token, User user) throws Exception {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Map<String, Object> getAllClaims(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("Role",user.getRole().getRoleType());
        return claims;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Set<Permission> permissions) {
        Set<Authority> roles = new HashSet<>();
        for(Permission permission:permissions){
            roles.add(new Authority(permission.getType()));
        }
        return roles;
    }

}