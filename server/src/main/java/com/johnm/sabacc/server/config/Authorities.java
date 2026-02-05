package com.johnm.sabacc.server.config;

public class Authorities {
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String SCOPED_USER = "SCOPE_" + ROLE_USER;
    public static final String SCOPED_ADMIN = "SCOPE_" + ROLE_ADMIN;
}