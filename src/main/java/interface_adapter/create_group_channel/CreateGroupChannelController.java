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
     *
     * @param chatName      for name of chat
     * @param users         for list of users
     * @param currentUserId for logged-in user's ID
     */
    public void execute(String chatName, List<String> users, String currentUserId) {
        final CreateGroupChannelInputData inputData = new CreateGroupChannelInputData(chatName, users, currentUserId);
        createGroupChannelUseCaseInteractor.execute(inputData);
    }
}
