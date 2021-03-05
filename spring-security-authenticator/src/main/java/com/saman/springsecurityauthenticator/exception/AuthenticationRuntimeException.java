package com.saman.springsecurityauthenticator.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Saman Karim
 *
 * Custom Exception class
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRuntimeException extends RuntimeException {
    private String message;
}
