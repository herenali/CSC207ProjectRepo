package use_case.choose_group_channel;

/**
 * DAO for the Choose Group Channel Use Case.
 */
public interface ChooseGroupChannelDataAccessInterface {
    /**
     * Sets current group channel URL.
     * @param groupChannelUrl the current group channel; null to indicate that no channel is being accessed.
     */
    void setCurrentGroupChannel(String groupChannelUrl);
}
