package use_case.create_group_channel;

/**
 * Input Boundary for actions which are related to creating a group channel.
 */
public interface CreateGroupChannelInputBoundary {

    /**
     * Executes the creating group channel use case.
     * @param createGroupChannelInputData the input data
     */
    void execute(CreateGroupChannelInputData createGroupChannelInputData);
}
