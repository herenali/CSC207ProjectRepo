package entity;

import java.math.BigDecimal;
import java.util.*;

import org.openapitools.client.model.*;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.MessageApi;

import app.Config;

/**
 * Class for managing messages.
 */
public class SbMessageManager {
    private String apiToken;
    private MessageApi apiInstance;
    private Map<String, List<BigDecimal>> groupMessageMapping;

    public SbMessageManager(ApiClient defaultClient) {
        apiInstance = new MessageApi(defaultClient);
        apiToken = Config.getApiToken();
    }

    /**
     * Sends a message.
     * @param channelType the type of the group channel
     * @param channelUrl the url of the group channel
     * @param userId the id of the user
     * @param message the message
     * @param messageType the type of the message
     * @return a response containing the details of the message
     */
    public SendBirdMessageResponse sendMessage(String channelType, String channelUrl, String userId, String message, String messageType) {
        final SendMessageData sendMessageData = new SendMessageData();
        sendMessageData.channelType(channelType);
        sendMessageData.channelUrl(channelUrl);
        sendMessageData.userId(userId);
        sendMessageData.message(message);
        sendMessageData.messageType(messageType);

        try {
            final SendBirdMessageResponse result = apiInstance.sendMessage(channelType, channelUrl).apiToken(apiToken).sendMessageData(sendMessageData).execute();
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

    /**
     * Lists the messages for a given group channel.
     * @param channelUrl the url of the group channel
     * @return a response containing the details of the messages
     */
    public ListMessagesResponse listMessages(String channelUrl) {
        final String channelType = "group_channels";
        final Date now = new Date();
        final Long timestamp = now.getTime();

        try {
            return apiInstance.listMessages(channelType, channelUrl)
                    .apiToken(apiToken)
                    .messageTs(timestamp.toString())
                    .execute();
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

    /**
     * Deletes a message.
     * @param channelType the type of the group channel
     * @param channelUrl the url of the group channel
     * @param messageId the id of the message
     */
    public void deleteMessage(String channelType, String channelUrl, String messageId) {
        try {
            final MessageApi.APIdeleteMessageByIdRequest request =
                    apiInstance.deleteMessageById(channelType, channelUrl, messageId).apiToken(apiToken);
            request.execute();
            System.out.println("Message deleted successfully.");
        }
        catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#deleteMessage");
            e.printStackTrace();
        }
    }
  
    /**
     * Edits a message.
     * @param channelType the type of the group channel
     * @param channelUrl the url of the group channel
     * @param messageId the id of the message
     * @param userId the id of the user
     * @param newContent the new content of the message
     * @param messageType the type of the message
     * @return a response containing the details of the message
     */
    public SendBirdMessageResponse editMessage(String channelType, String channelUrl, int messageId, String userId, String newContent, String messageType) {
        final UpdateMessageByIdData updateMessageByIdData = new UpdateMessageByIdData();
        updateMessageByIdData.channelType(channelType)
                .channelUrl(channelUrl)
                .messageId(messageId)
                .messageType(messageType)
                .message(newContent);

        try {
            final SendBirdMessageResponse updatedMessage = apiInstance.updateMessageById(channelType, channelUrl, String.valueOf(messageId))
                    .apiToken(apiToken)
                    .updateMessageByIdData(updateMessageByIdData)
                    .execute();
            return updatedMessage;
        } catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#updateMessageById");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a message by its id.
     * @param channelType the type of the group channel
     * @param channelUrl the url of the group channel
     * @param messageId the id of the message
     * @return a response containing the details of the message
     */
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

    /**
     * Gets all messages from a given channel as a list.
     * @param channelUrl the url of the group channel
     * @return a list of responses containing the details of the message
     */
    public List<ListMessagesResponseMessagesInner> getAllMessages(String channelUrl) {
        final List<ListMessagesResponseMessagesInner> allMessages = new ArrayList<>();

        final List<ListMessagesResponseMessagesInner> messages = listMessages(channelUrl).getMessages();

        for (ListMessagesResponseMessagesInner message : messages) {
            allMessages.add(message);
        }
        return allMessages;
    }
}
