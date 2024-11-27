package entity;

import org.junit.Test;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SbUserManagerTest {
    @Test
    public void listGroupChannelsByUserId() {
        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        SbGroupChannelManager sbGroupChannelManager = new SbGroupChannelManager(defaultClient);

        String paulUserId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        List<SendBirdGroupChannel> groupChannels = sbGroupChannelManager.listChannels(paulUserId).getChannels();
        assertEquals("sendbird_group_channel_17729697_c93830e104f4c6ddcd7d6131ca6b03338cf93209",
                groupChannels.get(0).getChannelUrl());
    }
}
