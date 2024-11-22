package use_case.edit_message;

/**
 * The output boundary for the Edit Message Use Case.
 */
public interface EditMessageOutputBoundary {
    /**
     * Prepares the success view for the Edit Message Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(EditMessageOutputData outputData);

    void prepareFailView(String error);

}
