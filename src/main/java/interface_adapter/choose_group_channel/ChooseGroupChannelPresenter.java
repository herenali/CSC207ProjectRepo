package interface_adapter.choose_group_channel;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import use_case.choose_group_channel.ChooseGroupChannelOutputBoundary;
import use_case.choose_group_channel.ChooseGroupChannelOutputData;

/**
 * The Presenter for the Choose Group Channel Use Case.
 */
public class ChooseGroupChannelPresenter implements ChooseGroupChannelOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public ChooseGroupChannelPresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(ChooseGroupChannelOutputData outputData) {
        // On success, update the contents of the chat area
        final var loggedInState = loggedInViewModel.getState();
        loggedInState.setUsersAndMessages(outputData.getUsersAndMessages());
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Display an error message in the chat area
    }
}
