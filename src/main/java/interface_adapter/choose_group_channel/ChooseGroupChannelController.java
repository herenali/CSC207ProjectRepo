package interface_adapter.choose_group_channel;

import use_case.choose_group_channel.ChooseGroupChannelInputBoundary;
import use_case.choose_group_channel.ChooseGroupChannelInputData;

/**
 * The controller for the Choose Group Channel Use Case.
 */
public class ChooseGroupChannelController {
    private final ChooseGroupChannelInputBoundary chooseGroupChannelUseCaseInteractor;


    public ChooseGroupChannelController(ChooseGroupChannelInputBoundary chooseGroupChannelUseCaseInteractor) {
        this.chooseGroupChannelUseCaseInteractor = chooseGroupChannelUseCaseInteractor;
    }

    /**
     * Executes the Choose Group Channel Use Case.
     * @param groupChannelId the URL of the current group channel
     */
    public void execute(String groupChannelId) {
        final ChooseGroupChannelInputData chooseGroupChannelInputData = new ChooseGroupChannelInputData(
                groupChannelId);

        chooseGroupChannelUseCaseInteractor.execute(chooseGroupChannelInputData);
    }
}

