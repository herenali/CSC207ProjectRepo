package entity;

import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;

import java.util.List;

public class SbGroupChannelFactory {
    private ApiClient apiClient;

    public SbGroupChannelFactory(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public SendBirdGroupChannel create(List userIds, String name) {
        final GroupChannelManager groupChannelManager = new SbGroupChannelManager(apiClient);
        return groupChannelManager.createChannel(userIds, name);
    }
}
