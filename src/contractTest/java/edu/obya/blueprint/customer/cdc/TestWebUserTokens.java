package edu.obya.blueprint.customer.cdc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

import static edu.obya.blueprint.customer.adapter.rest.TestWebUser.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestWebUserTokens {
    public static final String TEST_USER_TOKEN = tokenOf(TEST_USER_NAME, TEST_USER_PASSWORD);
    public static final String TEST_ADMIN_TOKEN = tokenOf(TEST_ADMIN_NAME, TEST_ADMIN_PASSWORD);

    private static String tokenOf(String username, String password) {
        return String.format("Basic %s",
                Base64Utils.encodeToString(
                        String.format("%s:%s", username, password).getBytes(StandardCharsets.UTF_8)));
    }
}
