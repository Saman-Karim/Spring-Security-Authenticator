package com.saman.springsecurityauthenticator.service;

import com.saman.springsecurityauthenticator.exception.AuthenticationRuntimeException;
import com.saman.springsecurityauthenticator.dto.AuthenticationRequestDto;
import com.saman.springsecurityauthenticator.dto.AuthenticationResponseDto;
import com.saman.springsecurityauthenticator.util.Constants;
import com.saman.springsecurityauthenticator.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Saman Karim
 */

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailServiceImpl userDetailService;

    @Override
    public AuthenticationResponseDto authenticateUser(AuthenticationRequestDto authenticationRequestDto) {
        log.info("authenticateUser called with username: ", authenticationRequestDto.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationRuntimeException (Constants.INVALID_CREDENTIALS);
        }
        try {
            final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequestDto.getUsername());
            final String jwt = TokenUtil.generateTokenFromUserDetails(userDetails);
            return new AuthenticationResponseDto(jwt);
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationRuntimeException(Constants.INVALID_USERNAME);
        }
    }
}
