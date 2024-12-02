package use_case.choose_group_channel;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChooseGroupChannelInteractorTest {
    @Test
    public void successTest() {
        ChooseGroupChannelInputData inputData =
                new ChooseGroupChannelInputData("sendbird_group_channel_19639730_1cd19e30afe26f9fbdd933b26ee4752a8fd5e107");

        ChooseGroupChannelOutputBoundary successPresenter = new ChooseGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(ChooseGroupChannelOutputData outputData) {
                List<List<String>> usersAndMessages = outputData.getUsersAndMessages();
                List<String> firstUserAndMessage = usersAndMessages.get(0);
                assertEquals("abc", firstUserAndMessage.get(0));
                assertEquals("Send Message Interactor Test", firstUserAndMessage.get(1));

                List<List<String>> userAndMessageIds = outputData.getUserAndMessageIds();
                List<String> firstUserAndMessageId = userAndMessageIds.get(0);
                assertEquals("39bd6921-ef43-44bf-b8eb-f5394ca5272f", firstUserAndMessageId.get(0));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        ChooseGroupChannelInputBoundary interactor = new ChooseGroupChannelInteractor(successPresenter);
        interactor.execute(inputData);
    }
}
