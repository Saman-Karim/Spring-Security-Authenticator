package com.saman.springsecurityauthenticator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Saman Karim
 *
 * Response for successful authentication
 */
@Data
@AllArgsConstructor
public class AuthenticationResponseDto implements Serializable {

    private static final long serialVersionUID = -1289388927739756444L;
    private final String authenticationToken;
}
