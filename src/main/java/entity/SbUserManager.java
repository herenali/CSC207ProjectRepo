package entity;

import org.openapitools.client.model.ListMyGroupChannelsResponse;
import org.openapitools.client.model.ListUsersResponse;
import org.openapitools.client.model.SendBirdUser;
import org.openapitools.client.model.UpdateUserByIdData;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.UserApi;

import app.Config;

/**
 * Class for managing users.
 */
public class SbUserManager implements UserManager {
    private String apiToken;
    private UserApi apiInstance;

    public SbUserManager(ApiClient defaultClient) {
        this.apiInstance = new UserApi(defaultClient);
        this.apiToken = Config.getApiToken();
    }

    /**
     * Updates username of given userID.
     * @param userId ID of user to change username for.
     * @param nickname new username to change to.
     * @return the updated user.
     */
    @Override
    public SendBirdUser updateUserById(String userId, String nickname) {
        final UpdateUserByIdData updateUserByIdData = new UpdateUserByIdData();
        updateUserByIdData.nickname(nickname);
        try {
            final SendBirdUser result = apiInstance.updateUserById(userId)
                    .apiToken(apiToken).updateUserByIdData(updateUserByIdData).execute();
            return result;
        }
        catch (ApiException ex) {
            System.err.println("Exception when calling UserApi#createUser");
            System.err.println("Status code: " + ex.getCode());
            System.err.println("Reason: " + ex.getResponseBody());
            System.err.println("Response headers: " + ex.getResponseHeaders());
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes user.
     * @param userId ID of user to delete.
     * @return the deleted user.
     */
    @Override
    public SendBirdUser deleteUserById(String userId) {
        try {
            apiInstance.deleteUserById(userId).apiToken(apiToken).execute();
        }
        catch (ApiException ex) {
            System.err.println("Exception when calling UserApi#createUser");
            System.err.println("Status code: " + ex.getCode());
            System.err.println("Reason: " + ex.getResponseBody());
            System.err.println("Response headers: " + ex.getResponseHeaders());
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Lists all users.
     */
    @Override
    public void listUsers() {
        try {
            final Integer limit = 56;
            final String activeMode = "activated";
            final Boolean showBot = true;
            final ListUsersResponse result = apiInstance.listUsers().limit(limit).activeMode(activeMode).execute();
            System.out.println(result);
        }
        catch (ApiException ex) {
            System.err.println("Exception when calling listUsers");
            System.err.println("Status code: " + ex.getCode());
            System.err.println("Reason: " + ex.getResponseBody());
            System.err.println("Response headers: " + ex.getResponseHeaders());
            ex.printStackTrace();
        }
    }
}
