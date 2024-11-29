package use_case.edit_message;

import org.openapitools.client.model.SendBirdMessageResponse;

/**
 * Output Data for the Edit Message Use Case.
 */
public class EditMessageOutputData {
    private final SendBirdMessageResponse message;
    private final boolean useCaseFailed;

    public EditMessageOutputData(SendBirdMessageResponse message, boolean useCaseFailed) {
        this.message = message;
        this.useCaseFailed = useCaseFailed;
    }

    public SendBirdMessageResponse getMessage() {
        return message;
    }
  
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
