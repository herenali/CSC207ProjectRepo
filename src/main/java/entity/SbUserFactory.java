package entity;

import org.sendbird.client.ApiClient;

public class SbUserFactory implements UserFactory {
    private ApiClient apiClient;

    public SbUserFactory(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public User create(String name, String password) {
        return new SbUser(apiClient, name, password);
    }
}
