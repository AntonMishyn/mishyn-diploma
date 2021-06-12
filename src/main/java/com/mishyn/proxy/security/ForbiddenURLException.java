package com.mishyn.proxy.security;

public class ForbiddenURLException extends Exception {

    public ForbiddenURLException(String message) {
        super(message);
    }
}
