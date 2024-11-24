package interface_adapter.create_group_channel;

import interface_adapter.ViewModel;

/**
 * The View Model for the Create Group Channel View.
 */
public class CreateGroupChannelViewModel extends ViewModel<CreateGroupChannelState> {
    public CreateGroupChannelViewModel() {
        super("create group channel");
        setState(new CreateGroupChannelState());
    }

    /**
     * Updates the channel creation state with the provided channel name and user ID.
     *
     * @param channelName The name of the channel being created.
     * @param currentUserId The ID of the user creating the channel.
     */
    public void updateChannelCreation(String channelName, String currentUserId) {
        getState().setChannelName(channelName);
        getState().setCurrentUserId(currentUserId);
    }

    /**
     * Sets an error message in the state if the channel creation fails.
     *
     * @param error The error message describing the issue.
     */
    public void setCreateError(String error) {
        getState().setCreateError(error);
    }

}
