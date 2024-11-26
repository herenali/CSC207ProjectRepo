package entity;

import java.util.List;

import org.openapitools.client.model.GcCreateChannelData;
import org.openapitools.client.model.GcListChannelsResponse;
import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.GroupChannelApi;

import app.Config;

/**
 * Class for managing group channels.
 */
public class SbGroupChannelManager implements GroupChannelManager {
    private String apiToken;
    private GroupChannelApi apiInstance;

    public SbGroupChannelManager(ApiClient defaultClient) {
        if (defaultClient == null) {
            throw new IllegalArgumentException("ApiClient cannot be null");
        }
        setApiInstance(new GroupChannelApi(defaultClient));
        setApiToken(Config.getApiToken());
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

        try {
            System.out.println("Creating channel with data: " + gcCreateChannelData);
            SendBirdGroupChannel result = getApiInstance().gcCreateChannel().apiToken(getApiToken()).gcCreateChannelData(gcCreateChannelData).execute();
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

    /**
     * Deletes selected group channel.
     * @param channelUrl URL of channel to be deleted
     * @return The deleted group channel.
     */
    @Override
    public OcDeleteChannelByUrl200Response deleteChannelByUrl(String channelUrl) {
        try {
            final OcDeleteChannelByUrl200Response result = getApiInstance()
                    .gcDeleteChannelByUrl(channelUrl).apiToken(getApiToken()).execute();
            return result;
        }
        catch (ApiException ex) {
            System.err.println("Exception when calling GroupChannelApi#gcViewChannelByUrl");
            System.err.println("Status code: " + ex.getCode());
            System.err.println("Reason: " + ex.getResponseBody());
            System.err.println("Response headers: " + ex.getResponseHeaders());
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Lists group channels user is in.
     * @param userId ID of user to list channels for.
     * @return The list of group channels.
     */
    @Override
    public GcListChannelsResponse listChannels(String userId) {
        try {
            final GcListChannelsResponse result = getApiInstance().gcListChannels()
                    .apiToken(getApiToken())
                    .membersIncludeIn(userId)
                    .execute();
            return result;
        }
        catch (ApiException ex) {
            System.err.println("Exception when calling GroupChannelApi#gcViewChannelByUrl");
            System.err.println("Status code: " + ex.getCode());
            System.err.println("Reason: " + ex.getResponseBody());
            System.err.println("Response headers: " + ex.getResponseHeaders());
            ex.printStackTrace();
        }

        return null;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public GroupChannelApi getApiInstance() {
        return apiInstance;
    }

    public void setApiInstance(GroupChannelApi apiInstance) {
        this.apiInstance = apiInstance;
    }
}
