package use_case.send_message;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SendMessageInteractorTest {
    @Test
    public void successTest() {
        SendMessageInputData inputData = new SendMessageInputData("9fe8dffb-30a8-4125-8882-c24e0d5efc52",
                "sendbird_group_channel_17729697_fbf1838c39e6d07e9cc4b3d68d1a5f35eae4312f",
                "Send Message Interactor Test");

        SendMessageOutputBoundary successPresenter = new SendMessageOutputBoundary() {
            @Override
            public void prepareSuccessView(SendMessageOutputData outputData) {
                String message = outputData.getMessage();
                assertEquals("Send Message Interactor Test", message);
            }

            @Override
            public void prepareFailView(String error) {

            }
        };

        SendMessageInputBoundary interactor = new SendMessageInteractor(successPresenter);
        interactor.execute(inputData);
    }
}
