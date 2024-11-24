package use_case.create_group_channel;

import java.util.List;

/**
 * The Input Data for the ChooseGroupChannel Use Case.
 */
public final class CreateGroupChannelInputData {
    private final String chatName;
    private final String user;
    private final List<String> users;
    private final String currentUserId;

    public CreateGroupChannelInputData(String chatName, String user, List<String> users, String currentUserId) {
        this.chatName = chatName;
        this.user = user;
        this.users = users;
        this.currentUserId = currentUserId;
    }

    /**
     * Executes the Create Single Chat Use Case.
     * @param chatName for name of chat
     * @param user for name of user
     * @param currentUserId for current logged-in user's ID
     * @return the InputData
     */
    public static CreateGroupChannelInputData forSingleChat(String chatName, String user, String currentUserId) {
        return new CreateGroupChannelInputData(chatName, user, null, currentUserId);
    }

    /**
     * Executes the Create Group Chat Use Case.
     * @param chatName for name of chat
     * @param users for name of users
     * @param currentUserId for current logged-in user's ID
     * @return the InputData
     */
    public static CreateGroupChannelInputData forGroupChat(String chatName, List<String> users, String currentUserId) {
        return new CreateGroupChannelInputData(chatName, null, users, currentUserId);
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

    public String getCurrentUserId() {
        return currentUserId;
    }
}
