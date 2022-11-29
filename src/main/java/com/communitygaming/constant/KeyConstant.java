package com.communitygaming.constant;

public final class KeyConstant {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORITIES_KEY = "auth";
    public static final String USER = "ROLE_USER";
    private KeyConstant() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}