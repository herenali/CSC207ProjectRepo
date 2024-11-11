package use_case.send_message;

/**
 * DAO for the Login Use Case.
 */
public interface SendMessageDataAccessInterface {
    /**
     * Returns the userId of the current user of the application.
     * @return the userId of the current user; null indicates that no one is logged into the application.
     */
    String getCurrentUserId();

    /**
     * Returns the url of the current group channel.
     * @return the url of the current group channel; null indicates that no group channel has been selected.
     */
    String getCurrentGroupChannelUrl();
}
