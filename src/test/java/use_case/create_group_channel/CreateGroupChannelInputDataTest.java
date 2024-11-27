package use_case.create_group_channel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateGroupChannelInputDataTest {
    private CreateGroupChannelInputData createGroupChannelInputData;

    @BeforeEach
    void setUp() {
        createGroupChannelInputData = new CreateGroupChannelInputData("Test Channel",
                new ArrayList<>(Arrays.asList("user456", "user789")), "user123");
    }

    @Test
    void getChatName() {
        Assertions.assertEquals("Test Channel", createGroupChannelInputData.getChatName());
    }

    @Test
    void getCurrentUserId() {
        Assertions.assertEquals("user123", createGroupChannelInputData.getCurrentUserId());
    }

    @Test
    void getUserNames() {
        Assertions.assertEquals(new ArrayList<>(Arrays.asList("user456", "user789")), createGroupChannelInputData.getUsers());
    }
}

