package interface_adapter.send_message;

import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;

/**
 * The controller for the Send Message Use Case.
 */
public class SendMessageController {
    private final SendMessageInputBoundary sendMessageCaseInteractor;

    public SendMessageController(SendMessageInputBoundary sendMessageCaseInteractor) {
        this.sendMessageCaseInteractor = sendMessageCaseInteractor;
    }

    /**
     * Executes the Send Message Use Case.
     * @param userId the user ID of the user logging in
     * @param message the message contents to send
     */
    public void execute(String userId, String message) {
        // 1. instantiate the `SendMessageData`, which should contain the username.
        // 2. tell the Interactor to execute.
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(message);

        sendMessageCaseInteractor.execute(sendMessageInputData);
    }

}
