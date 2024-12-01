package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import org.openapitools.client.model.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

class SbMessageManagerTest {
    private static SbMessageManager sbMessageManager;
    private static SbGroupChannelManager sbGroupChannelManager;

    private static String userPaulId;
    private static String userJonathanId;

    private static String groupChannelUrl;

    @BeforeEach
    void initAll() {
        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        sbMessageManager = new SbMessageManager(defaultClient);
        sbGroupChannelManager = new SbGroupChannelManager(defaultClient);

        // The users and group channel has already been created, we can use their id/url values directly
        userPaulId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        userJonathanId = "11415872-17cb-47ff-a986-ed7c1b63760c";
        SendBirdGroupChannel result = sbGroupChannelManager.createChannel(Arrays.asList(userPaulId, userJonathanId), "Test");
        groupChannelUrl = result.getChannelUrl();
    }

    @Test
    void sendMessage() {
        BigDecimal messageId = sbMessageManager.sendMessage("group_channels",
                groupChannelUrl,
                userPaulId,
                "Hello! This is the first message.",
                "MESG").getMessageId();
        System.out.println(sbMessageManager.listMessages(groupChannelUrl).getMessages());
        assertEquals("Hello! This is the first message.",
                sbMessageManager.getMessage("group_channels", groupChannelUrl, messageId.toString())
                        .getMessage());
    }

    @Test
    public void testEditMessage() {
        BigDecimal messageId = sbMessageManager.sendMessage("group_channels",
                groupChannelUrl,
                userPaulId,
                "message before editing",
                "MESG").getMessageId();
        String newContent = "new message after editing";
        SendBirdMessageResponse updatedMessage = sbMessageManager.editMessage("group_channels",
                groupChannelUrl, messageId.intValue(), userPaulId, newContent, "MESG");

        assertEquals(newContent, updatedMessage.getMessage());
    }

    @Test
    public void testEditMessageFail() {
        assertNull(sbMessageManager.editMessage("group_channels", groupChannelUrl, 0,
                null, "", "MESG"));
    }

    @Test
    public void testListMessagesFail() {
        assertNull(sbMessageManager.listMessages(null));
    }

    @Test
    public void testDeleteMessage() {
        BigDecimal messageId = sbMessageManager.sendMessage("group_channels",
                groupChannelUrl,
                userPaulId,
                "message sent",
                "MESG").getMessageId();

        sbMessageManager.deleteMessage("group_channels", groupChannelUrl, String.valueOf(messageId));
    }

    @Test
    public void testDeleteMessageFail() {
        sbMessageManager.deleteMessage("group_channels", groupChannelUrl, null);
    }

    @Test
    public void testGetMessageFail() {
        assertNull(sbMessageManager.getMessage("group_channels", groupChannelUrl, null));
    }

    @Test
    public void testGetAllMessages() {
        List<ListMessagesResponseMessagesInner> result = sbMessageManager.getAllMessages(groupChannelUrl);
        assertNotNull(result);
    }
}
