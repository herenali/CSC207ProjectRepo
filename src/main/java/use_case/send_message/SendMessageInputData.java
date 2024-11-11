package use_case.send_message;

/**
 * The Input Data for the Send Message Use Case.
 */
public class SendMessageInputData {
    private final String userId;
    private final String message;

    public SendMessageInputData(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    String getUserId() {
        return userId;
    }

    String getMessage() {
        return message;
    }
}
