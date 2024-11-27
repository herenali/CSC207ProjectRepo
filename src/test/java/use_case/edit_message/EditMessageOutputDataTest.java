package use_case.edit_message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.SendBirdMessageResponse;

import static org.junit.jupiter.api.Assertions.*;

class EditMessageOutputDataTest {
    private EditMessageOutputData successOutputData;
    private EditMessageOutputData failureOutputData;
    private SendBirdMessageResponse testMessage;

    @BeforeEach
    void setUp() {
        testMessage = new SendBirdMessageResponse();
        testMessage.setMessage("test message");
        successOutputData = new EditMessageOutputData(testMessage, false);
        failureOutputData = new EditMessageOutputData(testMessage, true);
    }

    @Test
    void getMessage() {
        assertEquals(testMessage.getMessage(), successOutputData.getMessage().getMessage()); // success case
        assertEquals(testMessage.getMessage(), failureOutputData.getMessage().getMessage()); // failure case
    }

    @Test
    void isUseCaseFailed() {
        assertFalse(successOutputData.isUseCaseFailed()); // success case
        assertTrue(failureOutputData.isUseCaseFailed()); // failure case
    }
}