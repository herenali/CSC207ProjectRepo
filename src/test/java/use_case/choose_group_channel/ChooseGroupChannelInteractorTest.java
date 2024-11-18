package use_case.choose_group_channel;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChooseGroupChannelInteractorTest {
    @Test
    public void successTest() {
        ChooseGroupChannelInputData inputData =
                new ChooseGroupChannelInputData("sendbird_group_channel_17729697_fbf1838c39e6d07e9cc4b3d68d1a5f35eae4312f");

        ChooseGroupChannelOutputBoundary successPresenter = new ChooseGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(ChooseGroupChannelOutputData outputData) {
                List<List<String>> usersAndMessages = outputData.getUsersAndMessages();
                List<String> lastUserAndMessage = usersAndMessages.get(usersAndMessages.size() - 1);
                assertEquals(lastUserAndMessage.get(0), "Paul");
                assertEquals(lastUserAndMessage.get(1), "Hello! This is the first message.");
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
