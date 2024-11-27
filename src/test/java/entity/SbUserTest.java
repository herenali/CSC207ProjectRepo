package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import static org.junit.jupiter.api.Assertions.*;

class SbUserTest {
    private SbUser sbUser;
    private String testUsername;
    private String testPassword;
    private ApiClient defaultClient;

    @BeforeEach
    void setUp() {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        testUsername = "user1";
        testPassword = "password123";
        sbUser = new SbUser(defaultClient, testUsername, testPassword);
    }

    @Test
    void getName() {
        assertEquals(testUsername, sbUser.getName());
    }

    @Test
    void getPassword() {
        assertEquals(testPassword, sbUser.getPassword());
    }
}