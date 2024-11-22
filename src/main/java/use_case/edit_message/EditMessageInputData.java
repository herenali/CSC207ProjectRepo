package use_case.edit_message;

/**
 * The Input Data for the Edit Message Use Case.
 */
public class EditMessageInputData {
    private final String userId;
    private final int messageId;
    private final String groupChannelUrl;
    private final String newMessage;

    public EditMessageInputData(String userId, int messageId, String groupChannelUrl, String newMessage) {
        this.userId = userId;
        this.messageId = messageId;
        this.groupChannelUrl = groupChannelUrl;
        this.newMessage = newMessage;
    }

    public String getUserId() {
        return userId;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getGroupChannelUrl() {
        return groupChannelUrl;
    }

    public String getNewMessage() {
        return newMessage;
    }
}
