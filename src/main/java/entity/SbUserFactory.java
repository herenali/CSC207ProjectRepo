package entity;

import org.sendbird.client.ApiClient;

/**
 * Factory for creating users.
 */
public class SbUserFactory implements UserFactory {
    private final ApiClient apiClient;

    public SbUserFactory(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public User create(String name, String password) {
        return new SbUser(apiClient, name, password);
    }
}
