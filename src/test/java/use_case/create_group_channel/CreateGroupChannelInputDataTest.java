package use_case.create_group_channel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CreateGroupChannelInputDataTest {
    private CreateGroupChannelInputData createGroupChannelInputData;

    @BeforeEach
    void setUp() {
        createGroupChannelInputData = new CreateGroupChannelInputData("Test Channel", null,
                new ArrayList<>(Arrays.asList("user456", "user789")), "user123");
    }

    @Test
    void getChatName() {
        assertEquals("Test Channel", createGroupChannelInputData.getChatName());
    }

    @Test
    void getCurrentUserId() {
        assertEquals("user123", createGroupChannelInputData.getCurrentUserId());
    }

    @Test
    void getUserIds() {
        assertEquals(new ArrayList<>(Arrays.asList("user456", "user789")), createGroupChannelInputData.getUsers());
    }
}
