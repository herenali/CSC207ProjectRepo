package use_case.send_message;

/**
 * The Input Data for the Send Message Use Case.
 */
public class SendMessageInputData {
    private final String message;

    public SendMessageInputData(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }
}
