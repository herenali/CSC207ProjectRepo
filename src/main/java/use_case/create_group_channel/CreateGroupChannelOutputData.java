package use_case.create_group_channel;

/**
 * Output Data for the Choose Group Channel Use Case.
 */
public class CreateGroupChannelOutputData {
    private final String channelUrl;
    private final String message;
    private final boolean useCaseFailed;

    public CreateGroupChannelOutputData(String channelUrl, String message, Boolean useCaseFailed) {
        this.channelUrl = channelUrl;
        this.message = message;
        this.useCaseFailed = useCaseFailed;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
