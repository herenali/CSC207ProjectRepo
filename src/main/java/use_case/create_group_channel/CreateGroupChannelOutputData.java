package use_case.create_group_channel;

/**
 * Output Data for the Choose Group Channel Use Case.
 */
public class CreateGroupChannelOutputData {
    private final String message;
    private final boolean useCaseFailed;

    public CreateGroupChannelOutputData(String message, Boolean useCaseFailed) {
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
