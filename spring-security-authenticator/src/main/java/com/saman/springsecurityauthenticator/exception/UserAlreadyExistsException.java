package com.saman.springsecurityauthenticator.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Saman Karim
 *
 * Custom exception class for unique username case
 */
@Data
public class UserAlreadyExistsException extends Exception {
}
