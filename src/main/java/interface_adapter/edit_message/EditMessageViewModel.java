package interface_adapter.edit_message;

import interface_adapter.ViewModel;

/**
 * The View Model for the Edit Message View.
 */
public class EditMessageViewModel extends ViewModel<EditMessageState> {

    public EditMessageViewModel() {
        super("edit message");
        setState(new EditMessageState());
    }

}
