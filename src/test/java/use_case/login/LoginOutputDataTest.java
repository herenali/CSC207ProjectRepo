package use_case.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginOutputDataTest {
    private String testUsername;
    private String testUserId;
    private boolean testUseCaseFailed;
    LoginOutputData loginOutputData;

    @BeforeEach
    void setUp() {
        testUsername = "testusername";
        testUserId = "user123";
        testUseCaseFailed = false;
        loginOutputData = new LoginOutputData(testUsername, testUserId, testUseCaseFailed);
    }

    @Test
    void getUsername() {
        assertEquals(testUsername, loginOutputData.getUsername());
    }

    @Test
    void getUserId() {
        assertEquals(testUserId, loginOutputData.getUserId());
    }
}