package com.saman.springsecurityauthenticator.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Saman Karim
 *
 * Utility to create and validate authentication token based on username and expiry
 */
@Component
public class TokenUtil {

    private static String SECRET_KEY;

    @Autowired
    public TokenUtil(@Value("${jwt.secret.key}") String SECRET_KEY) {
        TokenUtil.SECRET_KEY = SECRET_KEY;
    }

    /**
     * get username from authentication token
     */
    public static String extractUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * get expiry date from authentication token
     */
    public static Date extractExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token)
                .getExpiration();
    }

    /**
     * Using secret key to extract information from token
     */
    private static Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * check if the token has expired
     */
    private static Boolean hasTokenExpired(String token) {
        final Date expiration = extractExpirationDateFromToken(token);
        return Objects.nonNull(expiration)
                && expiration.before(new Date());
    }

    /**
     * Generate token from username
     */
    public static String generateTokenFromUserDetails(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails.getUsername());
    }

    /**
     * Generate token
     */
    private static String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    /**
     * validate token based on username and expiry
     */
    public static Boolean validateToken(String token, UserDetails userDetails) {
        return (extractUsernameFromToken(token)
                .equals(userDetails.getUsername())
                && !hasTokenExpired(token));
    }
}
