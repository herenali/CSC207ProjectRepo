package interface_adapter.edit_message;

import interface_adapter.ViewManagerModel;
import use_case.edit_message.EditMessageOutputBoundary;
import use_case.edit_message.EditMessageOutputData;

/**
 * The Presenter for the Edit Message Use Case.
 */
public class EditMessagePresenter implements EditMessageOutputBoundary {

    private final EditMessageViewModel editMessageViewModel;
    private final ViewManagerModel viewManagerModel;

    public EditMessagePresenter(ViewManagerModel viewManagerModel, EditMessageViewModel editMessageViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.editMessageViewModel = editMessageViewModel;
    }

    @Override
    public void prepareSuccessView(EditMessageOutputData outputData) {

    }

    @Override
    public void prepareFailView(String error) {
        final EditMessageState editMessageState = editMessageViewModel.getState();
        editMessageState.setEditError(error);
        editMessageViewModel.firePropertyChanged();
    }
}
