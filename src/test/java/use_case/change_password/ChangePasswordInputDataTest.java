package use_case.change_password;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordInputDataTest {
    private String testPassword;
    private String testUsername;
    private ChangePasswordInputData inputData;

    @BeforeEach
    void setUp() {
        testPassword = "password123";
        testUsername = "user1";

        inputData = new ChangePasswordInputData(testPassword, testUsername);
    }

    @Test
    void getPassword() {
        assertEquals(testPassword, inputData.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals(testUsername, inputData.getUsername());
    }
}