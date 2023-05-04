package edu.obya.blueprint.customer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUser {
    public static final String TEST_USER_NAME = "test";
    public static final String TEST_USER_PASSWORD = "test";
    public static final String TEST_ADMIN_NAME = "admin";
    public static final String TEST_ADMIN_PASSWORD = "admin";
    public static final String TEST_ANONYMOUS_NAME = "anonymous";
    public static final String TEST_ANONYMOUS_PASSWORD = "none";
}