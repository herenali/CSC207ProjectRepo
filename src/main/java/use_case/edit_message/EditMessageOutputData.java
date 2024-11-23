package use_case.edit_message;

/**
 * Output Data for the Edit Message Use Case.
 */
public class EditMessageOutputData {
    private final String message;
    private final boolean useCaseFailed;

    public EditMessageOutputData(String message, boolean useCaseFailed) {
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
