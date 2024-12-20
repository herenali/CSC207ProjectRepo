package use_case.send_message;

/**
 * Output Data for the Send Message Use Case.
 */
public class SendMessageOutputData {
    private final String message;

    public SendMessageOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
