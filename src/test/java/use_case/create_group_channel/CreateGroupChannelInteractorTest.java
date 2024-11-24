package use_case.create_group_channel;

import entity.SbGroupChannelManager;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_group_channel.CreateGroupChannelController;
import interface_adapter.create_group_channel.CreateGroupChannelPresenter;
import interface_adapter.create_group_channel.CreateGroupChannelViewModel;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CreateGroupChannelInteractorTest {
    @Test
    public void createSingleChannelSuccess() {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        SbGroupChannelManager sbGroupChannelManager = new SbGroupChannelManager(defaultClient);
        CreateGroupChannelPresenter createGroupChannelPresenter = new CreateGroupChannelPresenter(new ViewManagerModel(), new CreateGroupChannelViewModel());
        CreateGroupChannelInteractor createGroupChannelInteractor = new CreateGroupChannelInteractor(createGroupChannelPresenter);

        String userPaulId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        String userJonathanId = "11415872-17cb-47ff-a986-ed7c1b63760c";
        String channelName = "Test Channel";

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData(channelName, userJonathanId, null, userPaulId);

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
