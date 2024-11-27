package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class SbGroupChannelFactoryTest {
    private SbGroupChannelFactory sbGroupChannelFactory;

    @BeforeEach
    void setUp() {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        sbGroupChannelFactory = new SbGroupChannelFactory(defaultClient);
    }

    @Test
    void create() {
        List testUserIds = new ArrayList();
        testUserIds.add("user1");
        testUserIds.add("user2");
        testUserIds.add("user3");

        String testName = "group name";

        SendBirdGroupChannel createdChannel = sbGroupChannelFactory.create(testUserIds, testName);

        assertNotNull(createdChannel);
        assertEquals(testName, createdChannel.getName());
    }
}