package interface_adapter.change_password;

import java.util.ArrayList;
import java.util.List;

/**
 * The State information representing the logged-in user.
 */
public class LoggedInState {
    private String username = "";

    private String password = "";
    private String passwordError;

    private String userId = "";

    private String groupChannelUrl = "";
    private List<List<String>> usersAndMessages;
    private List<List<String>> userAndMessageIds;
    private List<List<String>> groupChannelUrls = new ArrayList<>();

    public LoggedInState(LoggedInState copy) {
        username = copy.username;
        password = copy.password;
        passwordError = copy.passwordError;
        userId = copy.userId;
        groupChannelUrl = copy.groupChannelUrl;
        usersAndMessages = copy.usersAndMessages;
        groupChannelUrls = copy.groupChannelUrls;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {
        this.usersAndMessages = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setGroupChannelUrl(String groupChannelUrl) {
        this.groupChannelUrl = groupChannelUrl;
    }

    public String getGroupChannelUrl() {
        return groupChannelUrl;
    }

    public void setUsersAndMessages(List<List<String>> usersAndMessages) {
        this.usersAndMessages = usersAndMessages;
    }

    public List<List<String>> getUsersAndMessages() {
        return usersAndMessages;
    }

    public void setUserAndMessageIds(List<List<String>> userAndMessageIds) {
        this.userAndMessageIds = userAndMessageIds;
    }

    public List<List<String>> getUserAndMessageIds() {
        return userAndMessageIds;
    }

    /**
     * Adds group channel URL to groupChannelUrls.
     *
     * @param newGroupChannelUrl URL of group channel to add.
     */
    public void addGroupChannelUrl(String newGroupChannelUrl) {
        this.groupChannelUrls.add(List.of(newGroupChannelUrl));
    }
}
