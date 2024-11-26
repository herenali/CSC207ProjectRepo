package use_case.create_group_channel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGroupChannelOutputDataTest {
    private CreateGroupChannelOutputData successOutputData;
    private CreateGroupChannelOutputData failureOutputData;


    @BeforeEach
    void setUp() {
        successOutputData = new CreateGroupChannelOutputData("Channel Url", false);
        failureOutputData = new CreateGroupChannelOutputData(null, true);
    }

    @Test
    void isUseCaseFailed() {
        assertFalse(successOutputData.isUseCaseFailed());
        assertTrue(failureOutputData.isUseCaseFailed());
    }
}
