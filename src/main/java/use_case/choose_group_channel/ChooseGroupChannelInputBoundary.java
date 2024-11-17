package use_case.choose_group_channel;

/**
 * Input Boundary for actions which are related to choosing a group channel.
 */
public interface ChooseGroupChannelInputBoundary {

    /**
     * Executes the choose group channel use case.
     * @param chooseGroupChannelInputData the input data
     */
    void execute(ChooseGroupChannelInputData chooseGroupChannelInputData);
}
