package entity;

import app.Config;
import org.openapitools.client.model.GcCreateChannelData;
import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.GroupChannelApi;

import java.util.List;

/**
 * Class for managing group channels.
 */
public class SbGroupChannelManager implements GroupChannelManager {
    String apiToken;
    GroupChannelApi apiInstance;

    public SbGroupChannelManager(ApiClient defaultClient) {
        apiInstance = new GroupChannelApi(defaultClient);
        apiToken = Config.apiToken;
    }

    public SendBirdGroupChannel createChannel(String userId, String name) {
        return createChannel(List.of(userId), name);
    }

    @Override
    public SendBirdGroupChannel createChannel(List userIds, String name) {
        GcCreateChannelData gcCreateChannelData = new GcCreateChannelData();
        gcCreateChannelData.name(name);
        gcCreateChannelData.userIds(userIds);
        gcCreateChannelData.isDistinct(userIds.size() == 1);

        try {
            SendBirdGroupChannel result = apiInstance.gcCreateChannel().apiToken(apiToken).gcCreateChannelData(gcCreateChannelData).execute();
            return result;

        }
        catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#gcCreateChannel");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public OcDeleteChannelByUrl200Response deleteChannelByUrl(String channelUrl) {
        try {
            final OcDeleteChannelByUrl200Response result = apiInstance.gcDeleteChannelByUrl(channelUrl).apiToken(apiToken).execute();
            return result;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#gcViewChannelByUrl");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return null;
    }

}
