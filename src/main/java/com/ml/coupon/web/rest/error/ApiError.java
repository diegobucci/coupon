package com.ml.coupon.web.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private String error;
}
