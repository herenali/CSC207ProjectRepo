package use_case.signup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupOutputDataTest {
    private String testUsername;
    private boolean testUseCaseFailed;
    SignupOutputData outputData;

    @BeforeEach
    void setUp() {
        testUsername = "username123";
        testUseCaseFailed = false;
        outputData = new SignupOutputData(testUsername, testUseCaseFailed);
    }

    @Test
    void getUsername() {
        assertEquals(testUsername, outputData.getUsername());
    }

    @Test
    void isUseCaseFailed() {
        assertEquals(testUseCaseFailed, outputData.isUseCaseFailed());
    }
}