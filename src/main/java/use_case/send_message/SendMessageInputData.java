package use_case.send_message;

/**
 * The Input Data for the Send Message Use Case.
 */
public class SendMessageInputData {
    private final String userId;
    private final String groupChannelUrl;
    private final String message;

    public SendMessageInputData(String userId, String groupChannelUrl, String message) {
        this.userId = userId;
        this.groupChannelUrl = groupChannelUrl;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupChannelUrl() {
        return groupChannelUrl;
    }

    public String getMessage() {
        return message;
    }
}
