package use_case.create_group_channel;

import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import entity.SbGroupChannelManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The Create Group Channel Interactor.
 */
public class CreateGroupChannelInteractor implements CreateGroupChannelInputBoundary {
    private final CreateGroupChannelOutputBoundary createGroupChannelPresenter;

    public CreateGroupChannelInteractor(CreateGroupChannelOutputBoundary createGroupChannelPresenter) {
        this.createGroupChannelPresenter = createGroupChannelPresenter;
    }

    public void execute(CreateGroupChannelInputData createGroupChannelInputData) {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        final SbGroupChannelManager sbGroupChannelManager = new SbGroupChannelManager(defaultClient);

        final String currentUserId = createGroupChannelInputData.getCurrentUserId();
        try {
            if (createGroupChannelInputData.getUsers() != null && !createGroupChannelInputData.getUsers().isEmpty()) {
                if (!createGroupChannelInputData.getUsers().contains(currentUserId)){
                    createGroupChannelInputData.getUsers().add(currentUserId);
                }
                sbGroupChannelManager.createChannel(createGroupChannelInputData.getUsers(),
                        createGroupChannelInputData.getChatName());
            }
            else if (createGroupChannelInputData.getUser() != null && !createGroupChannelInputData.getUser().isEmpty()) {
                List<String> userList = new ArrayList<>();
                userList.add(createGroupChannelInputData.getUser());
                sbGroupChannelManager.createChannel(userList,
                        createGroupChannelInputData.getChatName());
            }
            else {
                createGroupChannelPresenter.prepareFailView("No valid user or users.");
                return;
            }
            createGroupChannelPresenter.prepareSuccessView(new CreateGroupChannelOutputData("Chat is created."));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
