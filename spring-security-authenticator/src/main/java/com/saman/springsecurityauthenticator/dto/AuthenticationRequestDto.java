package com.saman.springsecurityauthenticator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * Created by Saman Karim
 *
 * Request for Authentication
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto implements Serializable {

    private static final long serialVersionUID = -8764456155084681716L;
    private String username;
    private String password;

}
