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

        String paulUserId = "39bd6921-ef43-44bf-b8eb-f5394ca5272f";
        List<SendBirdGroupChannel> groupChannels = sbGroupChannelManager.listChannels(paulUserId).getChannels();
        assertEquals("sendbird_group_channel_19639730_b073d0cb90db5a1dfe40d7eca18b33d4b187fb63",
                groupChannels.get(0).getChannelUrl());
    }
}
