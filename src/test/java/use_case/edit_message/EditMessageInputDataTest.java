package use_case.edit_message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditMessageInputDataTest {
    private EditMessageInputData editMessageInputData;

    @BeforeEach
    void setUp() {
        editMessageInputData = new EditMessageInputData("user123", 101, "channelUrl123", "updated message");
    }

    @Test
    void getUserId() {
        assertEquals("user123", editMessageInputData.getUserId());
    }

    @Test
    void getMessageId() {
        assertEquals(101, editMessageInputData.getMessageId());
    }

    @Test
    void getGroupChannelUrl() {
        assertEquals("channelUrl123", editMessageInputData.getGroupChannelUrl());
    }

    @Test
    void getNewMessage() {
        assertEquals("updated message", editMessageInputData.getNewMessage());
    }
}