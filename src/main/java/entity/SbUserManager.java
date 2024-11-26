package entity;

import app.Config;
import org.openapitools.client.model.ListMyGroupChannelsResponse;
import org.openapitools.client.model.ListUsersResponse;
import org.openapitools.client.model.SendBirdUser;
import org.openapitools.client.model.UpdateUserByIdData;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.UserApi;

/**
 * Class for managing users.
 */
public class SbUserManager implements UserManager {
    private String apiToken;
    private UserApi apiInstance;

    public SbUserManager(ApiClient defaultClient) {
        this.apiInstance = new UserApi(defaultClient);
        this.apiToken = Config.apiToken;
    }

    @Override
    public SendBirdUser updateUserById(String userId, String nickname) {
        UpdateUserByIdData updateUserByIdData = new UpdateUserByIdData();
        updateUserByIdData.nickname(nickname);
        try {
            SendBirdUser result = apiInstance.updateUserById(userId).apiToken(apiToken).updateUserByIdData(updateUserByIdData).execute();
            return result;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling UserApi#createUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SendBirdUser deleteUserById(String userId) {
        try {
            apiInstance.deleteUserById(userId).apiToken(apiToken).execute();
        }
        catch (ApiException e) {
            System.err.println("Exception when calling UserApi#createUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void listUsers() {
        try {
            final Integer limit = 56;
            final String activeMode = "activated";
            final Boolean showBot = true;
            final ListUsersResponse result = apiInstance.listUsers().limit(limit).activeMode(activeMode).execute();
            System.out.println(result);

        }
        catch (ApiException e) {
            System.err.println("Exception when calling listUsers");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    @Override
    public ListMyGroupChannelsResponse listGroupChannelsByUserId(String userId) {
        // TODO: remove this method
        try {
            ListMyGroupChannelsResponse result = apiInstance.listMyGroupChannels(userId).execute();
            return result;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling listUsers");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
}
