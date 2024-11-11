package interface_adapter.send_message;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.send_message.SendMessageState;
import interface_adapter.send_message.SendMessageViewModel;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

/**
 * The Presenter for the Send Message Use Case.
 */
public class SendMessagePresenter implements SendMessageOutputBoundary {
    private final SendMessageViewModel sendMessageViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public SendMessagePresenter(ViewManagerModel viewManagerModel,
                                LoggedInViewModel loggedInViewModel,
                                SendMessageViewModel send_messageViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.sendMessageViewModel = send_messageViewModel;
    }

    @Override
    public void prepareSuccessView(SendMessageOutputData response) {
        // On success, switch to the logged in view.

//        final LoggedInState loggedInState = loggedInViewModel.getState();
//        loggedInState.setUsername(response.getUsername());
//        this.loggedInViewModel.setState(loggedInState);
//        this.loggedInViewModel.firePropertyChanged();
//
//        this.viewManagerModel.setState(loggedInViewModel.getViewName());
//        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final SendMessageState sendMessageState = sendMessageViewModel.getState();
        sendMessageState.setSendError(error);
        sendMessageViewModel.firePropertyChanged();
    }
}
