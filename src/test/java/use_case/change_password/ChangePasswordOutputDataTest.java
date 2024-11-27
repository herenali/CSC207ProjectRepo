package use_case.change_password;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordOutputDataTest {
    private String testUsername;
    private boolean testUseCaseFailed;
    private ChangePasswordOutputData changePasswordOutputData;

    @BeforeEach
    void setUp() {
        testUsername = "user123";
        testUseCaseFailed = false;

        changePasswordOutputData = new ChangePasswordOutputData(testUsername, testUseCaseFailed);
    }

    @Test
    void getUsername() {
        assertEquals(testUsername, changePasswordOutputData.getUsername());
    }

    @Test
    void isUseCaseFailed() {
        assertEquals(testUseCaseFailed, changePasswordOutputData.isUseCaseFailed());
    }
}