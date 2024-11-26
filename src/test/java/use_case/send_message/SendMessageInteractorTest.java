package use_case.send_message;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SendMessageInteractorTest {
    @Test
    public void successTest() {
        SendMessageInputData inputData = new SendMessageInputData("39bd6921-ef43-44bf-b8eb-f5394ca5272f",
                "sendbird_group_channel_19639730_1cd19e30afe26f9fbdd933b26ee4752a8fd5e107",
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
