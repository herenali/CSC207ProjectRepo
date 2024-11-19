package use_case.create_group_channel;

import java.util.List;

/**
 * The Input Data for the ChooseGroupChannel Use Case.
 */
public final class CreateGroupChannelInputData {
    private final String chatName;
    private final String user;
    private final List<String> users;

    private CreateGroupChannelInputData(String chatName, String user, List<String> users) {
        this.chatName = chatName;
        this.user = user;
        this.users = users;
    }

    /**
     * Executes the Create Single Chat Use Case.
     * @param chatName for name of chat
     * @param user for name of user
     * @return the InputData
     */
    public static CreateGroupChannelInputData forSingleChat(String chatName, String user) {
        return new CreateGroupChannelInputData(chatName, user, null);
    }

    /**
     * Executes the Create Group Chat Use Case.
     * @param chatName for name of chat
     * @param users for name of users
     * @return the InputData
     */
    public static CreateGroupChannelInputData forGroupChat(String chatName, List<String> users) {
        return new CreateGroupChannelInputData(chatName, null, users);
    }

    public String getChatName() {
        return chatName;
    }

    public String getUser() {
        return user;
    }

    public List<String> getUsers() {
        return users;
    }
}
