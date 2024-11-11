package use_case.send_message;

import entity.SbMessageManager;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

public class SendMessageInteractor implements SendMessageInputBoundary {
    private final SendMessageDataAccessInterface userDataAccessObject;
    private final SendMessageOutputBoundary sendMessagePresenter;

    public SendMessageInteractor(SendMessageDataAccessInterface userDataAccessObject,
                                 SendMessageOutputBoundary sendMessagePresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.sendMessagePresenter = sendMessagePresenter;
    }

    @Override
    public void execute(SendMessageInputData sendMessageInputData) {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");
        final SbMessageManager sbMessageManager = new SbMessageManager(defaultClient);

        final String userId = userDataAccessObject.getCurrentUserId();
        final String groupChannelUrl = userDataAccessObject.getCurrentGroupChannelUrl();
        final String message = sendMessageInputData.getMessage();
        sbMessageManager.sendMessage("group_channels",
                groupChannelUrl,
                userId,
                message,
                "MESG");

        // sendMessagePresenter.prepareSuccessView(sendMessageOutputData);
    }
}
