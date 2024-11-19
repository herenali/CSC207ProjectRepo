package interface_adapter.create_group_channel;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import use_case.create_group_channel.CreateGroupChannelOutputBoundary;
import use_case.create_group_channel.CreateGroupChannelOutputData;

/**
 * The Presenter for the Create Group Chat Use Case.
 */
public class CreateGroupChannelPresenter implements CreateGroupChannelOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateGroupChannelPresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    /**
     * Executes the Create Group Channel Use Case.
     * @param outputData for data to be outputted
     */
    public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
        // On success, update the contents of the chat area
        final var loggedInState = loggedInViewModel.getState();
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();
    }

    /**
     * Executes the Create Group Channel Use Case.
     * @param errorMessage to be displayed when program runs into an error
     */
    public void prepareFailView(String errorMessage) {
        // Display an error message in the chat area
    }
}
