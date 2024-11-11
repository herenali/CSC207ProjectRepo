package use_case.send_message;

/**
 * Input Boundary for actions which are related to sending messages.
 */
public interface SendMessageInputBoundary {
    /**
     * Executes the send message use case.
     * @param sendMessageInputData the input data
     */
    void execute(SendMessageInputData sendMessageInputData);
}
