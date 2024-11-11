package use_case.send_message;

/**
 * Output Data for the Send Message Use Case.
 */
public class SendMessageOutputData {
    private final String message;
    private final boolean useCaseFailed;

    public SendMessageOutputData(String message, boolean useCaseFailed) {
        this.message = message;
        this.useCaseFailed = useCaseFailed;
    }

    public String getMessage() {
        return message;
    }
  
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
