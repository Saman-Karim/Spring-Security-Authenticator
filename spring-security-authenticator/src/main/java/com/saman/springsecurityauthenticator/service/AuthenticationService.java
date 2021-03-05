package com.saman.springsecurityauthenticator.service;

import com.saman.springsecurityauthenticator.dto.AuthenticationRequestDto;
import com.saman.springsecurityauthenticator.dto.AuthenticationResponseDto;
/**
 * Created by Saman Karim
 */
public interface AuthenticationService {

    AuthenticationResponseDto authenticateUser(AuthenticationRequestDto authenticationRequestDto);
}
