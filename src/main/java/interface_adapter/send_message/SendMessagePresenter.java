package interface_adapter.send_message;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

/**
 * The Presenter for the Send Message Use Case.
 */
public class SendMessagePresenter implements SendMessageOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public SendMessagePresenter(ViewManagerModel viewManagerModel,
                                LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(SendMessageOutputData response) {
    }

    @Override
    public void prepareFailView(String error) {
    }
}
