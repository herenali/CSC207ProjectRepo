package use_case.choose_group_channel;

/**
 * The Input Data for the ChooseGroupChannel Use Case.
 */
public class ChooseGroupChannelInputData {
    private final String groupChannelUrl;

    public ChooseGroupChannelInputData(String groupChannelUrl) {
        this.groupChannelUrl = groupChannelUrl;
    }

    String getGroupChannelUrl() {
        return groupChannelUrl;
    }
}
