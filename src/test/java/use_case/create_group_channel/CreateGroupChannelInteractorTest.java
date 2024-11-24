package use_case.create_group_channel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CreateGroupChannelInteractorTest {
    @Test
    public void successTestSingleUser() {
        CreateGroupChannelInputData inputData =
                new CreateGroupChannelInputData("New Chat", "Jonathan", null, "Paul");

        CreateGroupChannelOutputBoundary successPresenter = new CreateGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                assertEquals("Chat is created.", outputData.getMessage());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        CreateGroupChannelInputBoundary interactor = new CreateGroupChannelInteractor(successPresenter);
        interactor.execute(inputData);
    }
}
