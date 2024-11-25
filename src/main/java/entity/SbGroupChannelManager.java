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
        if (defaultClient == null) {
            throw new IllegalArgumentException("ApiClient cannot be null");
        }
        apiInstance = new GroupChannelApi(defaultClient);
        apiToken = Config.apiToken;
    }

    /**
     * Creates a group channel for multiple users.
     * @param userIds A list of user IDs for the group.
     * @param name The name of the channel.
     * @return The created group channel.
     */
    @Override
    public SendBirdGroupChannel createChannel(List userIds, String name) {
        GcCreateChannelData gcCreateChannelData = new GcCreateChannelData();
        gcCreateChannelData.name(name);
        gcCreateChannelData.userIds(userIds);

//        if (userIds.size() == 1) {
//            gcCreateChannelData.isDistinct(true);
//        }

        try {
            System.out.println("Creating channel with data: " + gcCreateChannelData);
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
