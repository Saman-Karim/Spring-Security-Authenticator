package com.saman.springsecurityauthenticator.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saman.springsecurityauthenticator.dto.ErrorResponseDto;
import com.saman.springsecurityauthenticator.service.MyUserDetailServiceImpl;
import com.saman.springsecurityauthenticator.util.Constants;
import com.saman.springsecurityauthenticator.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Saman Karim
 *
 * AuthenticationFilter to intercept requests and validate JWT token
 */
@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailServiceImpl userDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Method doFilterInternal called");
        final String authorizationHeader = httpServletRequest.getHeader(Constants.AUTHORIZATION);
        try {
            String jwt = null;
            String username = null;
            //If the request has token, get the username from that token
            if (authorizationHeader != null && authorizationHeader.startsWith(Constants.BEARER)) {
                jwt = authorizationHeader.substring(Constants.BEARER.length());
                username = TokenUtil.extractUsernameFromToken(jwt);
            }
            //Checking that there is no active session as of yet
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
            //validating the token
                if (TokenUtil.validateToken(jwt, userDetails).equals(Boolean.TRUE)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            //Setting the context to that of current user
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            // custom error response in case of exception
            log.error("Exception occurred while user authentication: ", e.getMessage());
            ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.UNAUTHORIZED);
            httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return objectMapper.writeValueAsString(object);
    }
}
