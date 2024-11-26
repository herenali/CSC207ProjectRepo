package use_case.edit_message;

import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import entity.SbMessageManager;

/**
 * The Edit Message Interactor.
 */
public class EditMessageInteractor implements EditMessageInputBoundary {
    private final EditMessageOutputBoundary editMessagePresenter;

    public EditMessageInteractor(EditMessageOutputBoundary editMessagePresenter) {
        this.editMessagePresenter = editMessagePresenter;
    }

    @Override
    public void execute(EditMessageInputData editMessageInputData) {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");
        final SbMessageManager sbMessageManager = new SbMessageManager(defaultClient);

        final String userId = editMessageInputData.getUserId();
        final int messageId = editMessageInputData.getMessageId();
        final String groupChannelUrl = editMessageInputData.getGroupChannelUrl();
        final String newMessage = editMessageInputData.getNewMessage();
        sbMessageManager.editMessage("group_channels",
                groupChannelUrl,
                messageId,
                userId,
                newMessage,
                "MESG");
    }
}
