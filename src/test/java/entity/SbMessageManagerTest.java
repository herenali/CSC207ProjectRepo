package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import org.openapitools.client.model.*;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

class SbMessageManagerTest {
    private static SbMessageManager sbMessageManager;
    private static SbGroupChannelManager sbGroupChannelManager;
    private static SbUserManager sbUserManager;

//    private static User userPaul;
//    private static User userJonathan;
    private static String userPaulId;
    private static String userJonathanId;
//
//    private static SendBirdGroupChannel groupChannel;
    private static String groupChannelUrl;

    @BeforeEach
    void initAll() {
        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        sbMessageManager = new SbMessageManager(defaultClient);
        sbGroupChannelManager = new SbGroupChannelManager(defaultClient);
        sbUserManager = new SbUserManager(defaultClient);

//        SbUserFactory sbUserFactory = new SbUserFactory(defaultClient);
//        userPaul = sbUserFactory.create("Paul", "123");
//        userJonathan = sbUserFactory.create("Jonathan", "csc207");
//        List<String> userIds = new ArrayList<>();
//        userIds.add(userPaul.getUserId());
//        userIds.add(userJonathan.getUserId());

//        SbGroupChannelFactory sbGroupChannelFactory = new SbGroupChannelFactory(defaultClient);
//        groupChannel = sbGroupChannelFactory.create(userIds, "CSC207 Group Chat");

        // The users and group channel has already been created, we can use their id/url values directly
        userPaulId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        userJonathanId = "11415872-17cb-47ff-a986-ed7c1b63760c";
        groupChannelUrl = "sendbird_group_channel_17729697_fbf1838c39e6d07e9cc4b3d68d1a5f35eae4312f";
    }

    @Test
    void sendMessage() {
        BigDecimal messageId = sbMessageManager.sendMessage("group_channels",
                groupChannelUrl,
                userPaulId,
                "Hello! This is the first message.",
                "MESG").getMessageId();
        System.out.println(sbMessageManager.listMessages(groupChannelUrl));
        assertEquals("Hello! This is the first message.",
                sbMessageManager.getMessage("group_channels", groupChannelUrl, messageId.toString())
                        .getMessage());
    }

//    @Test
//    void getUnreadMessages() {
//        sbMessageManager.sendMessage("group_channels",
//                groupChannelUrl,
//                userPaulId,
//                "New message",
//                "MESG");
//        List<ListMessagesResponseMessagesInner> unreadMessages = sbMessageManager
//                .getUnreadMessages(groupChannelUrl, userJonathanId);
//        System.out.println(unreadMessages);
//        assertEquals("New Message", unreadMessages.get(unreadMessages.size() - 1).getMessage());
//    }

    @Test void mapping() {
        sbMessageManager.sendMessage("group_channels",
                groupChannelUrl,
                userPaulId,
                "New message",
                "MESG");
        System.out.println(sbMessageManager.getGroupMessageMapping());
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

}
