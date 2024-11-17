package use_case.choose_group_channel;

import entity.SbMessageManager;
import org.openapitools.client.model.ListMessagesResponse;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;


/**
 * The Choose Group Channel Interactor.
 */
public class ChooseGroupChannelInteractor implements ChooseGroupChannelInputBoundary {
    private final ChooseGroupChannelOutputBoundary chooseGroupChannelPresenter;

    public ChooseGroupChannelInteractor(ChooseGroupChannelOutputBoundary chooseGroupChannelPresenter) {
        this.chooseGroupChannelPresenter = chooseGroupChannelPresenter;
    }

    @Override
    public void execute(ChooseGroupChannelInputData chooseGroupChannelInputData) {
        final String groupChannelUrl = chooseGroupChannelInputData.getGroupChannelUrl();

        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        final SbMessageManager sbMessageManager = new SbMessageManager(defaultClient);
        final ListMessagesResponse messagesResponse = sbMessageManager.listMessages(groupChannelUrl);
        final ChooseGroupChannelOutputData outputData = new ChooseGroupChannelOutputData(messagesResponse);
        chooseGroupChannelPresenter.prepareSuccessView(outputData);
    }
}
