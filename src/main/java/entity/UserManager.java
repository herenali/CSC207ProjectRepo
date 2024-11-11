package entity;

import org.openapitools.client.model.SendBirdUser;

public interface UserManager {
    public SendBirdUser updateUserById(String userId, String nickname);

    public SendBirdUser deleteUserById(String userId);

    public void listUsers();
}
