package use_case.create_group_channel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGroupChannelOutputDataTest {
    private CreateGroupChannelOutputData successOutputData;
    private CreateGroupChannelOutputData failureOutputData;

    @BeforeEach
    void setUp() {
        successOutputData = new CreateGroupChannelOutputData("Group channel created successfully.", false);
        failureOutputData = new CreateGroupChannelOutputData("Failed to create group channel.", true);
    }

    @Test
    void getMessage() {
        assertEquals("Group channel created successfully.", successOutputData.getMessage());
        assertEquals("Failed to create group channel.", failureOutputData.getMessage());
    }

    @Test
    void isUseCaseFailed() {
        assertFalse(successOutputData.isUseCaseFailed());
        assertTrue(failureOutputData.isUseCaseFailed());
    }

}
