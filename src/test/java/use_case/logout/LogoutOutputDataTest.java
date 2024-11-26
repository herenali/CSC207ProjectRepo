package use_case.logout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.login.LoginOutputData;

import static org.junit.jupiter.api.Assertions.*;

class LogoutOutputDataTest {
    private String testUsername;
    private boolean testUseCaseFailed;
    LogoutOutputData logoutOutputData;

    @BeforeEach
    void setUp() {
        testUsername = "testusername";
        testUseCaseFailed = false;
        logoutOutputData = new LogoutOutputData(testUsername, testUseCaseFailed);
    }

    @Test
    void getUsername() {
        assertEquals(testUsername, logoutOutputData.getUsername());
    }

    @Test
    void isUseCaseFailed() {
        assertEquals(testUseCaseFailed, logoutOutputData.isUseCaseFailed());
    }
}