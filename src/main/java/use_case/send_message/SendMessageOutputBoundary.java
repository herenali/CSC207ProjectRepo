package use_case.send_message;

/**
 * The output boundary for the Send Message Use Case.
 */
public interface SendMessageOutputBoundary {
    /**
     * Prepares the success view for the Send Message Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SendMessageOutputData outputData);

    /**
     * Prepares the fail view for the Send Message Use Case.
     * @param error the error
     */
    void prepareFailView(String error);

}
