package com.saman.springsecurityauthenticator.controller;

import com.saman.springsecurityauthenticator.exception.AuthenticationRuntimeException;
import com.saman.springsecurityauthenticator.dto.AuthenticationRequestDto;
import com.saman.springsecurityauthenticator.dto.AuthenticationResponseDto;
import com.saman.springsecurityauthenticator.dto.ErrorResponseDto;
import com.saman.springsecurityauthenticator.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Saman Karim
 */

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint to get Authentication token of a user
     *
     * @param request AuthenticationRequest: has username and password of the user
     * @return JWT Authentication Token in response
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {
        AuthenticationResponseDto authenticationResponseDto = null;
        try {
            authenticationResponseDto = authenticationService.authenticateUser(request);
            return new ResponseEntity<>(authenticationResponseDto, HttpStatus.OK);
        } catch (AuthenticationRuntimeException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage(), HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
        }
    }

}
