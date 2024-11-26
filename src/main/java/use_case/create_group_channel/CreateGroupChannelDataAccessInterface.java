package use_case.create_group_channel;

import entity.User;

/**
 * DAO for the Create Group Channel Use Case.
 */
public interface CreateGroupChannelDataAccessInterface {
    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Returns userId of the user with the given username.
     * @param username the username to look up
     * @return the userId of the user with the given username
     */
    String getUserId(String username);

    /**
     * Saves the user.
     * @param user the user to save
     */
    void save(User user);

}

