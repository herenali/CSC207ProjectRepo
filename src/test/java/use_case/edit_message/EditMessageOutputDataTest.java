package use_case.edit_message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditMessageOutputDataTest {
    private EditMessageOutputData successOutputData;
    private EditMessageOutputData failureOutputData;

    @BeforeEach
    void setUp() {
        successOutputData = new EditMessageOutputData("Message edited successfully.", false);
        failureOutputData = new EditMessageOutputData("Failed to edit message.", true);
    }

    @Test
    void getMessage() {
        assertEquals("Message edited successfully.", successOutputData.getMessage()); // success case
        assertEquals("Failed to edit message.", failureOutputData.getMessage()); // failure case
    }

    @Test
    void isUseCaseFailed() {
        assertFalse(successOutputData.isUseCaseFailed()); // success case
        assertTrue(failureOutputData.isUseCaseFailed()); // failure case
    }
}