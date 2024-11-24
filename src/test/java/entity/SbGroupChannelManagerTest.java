package entity;

import org.junit.Test;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SbGroupChannelManagerTest {
    @Test
    public void createChannel() {
        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        SbGroupChannelManager sbGroupChannelManager = new SbGroupChannelManager(defaultClient);

        List<String> userIds = List.of("9fe8dffb-30a8-4125-8882-c24e0d5efc52", "Test User Id");
        String channelName = "Test";

        SendBirdGroupChannel newChannel = sbGroupChannelManager.createChannel(userIds, channelName);
        assertEquals(channelName, newChannel.getName());
    }
}
