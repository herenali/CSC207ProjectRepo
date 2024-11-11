package use_case.send_message;

import use_case.login.LoginOutputData;

/**
 * The output boundary for the Send Message Use Case.
 */
public interface SendMessageOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SendMessageOutputData sendMessageOutputData);

    void prepareFailView(String error);

}
