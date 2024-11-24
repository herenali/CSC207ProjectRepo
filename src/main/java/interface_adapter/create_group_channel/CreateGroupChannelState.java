package interface_adapter.create_group_channel;

public class CreateGroupChannelState {
    private String channelName = "";
    private String currentUserId = "";
    private String createError;

    public String getChannelName() {
        return channelName;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getCreateError() {
        return createError;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void setCreateError(String createError) {
        this.createError = createError;
    }
}
