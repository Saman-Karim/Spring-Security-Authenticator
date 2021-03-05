package com.saman.springsecurityauthenticator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Created by Saman Karim
 *
 * Custom error code class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto implements Serializable {

    private static final long serialVersionUID = 4107762873099588845L;
    private String errorMessage;
    private HttpStatus httpStatus;
}
