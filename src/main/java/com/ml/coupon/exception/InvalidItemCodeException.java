package com.ml.coupon.exception;

public class InvalidItemCodeException extends RuntimeException {

    public InvalidItemCodeException() {
    }

    public InvalidItemCodeException(String s) {
        super(s);
    }
}

