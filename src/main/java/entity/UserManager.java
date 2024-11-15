package entity;

import org.openapitools.client.model.SendBirdUser;

/**
 * Class for managing users.
 */
public interface UserManager {
    /**
     * Updates the nickname of a user.
     * @param userId the id of the user
     * @param nickname the new nickname of the user
     * @return the user
     */
    SendBirdUser updateUserById(String userId, String nickname);

    /**
     * Deletes a user.
     * @param userId the id of the user
     * @return the deleted user
     */
    SendBirdUser deleteUserById(String userId);

    /**
     * Prints out the users of the chat app.
     */
    void listUsers();
}
