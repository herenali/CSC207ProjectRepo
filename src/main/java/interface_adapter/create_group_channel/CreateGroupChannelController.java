package interface_adapter.create_group_channel;

import java.util.List;

import use_case.create_group_channel.CreateGroupChannelInputBoundary;
import use_case.create_group_channel.CreateGroupChannelInputData;

/**
 * The controller for the Create Group Channel Use Case.
 */
public class CreateGroupChannelController {
    private final CreateGroupChannelInputBoundary createGroupChannelUseCaseInteractor;

    public CreateGroupChannelController(CreateGroupChannelInputBoundary createGroupChannelUseCaseInteractor) {
        this.createGroupChannelUseCaseInteractor = createGroupChannelUseCaseInteractor;
    }

    /**
     * Executes the Create Group Chat Use Case.
     * @param chatName for name of chat
     * @param user for name of user
     */
    public void createSingleChat(String chatName, String user) {
        final CreateGroupChannelInputData createGroupChannelInputData = CreateGroupChannelInputData.forSingleChat(chatName, user);
        createGroupChannelUseCaseInteractor.execute(createGroupChannelInputData);
    }

    /**
     * Executes the Create Group Chat Use Case.
     * @param chatName for name of chat
     * @param users for name of user
     */
    public void createGroupChat(String chatName, List<String> users) {
        final CreateGroupChannelInputData inputData = CreateGroupChannelInputData.forGroupChat(chatName, users);
        createGroupChannelUseCaseInteractor.execute(inputData);
    }
}

