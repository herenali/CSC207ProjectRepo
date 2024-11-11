package entity;

import app.Config;
import org.openapitools.client.model.*;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.MessageApi;

import java.math.BigDecimal;
import java.util.*;

public class SbMessageManager {
    private ApiClient defaultClient;
    private String apiToken;
    private MessageApi apiInstance;

    // mapping of group channel URLs to a list of message IDs
    private Map<String, List<BigDecimal>> groupMessageMapping;

    public SbMessageManager(ApiClient defaultClient) {
        apiInstance = new MessageApi(defaultClient);
        apiToken = Config.apiToken;
        groupMessageMapping = new HashMap<>();
    }

    public Map<String, List<BigDecimal>> getGroupMessageMapping() {
        return groupMessageMapping;
    }

    public SendBirdMessageResponse sendMessage(String channelType, String channelUrl, String userId, String message, String messageType) {
        final SendMessageData sendMessageData = new SendMessageData();
        sendMessageData.channelType(channelType);
        sendMessageData.channelUrl(channelUrl);
        sendMessageData.userId(userId);
        sendMessageData.message(message);
        sendMessageData.messageType(messageType);

        try {
            final SendBirdMessageResponse result = apiInstance.sendMessage(channelType, channelUrl).apiToken(apiToken).sendMessageData(sendMessageData).execute();
            // add messageID to groupMessageMapping
//            if (groupMessageMapping.containsKey(channelUrl)) {
//                groupMessageMapping.get(channelUrl).add(result.getMessageId());
//            }
//            else {
//                final List<BigDecimal> messageIds = new ArrayList<>();
//                messageIds.add(result.getMessageId());
//                groupMessageMapping.put(channelUrl, messageIds);
//            }
            return result;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#sendMessage");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    public SendBirdMessageResponse sendFileMessage(String channelType, String channelUrl, String userId, String message, String messageType) {
        final SendMessageData sendMessageData = new SendMessageData();
        sendMessageData.channelType(channelType);
        // try file and url
        sendMessageData.url("https://assets.vercel.com/image/upload/v1661135356/front/next-conf-2022/og.png");
        sendMessageData.channelUrl(channelUrl);
        sendMessageData.userId(userId);
        sendMessageData.message(message);
        sendMessageData.messageType(messageType);

        try {
            SendBirdMessageResponse result = apiInstance.sendMessage(channelType, channelUrl).apiToken(apiToken).sendMessageData(sendMessageData).execute();
            return result;

        }
        catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#sendMessage");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    public ListMessagesResponse listMessages(String channelUrl) {
        final String channelType = "group_channels";
        final Date now = new Date();
        final Long timestamp = now.getTime();

        try {
            final ListMessagesResponse result = apiInstance.listMessages(channelType, channelUrl)
                    .apiToken(apiToken)
                    .messageTs(timestamp.toString())
                    .execute();
            return result;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#listMessages");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    public void deleteMessage(String channelType, String channelUrl, String messageId) {
        try {
            final MessageApi.APIdeleteMessageByIdRequest request = apiInstance.deleteMessageById(channelType, channelUrl, messageId).apiToken(apiToken);
            request.execute();
            System.out.println("Message deleted successfully.");
        }
        catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#deleteMessage");
            e.printStackTrace();
        }
    }

    public SendBirdMessageResponse editMessage(String channelType, String channelUrl, String messageId, String userId, String newContent, String messageType) {
        deleteMessage(channelType, channelUrl, messageId);
        return sendMessage(channelType, channelUrl, userId, newContent, messageType);
    }

    private GcViewNumberOfEachMembersUnreadMessagesResponse getNumberOfUnreadMessages(String channelUrl, String userId) {
        try {
            final GcViewNumberOfEachMembersUnreadMessagesResponse result = apiInstance.gcViewNumberOfEachMembersUnreadMessages(channelUrl)
                    .apiToken(apiToken)
                    .userIds(userId)
                    .execute();
            return result;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#gcViewNumberOfEachMembersUnreadMessages");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    public SendBirdMessageResponse getMessage(String channelType, String channelUrl, String messageId) {
        try {
            final SendBirdMessageResponse result = apiInstance.viewMessageById(channelType, channelUrl, messageId)
                    .apiToken(apiToken)
                    .withSortedMetaArray(false)
                    .withMetaArray(false)
                    .includeParentMessageInfo(false)
                    .execute();
            return result;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#viewMessageById");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    private void markAllMessagesAsRead(String channelUrl, String userId) {
        // Note: this function currently raises an error because of a bug with the API
        GcMarkAllMessagesAsReadData gcMarkAllMessagesAsReadData = new GcMarkAllMessagesAsReadData();
        gcMarkAllMessagesAsReadData.setChannelUrl(channelUrl);
        gcMarkAllMessagesAsReadData.setUserId(userId);
        gcMarkAllMessagesAsReadData.setTimestamp(0);
        try {
            final Object result = apiInstance.gcMarkAllMessagesAsRead(channelUrl)
                    .apiToken(apiToken)
                    .gcMarkAllMessagesAsReadData(gcMarkAllMessagesAsReadData)
                    .execute();
        }
        catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#gcMarkAllMessagesAsRead");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    public List<ListMessagesResponseMessagesInner> getUnreadMessages(String channelUrl, String userId) {
        final List<ListMessagesResponseMessagesInner> unreadMessages = new ArrayList<>();

        final GcViewNumberOfEachMembersUnreadMessagesResponse unreadMessagesResponse = getNumberOfUnreadMessages(channelUrl, userId);
        final int numUnreadMessages = unreadMessagesResponse.getUnread().get(userId).intValue();
        final List<ListMessagesResponseMessagesInner> messages = listMessages(channelUrl).getMessages();

        // loop through list of all messages to get unread messages
        System.out.println(messages.size());
        System.out.println(numUnreadMessages);
        int startingIndex;
        if (numUnreadMessages > messages.size()) {
            startingIndex = 0;
        }
        else {
            startingIndex = messages.size() - numUnreadMessages;
        }
        for (int i = startingIndex; i < messages.size(); i++) {
            unreadMessages.add(messages.get(i));
        }

        // mark all messages as read
        // markAllMessagesAsRead(channelUrl, userId);

        return unreadMessages;
    }

    public List<ListMessagesResponseMessagesInner> getAllMessages(String channelUrl) {
        final List<ListMessagesResponseMessagesInner> allMessages = new ArrayList<>();

        final List<ListMessagesResponseMessagesInner> messages = listMessages(channelUrl).getMessages();

        for (int i = 0; i < messages.size(); i++) {
            allMessages.add(messages.get(i));
        }
        return allMessages;
    }
}
