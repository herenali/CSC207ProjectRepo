package use_case.send_message;

/**
 * The output boundary for the Send Message Use Case.
 */
public interface SendMessageOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SendMessageOutputData outputData);

}
