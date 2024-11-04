package app;

import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.UserApi;
import org.openapitools.client.model.ListUsersResponse;


class AppTest {
    static class User {
        UserApi apiInstance;
        public User(ApiClient defaultClient){
            apiInstance = new UserApi(defaultClient);
        }
        public void listUsers(){

            try {
                Integer limit = 56;
                String activeMode = "activated";
                Boolean showBot = true;
                ListUsersResponse result = apiInstance.listUsers().limit(limit).activeMode(activeMode).execute();
                System.out.println(result);

            } catch (ApiException e) {
                System.err.println("Exception when calling listUsers");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");
        User user = new User(defaultClient);
        user.listUsers();
    }
}
