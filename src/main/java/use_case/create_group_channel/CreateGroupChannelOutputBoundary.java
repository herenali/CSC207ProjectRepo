package use_case.create_group_channel;

/**
 * The output boundary for the Create Group Channel Use Case.
 */
public interface CreateGroupChannelOutputBoundary {
    /**
     * Prepares the success view for the Create Group Channel Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(CreateGroupChannelOutputData outputData);


    /**
     * Prepares the failure view for the Choose Group Channel Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
