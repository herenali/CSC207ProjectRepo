package use_case.send_message;

/**
 * Output Data for the Send Message Use Case.
 */
public class SendMessageOutputData {
    private final String userId;
    private final String message;
    private final boolean useCaseFailed;

    public SendMessageOutputData(String userId, String message, boolean useCaseFailed) {
        this.userId = userId;
        this.message = message;
        this.useCaseFailed = useCaseFailed;
    }

    String getUserId() {
        return userId;
    }

    String getMessage() {
        return message;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
