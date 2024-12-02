package entity;

import java.util.List;

import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;

/**
 * Factory for creating group channels.
 */
public class SbGroupChannelFactory {
    private final ApiClient apiClient;

    public SbGroupChannelFactory(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Creates a new group channel.
     * @param userIds the ids of the users
     * @param name the name of the new group channel
     * @return the new group channel
     */
    public SendBirdGroupChannel create(List<String> userIds, String name) {
        final GroupChannelManager groupChannelManager = new SbGroupChannelManager(apiClient);
        return groupChannelManager.createChannel(userIds, name);
    }
}
