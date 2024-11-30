package use_case.create_group_channel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGroupChannelOutputDataTest {
    private CreateGroupChannelOutputData successOutputData;
    private CreateGroupChannelOutputData failureOutputData;


    @BeforeEach
    void setUp() {
        successOutputData = new CreateGroupChannelOutputData("Channel Url", List.of("user1", "user2"), false);
        failureOutputData = new CreateGroupChannelOutputData(null, List.of("user3", "user4"), true);
    }

    @Test
    void isUseCaseFailed() {
        assertFalse(successOutputData.isUseCaseFailed());
        assertTrue(failureOutputData.isUseCaseFailed());
    }

    @Test
    void getChannelUrl() {
        assertEquals("Channel Url", successOutputData.getChannelUrl(), "Success. Channel URL should match.");
        assertNull(failureOutputData.getChannelUrl(), "Failure. Channel URL should be null.");
    }

    @Test
    void getUserIds() {
        assertIterableEquals(List.of("user1", "user2"), successOutputData.getUserIds(), "UserIds should match for the success case.");
        assertIterableEquals(List.of("user3", "user4"), failureOutputData.getUserIds(), "UserIds should match for the failure case.");
    }
}
