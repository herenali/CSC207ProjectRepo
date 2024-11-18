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

        SbUserManager sbUserManager = new SbUserManager(defaultClient);

        String paulUserId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        List<SendBirdGroupChannel> groupChannels = sbUserManager.listGroupChannelsByUserId(paulUserId).getChannels();
        assertEquals(groupChannels.get(0).getChannelUrl(),
                "sendbird_group_channel_17729697_fbf1838c39e6d07e9cc4b3d68d1a5f35eae4312f");
    }
}
