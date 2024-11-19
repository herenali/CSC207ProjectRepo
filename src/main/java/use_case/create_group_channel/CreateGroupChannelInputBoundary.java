package use_case.create_group_channel;

/**
 * Input Boundary for actions which are related to choosing a group channel.
 */
public interface CreateGroupChannelInputBoundary {

    /**
     * Executes the creategroup channel use case.
     * @param createGroupChannelInputData the input data
     */
    void execute(CreateGroupChannelInputData createGroupChannelInputData);
}
