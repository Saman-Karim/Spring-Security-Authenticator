package com.saman.springsecurityauthenticator.controller;

import com.saman.springsecurityauthenticator.dto.AuthenticationRequestDto;
import com.saman.springsecurityauthenticator.dto.ErrorResponseDto;
import com.saman.springsecurityauthenticator.exception.AuthenticationRuntimeException;
import com.saman.springsecurityauthenticator.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Saman Karim
 */

@RestController
public class UserController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * @param request: current http request session
     *                 Endpoint that uses token created by Authentication API
     * @return successful response with username of current user
     */
    @GetMapping("/welcomeUser")
    public ResponseEntity<?> welcomeUser(HttpServletRequest request) {
        try {
            String username = request.getUserPrincipal().getName();
            return new ResponseEntity<>(new StringBuilder("Welcome " + username + "!"), HttpStatus.OK);
        } catch (AuthenticationRuntimeException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage(), HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/viewRegisteredUsers")
    public ResponseEntity<?> viewRegisteredUsers(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(myUserDetailsService.viewRegisteredUsers(), HttpStatus.OK);
        }
        catch (AuthenticationRuntimeException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage(), HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
        }

    }


    /**
     * Endpoint to add more Users in the system
     * Headers: Use authentication token of an existing user
     * RequestBody: AuthenticationRequestDto : username and password of the new user
     * Response: Success or failure
     */
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            myUserDetailsService.saveUser(requestDto);
            return new ResponseEntity<>(new StringBuilder("Successfully added User " + requestDto.getUsername() + "!"), HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
