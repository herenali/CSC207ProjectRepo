package use_case.create_group_channel;

import java.util.ArrayList;
import java.util.List;

import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import entity.SbGroupChannelManager;

/**
 * The Create Group Channel Interactor.
 */
public class CreateGroupChannelInteractor implements CreateGroupChannelInputBoundary {
    private final CreateGroupChannelOutputBoundary createGroupChannelPresenter;
    private final CreateGroupChannelDataAccessInterface userDataAccessObject;

    public CreateGroupChannelInteractor(CreateGroupChannelOutputBoundary createGroupChannelPresenter, CreateGroupChannelDataAccessInterface userDataAccessInterface) {
        this.createGroupChannelPresenter = createGroupChannelPresenter;
        this.userDataAccessObject = userDataAccessInterface;
    }

    public void execute(CreateGroupChannelInputData createGroupChannelInputData) {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        final SbGroupChannelManager sbGroupChannelManager = new SbGroupChannelManager(defaultClient);

        try {
            final List<String> userIds = new ArrayList<>();

            if (createGroupChannelInputData.getUsers() != null && !createGroupChannelInputData.getUsers().isEmpty()) {
                for (String username : createGroupChannelInputData.getUsers()) {
                    if (!userDataAccessObject.existsByName(username)) {
                        createGroupChannelPresenter.prepareFailView("Username \"" + username + "\" does not exist.");
                        return;
                    }
                    userIds.add(userDataAccessObject.getUserId(username));
                }
            }
            else {
                createGroupChannelPresenter.prepareFailView("No valid user(s).");
                return;
            }

            if (!userIds.contains(createGroupChannelInputData.getCurrentUserId())) {
                userIds.add(createGroupChannelInputData.getCurrentUserId());
            }

            final var groupChannel = sbGroupChannelManager.createChannel(userIds, createGroupChannelInputData.getChatName());

            if (groupChannel != null && groupChannel.getChannelUrl() != null) {
                createGroupChannelPresenter.prepareSuccessView(
                        new CreateGroupChannelOutputData(groupChannel.getChannelUrl(), userIds, false)
                );
            }
            else {
                createGroupChannelPresenter.prepareFailView("Failed to create group channel.");
            }
        }
        catch (Exception e) {
            createGroupChannelPresenter.prepareFailView("An error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}