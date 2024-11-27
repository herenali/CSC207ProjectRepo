package interface_adapter.create_group_channel;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import use_case.create_group_channel.CreateGroupChannelOutputBoundary;
import use_case.create_group_channel.CreateGroupChannelOutputData;

/**
 * The Presenter for the Create Group Chat Use Case.
 */
public class CreateGroupChannelPresenter implements CreateGroupChannelOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public CreateGroupChannelPresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    /**
     * Handles the response when the group channel creation is successful.
     * Updates the state and notifies the view manager to switch views.
     *
     * @param outputData contains the result of the group channel creation
     */
    @Override
    public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
        // On success, update the contents of the chat area
        final var loggedInState = loggedInViewModel.getState();
        loggedInState.setGroupChannelUrl(outputData.getChannelUrl());
        loggedInState.addGroupChannelUrl(outputData.getChannelUrl());
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged("newChat");
    }

    /**
     * Handles the response when the group channel creation fails.
     * Displays an error message or other relevant failure responses.
     *
     * @param errorMessage the error message to display
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // Display an error message in the chat area
        System.out.println(errorMessage);
    }
}
