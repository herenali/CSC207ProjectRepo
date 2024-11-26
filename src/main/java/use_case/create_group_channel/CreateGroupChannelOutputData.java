package use_case.create_group_channel;

/**
 * Output Data for the Choose Group Channel Use Case.
 */
public class CreateGroupChannelOutputData {
    private final String channelUrl;
    private final boolean useCaseFailed;

    public CreateGroupChannelOutputData(String channelUrl, Boolean useCaseFailed) {
        this.channelUrl = channelUrl;
        this.useCaseFailed = useCaseFailed;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
