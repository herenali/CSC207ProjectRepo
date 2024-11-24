package use_case.create_group_channel;

/**
 * Output Data for the Choose Group Channel Use Case.
 */
public class CreateGroupChannelOutputData {
    private final String message;

    public CreateGroupChannelOutputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
