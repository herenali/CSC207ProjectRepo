package entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import data_access.InMemoryUserDataAccessObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;


public class SbGroupChannelManagerTest {
    private static SbGroupChannelManager sbGroupChannelManager;
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


        userPaulId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        userJonathanId = "11415872-17cb-47ff-a986-ed7c1b63760c";
        groupChannelUrl = "sendbird_group_channel_17729697_fbf1838c39e6d07e9cc4b3d68d1a5f35eae4312f";
    }

    @Test
    public void testcreateChannel() {
        List<String> userIds = Arrays.asList(userPaulId, userJonathanId);
        String channelName = "Test";

        SendBirdGroupChannel result = sbGroupChannelManager.createChannel(userIds, channelName);

        assertNotNull("Resulting channel should not be null", result);
        assertEquals("Channel name should match the expected value", channelName, result.getName());
        assertNotNull("Channel URL should not be null", result.getChannelUrl());

//        SbUserManager userChannels = SbUserManager.listGroupChannelsByUserId(userPaulId);
//        assertNotNull("Failed to retrieve user channels.", userChannels);
//        boolean isChannelFound = userChannels.getChannels().stream()
//                .anyMatch(channel -> channel.getName().equals(channelName));
//        assertTrue("Created channel not found in user's channels.", isChannelFound);

    }

    @Test
    public void testdeleteChannel() {
        OcDeleteChannelByUrl200Response expectedResponse = new OcDeleteChannelByUrl200Response();
        OcDeleteChannelByUrl200Response result = sbGroupChannelManager.deleteChannelByUrl(groupChannelUrl);
        assertNotNull(result);
    }
}
