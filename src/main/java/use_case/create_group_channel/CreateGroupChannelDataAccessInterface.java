package use_case.create_group_channel;

/**
 * DAO for the Create Group Channel Use Case.
 */
public interface CreateGroupChannelDataAccessInterface {
    /**
     * Sets current chatname, user, and users.
     * @param chatName for chat name
     */
    void setCurrentChatName(String chatName);
}
