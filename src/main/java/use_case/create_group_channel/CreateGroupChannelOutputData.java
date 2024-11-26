package use_case.create_group_channel;

import java.util.List;

/**
 * Output Data for the Choose Group Channel Use Case.
 */
public class CreateGroupChannelOutputData {
    private final String channelUrl;
    private final List<String> userIds;
    private final boolean useCaseFailed;

    public CreateGroupChannelOutputData(String channelUrl, List<String> userIds, Boolean useCaseFailed) {
        this.channelUrl = channelUrl;
        this.userIds = userIds;
        this.useCaseFailed = useCaseFailed;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
