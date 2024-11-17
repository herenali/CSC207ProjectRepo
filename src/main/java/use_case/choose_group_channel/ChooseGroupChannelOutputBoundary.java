package use_case.choose_group_channel;

/**
 * The output boundary for the Choose Group Channel Use Case.
 */
public interface ChooseGroupChannelOutputBoundary {
    /**
     * Prepares the success view for the Choose Group Channel Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ChooseGroupChannelOutputData outputData);

    /**
     * Prepares the failure view for the Choose Group Channel Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
