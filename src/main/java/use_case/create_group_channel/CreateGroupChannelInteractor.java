package use_case.create_group_channel;

import java.util.ArrayList;
import java.util.List;

import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import entity.SbGroupChannelManager;

/**
 * The Creating Group Channel Interactor.
 */
public class CreateGroupChannelInteractor implements CreateGroupChannelInputBoundary {
    private final CreateGroupChannelOutputBoundary createGroupChannelPresenter;
    private final CreateGroupChannelDataAccessInterface userDataAccessObject;

    public CreateGroupChannelInteractor(
            CreateGroupChannelOutputBoundary createGroupChannelPresenter,
            CreateGroupChannelDataAccessInterface userDataAccessInterface) {
        this.createGroupChannelPresenter = createGroupChannelPresenter;
        this.userDataAccessObject = userDataAccessInterface;
    }

    @Override
    public void execute(CreateGroupChannelInputData createGroupChannelInputData) {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        final SbGroupChannelManager sbGroupChannelManager = new SbGroupChannelManager(defaultClient);

        final List<String> userIds = new ArrayList<>();

        if (createGroupChannelInputData.getCurrentUserId() == null
                || createGroupChannelInputData.getCurrentUserId().isEmpty()) {
            createGroupChannelPresenter.prepareFailView("Current user ID cannot be null or empty.");
            return;
        }
        else {
            userIds.add(createGroupChannelInputData.getCurrentUserId());
        }

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

        final var groupChannel = sbGroupChannelManager.createChannel(
                userIds, createGroupChannelInputData.getChatName());

        createGroupChannelPresenter.prepareSuccessView(
                new CreateGroupChannelOutputData(groupChannel.getChannelUrl(), userIds, false)
        );
    }
}