package use_case.choose_group_channel;

import java.util.ArrayList;
import java.util.List;

/**
 * The Input Data for the ChooseGroupChannel Use Case.
 */
public class ChooseGroupChannelInputData {
    private final String chatName;
    private final String user;
    private final List<String> users;
    private final String groupChannelUrl;

    private ChooseGroupChannelInputData(String chatName, String user, List<String> users, String groupChannelUrl) {
        this.chatName = chatName;
        this.user = user;
        this.users = users;
        this.groupChannelUrl = groupChannelUrl;
    }

    public ChooseGroupChannelInputData(String groupChannelUrl) {
        this.chatName = "";
        this.user = "";
        this.users = new ArrayList<String>();
        this.groupChannelUrl = groupChannelUrl;
    }

    public static ChooseGroupChannelInputData forSingleChat(String chatName, String user) {
        return new ChooseGroupChannelInputData(chatName, user, null, null);
    }

    public static ChooseGroupChannelInputData forGroupChat(String chatName, List<String> users) {
        return new ChooseGroupChannelInputData(chatName, null, users, null);
    }

    String getGroupChannelUrl() {
        return groupChannelUrl;
    }
}
