package com.claro.projeto.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = -3337035883563879315L;

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}