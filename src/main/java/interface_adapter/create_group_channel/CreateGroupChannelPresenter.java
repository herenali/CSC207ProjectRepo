package interface_adapter.create_group_channel;


import interface_adapter.ViewManagerModel;
import use_case.create_group_channel.CreateGroupChannelOutputBoundary;
import use_case.create_group_channel.CreateGroupChannelOutputData;


/**
 * The Presenter for the Create Group Chat Use Case.
 */
public class CreateGroupChannelPresenter implements CreateGroupChannelOutputBoundary {


    private final CreateGroupChannelViewModel createGroupChannelViewModel;
    private final ViewManagerModel viewManagerModel;
    private CreateGroupChannelOutputData outputData;


    public CreateGroupChannelPresenter(ViewManagerModel viewManagerModel, CreateGroupChannelViewModel createGroupChannelViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.createGroupChannelViewModel = createGroupChannelViewModel;
    }

    /**
     * Executes the Create Group Channel Use Case.
     * @param outputData for data to be outputted
     */
    public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
        // On success, update the contents of the chat area
    }

    /**
     * Executes the Create Group Channel Use Case.
     * @param errorMessage to be displayed when program runs into an error
     */
    public void prepareFailView(String errorMessage) {
        // Display an error message in the chat area
    }

    public CreateGroupChannelOutputData getOutputData() {
        return outputData;
    }
}
