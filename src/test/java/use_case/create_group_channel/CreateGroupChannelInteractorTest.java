package use_case.create_group_channel;

import entity.SbGroupChannelManager;
import interface_adapter.create_group_channel.CreateGroupChannelController;
import interface_adapter.create_group_channel.CreateGroupChannelPresenter;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CreateGroupChannelInteractorTest {
    private static SbGroupChannelManager sbGroupChannelManager;
    private static CreateGroupChannelInteractor createGroupChannelInteractor;
    private static CreateGroupChannelPresenter createGroupChannelPresenter;
    private static String userPaulId;
    private static String userJonathanId;
    private static String channelName;

    @BeforeEach
    void initAll() {
        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        sbGroupChannelManager = new SbGroupChannelManager(defaultClient);
        createGroupChannelInteractor = new CreateGroupChannelInteractor(createGroupChannelPresenter);

        userPaulId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        userJonathanId = "11415872-17cb-47ff-a986-ed7c1b63760c";
        channelName = "Test Channel";
    }

    @Test
    public void testCreateGroupChannel() {
        List<String> userIds = Arrays.asList(userPaulId, userJonathanId);
        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData(channelName, null, userIds, userPaulId);
        createGroupChannelInteractor.execute(inputData);
        CreateGroupChannelOutputBoundary successPresenter = new CreateGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                assertEquals("Chat is created.", outputData.getMessage());
            }
            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };
    }
}
