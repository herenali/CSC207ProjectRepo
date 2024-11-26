package entity;

import org.junit.Before;
import org.junit.Test;
import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import data_access.InMemoryUserDataAccessObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;


public class SbGroupChannelManagerTest {
    private static SbGroupChannelManager sbGroupChannelManager;
    private static String userPaulId;
    private static String userJonathanId;

    @Before
    public void initAll() {
        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";


        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");


        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        sbGroupChannelManager = new SbGroupChannelManager(defaultClient);
        SbUserManager sbUserManager = new SbUserManager(defaultClient);

        userPaulId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        userJonathanId = "11415872-17cb-47ff-a986-ed7c1b63760c";
        String groupChannelUrl = "sendbird_group_channel_17729697_fbf1838c39e6d07e9cc4b3d68d1a5f35eae4312f";
    }

    @Test
    public void testcreateChannel() {
        List<String> userIds = Arrays.asList(userPaulId, userJonathanId);
        String channelName = "Test";

        SendBirdGroupChannel result = sbGroupChannelManager.createChannel(userIds, channelName);

        assertNotNull("Resulting channel should not be null", result);
        assertEquals("Channel name should match the expected value", channelName, result.getName());
        assertNotNull("Channel URL should not be null", result.getChannelUrl());
    }

    @Test
    public void testListChannel() {
        List<String> userIds = Arrays.asList(userPaulId, userJonathanId);
        String channelName = "Test";

        SendBirdGroupChannel result = sbGroupChannelManager.createChannel(userIds, channelName);

        var paulsChannelsResponse = sbGroupChannelManager.listChannels(userPaulId);
        assertNotNull("Paul's channel list response should not be null", paulsChannelsResponse);
        List<SendBirdGroupChannel> paulsChannels = paulsChannelsResponse.getChannels();

        var jonathansChannelsResponse = sbGroupChannelManager.listChannels(userJonathanId);
        assertNotNull("Jonathan's channel list response should not be null", jonathansChannelsResponse);
        List<SendBirdGroupChannel> jonathansChannels = jonathansChannelsResponse.getChannels();

        assert paulsChannels != null;
        assertTrue("Paul's channel list should contain the new created chat. ",
                paulsChannels.stream()
                        .anyMatch(channel -> Objects.equals(channel.getChannelUrl(), result.getChannelUrl()))
        );

        assert jonathansChannels != null;
        assertTrue("Jonathan's channel list should contain the new created chat. ",
                jonathansChannels.stream()
                        .anyMatch(channel -> Objects.equals(channel.getChannelUrl(), result.getChannelUrl()))
        );
    }

    @Test
    public void testdeleteChannel() {
        List<String> userIds = Arrays.asList(userPaulId, userJonathanId);
        String channelName = "Test Delete";

        SendBirdGroupChannel result = sbGroupChannelManager.createChannel(userIds, channelName);
        assertNotNull("The created channel should not be null", result);
        String channelUrl = result.getChannelUrl();

        // Delete the channel
        OcDeleteChannelByUrl200Response deleteResponse = sbGroupChannelManager.deleteChannelByUrl(channelUrl);
        assertNotNull("Delete channel response should not be null", deleteResponse);

        var paulsChannelsResponse = sbGroupChannelManager.listChannels(userPaulId);
        assertNotNull("Paul's channel list response should not be null", paulsChannelsResponse);
        List<SendBirdGroupChannel> paulsChannels = paulsChannelsResponse.getChannels();

        var jonathansChannelsResponse = sbGroupChannelManager.listChannels(userJonathanId);
        assertNotNull("Jonathan's channel list response should not be null", jonathansChannelsResponse);
        List<SendBirdGroupChannel> jonathansChannels = jonathansChannelsResponse.getChannels();

        assert paulsChannels != null;
        assertFalse("Paul's channel list should no longer contain the deleted channel",
                paulsChannels.stream().anyMatch(channel -> Objects.equals(channel.getChannelUrl(), channelUrl)));

        assert jonathansChannels != null;
        assertFalse("Jonathan's channel list should no longer contain the deleted channel",
                jonathansChannels.stream().anyMatch(channel -> Objects.equals(channel.getChannelUrl(), channelUrl)));
    }
}
