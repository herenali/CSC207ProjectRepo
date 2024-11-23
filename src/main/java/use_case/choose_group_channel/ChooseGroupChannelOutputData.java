package use_case.choose_group_channel;

import org.openapitools.client.model.ListMessagesResponse;
import org.openapitools.client.model.ListMessagesResponseMessagesInner;

import java.util.ArrayList;
import java.util.List;

/**
 * Output Data for the Choose Group Channel Use Case.
 */
public class ChooseGroupChannelOutputData {
    private final ListMessagesResponse messagesResponse;
    // List of messages for that group channel
    // Each message is stored as a list of a user and their message
    private final List<List<String>> usersAndMessages;
    private final List<List<String>> userAndMessageIds;

    public ChooseGroupChannelOutputData(ListMessagesResponse messagesResponse) {
        this.messagesResponse = messagesResponse;
        this.usersAndMessages = new ArrayList<>();
        this.userAndMessageIds = new ArrayList<>();

        for (ListMessagesResponseMessagesInner message : messagesResponse.getMessages()) {
            final List<String> userAndMessage = new ArrayList<>();
            userAndMessage.add(message.getUser().getNickname());
            userAndMessage.add(message.getMessage());
            usersAndMessages.add(userAndMessage);

            final List<String> userAndMessageId = new ArrayList<>();
            userAndMessageId.add(message.getUser().getUserId());
            userAndMessageId.add(String.valueOf(message.getMessageId()));
            userAndMessageIds.add(userAndMessageId);
        }
    }

    public ListMessagesResponse getMessagesResponse() {
        return messagesResponse;
    }

    public List<List<String>> getUsersAndMessages() {
        return usersAndMessages;
    }

    public List<List<String>> getUserAndMessageIds() {
        return userAndMessageIds;
    }
}
