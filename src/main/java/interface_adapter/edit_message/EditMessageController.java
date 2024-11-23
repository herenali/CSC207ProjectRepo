package interface_adapter.edit_message;

import use_case.edit_message.EditMessageInputBoundary;
import use_case.edit_message.EditMessageInputData;

/**
 * The controller for the Edit Message Use Case.
 */
public class EditMessageController {
    private final EditMessageInputBoundary editMessageCaseInteractor;

    public EditMessageController(EditMessageInputBoundary editMessageCaseInteractor) {
        this.editMessageCaseInteractor = editMessageCaseInteractor;
    }

    /**
     * Executes the Edit Message Use Case.
     * @param userId the user ID of the user editing the message
     * @param messageId the message ID of the message being edited
     * @param groupChannelUrl the url of the group channel
     * @param message the message contents to change to
     */
    public void execute(String userId, int messageId, String groupChannelUrl, String message) {
        final EditMessageInputData editMessageInputData = new EditMessageInputData(userId, messageId, groupChannelUrl, message);

        editMessageCaseInteractor.execute(editMessageInputData);
    }

}
