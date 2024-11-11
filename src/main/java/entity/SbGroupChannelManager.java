package entity;

import app.Config;
import org.openapitools.client.model.GcCreateChannelData;
import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.GroupChannelApi;

import java.util.List;

public class SbGroupChannelManager implements GroupChannelManager {
    ApiClient defaultClient;
    String apiToken;
    GroupChannelApi apiInstance;

    public SbGroupChannelManager(ApiClient defaultClient) {
        apiInstance = new GroupChannelApi(defaultClient);
        apiToken = Config.apiToken;
    }

    public SendBirdGroupChannel createChannel(List userIds, String name) {
        GcCreateChannelData gcCreateChannelData = new GcCreateChannelData();
        gcCreateChannelData.name(name);

        gcCreateChannelData.userIds(userIds);
        gcCreateChannelData.isDistinct(false);

        try {
            SendBirdGroupChannel result = apiInstance.gcCreateChannel().apiToken(apiToken).gcCreateChannelData(gcCreateChannelData).execute();
            return result;

        } catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#gcCreateChannel");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return null;

    }

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
