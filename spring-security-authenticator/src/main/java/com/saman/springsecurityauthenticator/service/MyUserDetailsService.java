package com.saman.springsecurityauthenticator.service;

import com.saman.springsecurityauthenticator.dto.AuthenticationRequestDto;

import java.util.List;

/**
 * Created by Saman Karim
 */
public interface MyUserDetailsService {

    void saveUser(AuthenticationRequestDto requestDto) throws Exception;

    List<String> viewRegisteredUsers();
}
