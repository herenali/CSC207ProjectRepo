package app;

/**
 * Config class for API configurations.
 */
public class Config {
    // set your api key here
    private static String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
    // set app id here
    private static String appId = "049E2510-3508-4C99-80F9-A3C24ECA7677";

    public static String getApiToken() {
        return apiToken;
    }

    public static void setApiToken(String apiToken) {
        Config.apiToken = apiToken;
    }

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        Config.appId = appId;
    }
}
