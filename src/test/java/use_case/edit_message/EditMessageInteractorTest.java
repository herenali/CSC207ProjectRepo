package use_case.edit_message;

import entity.SbMessageManager;
import org.junit.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EditMessageInteractorTest {
    @Test
    public void editSuccess() {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");
        SbMessageManager sbMessageManager = new SbMessageManager(defaultClient);

        EditMessageOutputBoundary editMessagePresenter = new EditMessageOutputBoundary() {
            @Override
            public void prepareSuccessView(EditMessageOutputData outputData) {
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        String testUserId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        String testChannelType = "group_channels";
        String testChannelUrl = "sendbird_group_channel_17729697_fbf1838c39e6d07e9cc4b3d68d1a5f35eae4312f";
        String newMessage = "updated message";
        String oldMessage = "original message";

        BigDecimal testMessageId = sbMessageManager.sendMessage(testChannelType, testChannelUrl, testUserId,
                oldMessage, "MESG").getMessageId();

        EditMessageInputData editMessageInputData = new EditMessageInputData(testUserId, testMessageId.intValue(), testChannelUrl, newMessage);
        EditMessageInteractor editMessageInteractor = new EditMessageInteractor(editMessagePresenter);

        assertEquals(oldMessage, sbMessageManager.getMessage(testChannelType, testChannelUrl,
                String.valueOf(testMessageId)).getMessage()); // checks if message before editing is original message


        editMessageInteractor.execute(editMessageInputData);

        assertEquals(newMessage, sbMessageManager.getMessage(testChannelType, testChannelUrl,
                String.valueOf(testMessageId)).getMessage()); // checks if message after editing is updated message
    }
}