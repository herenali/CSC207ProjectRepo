package entity;

import java.util.UUID;

import org.openapitools.client.model.CreateUserData;
import org.openapitools.client.model.SendBirdUser;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.api.UserApi;

import app.Config;

/**
 * The representation of a user in our program.
 */
public class SbUser implements User {
    private final String apiToken;
    private final UserApi apiInstance;

    private final SendBirdUser userData;
    private final String username;
    private final String password;
    private final String userId;

    public SbUser(ApiClient defaultClient, String username, String password) {
        this.apiInstance = new UserApi(defaultClient);
        this.apiToken = Config.getApiToken();

        this.userData = createUser(username);
        this.username = username;
        this.password = password;
        this.userId = this.userData.getUserId();
    }

    /**
     * Creates user using API.
     * @param nickname username chosen by user.
     * @return the user created.
     */
    private SendBirdUser createUser(String nickname) {
        final CreateUserData createUserData = new CreateUserData();
        createUserData.userId(UUID.randomUUID().toString());
        createUserData.nickname(nickname);
        try {
            final SendBirdUser result = apiInstance.createUser().apiToken(apiToken).createUserData(createUserData).execute();
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

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

}
