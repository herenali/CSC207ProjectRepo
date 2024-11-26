package entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openapitools.client.model.GcListChannelsResponse;
import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.openapitools.client.model.SendBirdMember;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import data_access.InMemoryUserDataAccessObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import entity.SbUserManager;
import static org.junit.Assert.*;

public class SbGroupChannelManagerTest {
    private static SbGroupChannelManager sbGroupChannelManager;
    private static SbUserManager sbUserManager;
    private static String userPaulId;
    private static String userJonathanId;
    private static String groupChannelUrl;
    private InMemoryUserDataAccessObject userRepository;

    @Before
    public void initAll() {
        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";


        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");


        userRepository = new InMemoryUserDataAccessObject();
        sbGroupChannelManager = new SbGroupChannelManager(defaultClient);
        sbUserManager = new SbUserManager(defaultClient);

        userPaulId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        userJonathanId = "11415872-17cb-47ff-a986-ed7c1b63760c";
        SendBirdGroupChannel result = sbGroupChannelManager.createChannel(Arrays.asList(userPaulId, userJonathanId), "Test");
        groupChannelUrl = result.getChannelUrl();
    }

    @Test
    public void testCreateManagerFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SbGroupChannelManager(null);
        });
    }

    @Test
    public void testCreateChannel() {
        List<String> userIds = Arrays.asList(userPaulId, userJonathanId);
        String channelName = "Test";

        SendBirdGroupChannel result = sbGroupChannelManager.createChannel(userIds, channelName);

        assertNotNull("Resulting channel should not be null", result);
        assertEquals("Channel name should match the expected value", channelName, result.getName());
        assertNotNull("Channel URL should not be null", result.getChannelUrl());

        System.out.println(result.getMembers());
        System.out.println(result.getMemberCount());

//        List<SendBirdGroupChannel> paulChannels = sbUserManager.listGroupChannelsByUserId(userPaulId).getChannels();
//        assertTrue("Paul's channel list should contain the new channel", paulChannels.stream()
//                .anyMatch(channel -> channel.getChannelUrl().equals(result.getChannelUrl())));
//
//        List<SendBirdGroupChannel> jonathanChannels = sbUserManager.listGroupChannelsByUserId(userJonathanId).getChannels();
//        assertTrue("Jonathan's channel list should contain the new channel", jonathanChannels.stream()
//                .anyMatch(channel -> channel.getChannelUrl().equals(result.getChannelUrl())));

    }

    @Test
    public void testCreateChannelFail() {
        assertNull(sbGroupChannelManager.createChannel(null, null));
    }

    @Test
    public void testdeleteChannel() {
        OcDeleteChannelByUrl200Response expectedResponse = new OcDeleteChannelByUrl200Response();
        OcDeleteChannelByUrl200Response result = sbGroupChannelManager.deleteChannelByUrl(groupChannelUrl);
        assertNotNull(result);
    }

    @Test
    public void testDeleteChannelFail() {
        assertNull(sbGroupChannelManager.deleteChannelByUrl(null));
    }

    @Test
    public void testListChannels() {
        GcListChannelsResponse result = sbGroupChannelManager.listChannels(userPaulId);
        assertNotNull(result);
    }
}