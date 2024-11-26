package use_case.create_group_channel;

import java.util.List;

/**
 * The Input Data for the ChooseGroupChannel Use Case.
 */
public final class CreateGroupChannelInputData {
    private final String chatName;
    private final List<String> users;
    private final String currentUserId;

    public CreateGroupChannelInputData(String chatName, List<String> users, String currentUserId) {
        this.chatName = chatName;
        this.users = users;
        this.currentUserId = currentUserId;
    }

    public String getChatName() {
        return chatName;
    }

    public List<String> getUsers() {
        return users;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }
}
