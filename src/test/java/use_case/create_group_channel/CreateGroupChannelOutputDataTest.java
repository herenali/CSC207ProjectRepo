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
        successOutputData = new CreateGroupChannelOutputData("Channel Url", List.of("userIds"), false);
        failureOutputData = new CreateGroupChannelOutputData(null, List.of("userIds"), true);
    }

    @Test
    void isUseCaseFailed() {
        assertFalse(successOutputData.isUseCaseFailed());
        assertTrue(failureOutputData.isUseCaseFailed());
    }
}
