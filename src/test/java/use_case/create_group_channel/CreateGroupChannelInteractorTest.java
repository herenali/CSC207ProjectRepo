package use_case.create_group_channel;

import data_access.InMemoryUserDataAccessObject;
import entity.SbUserFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.create_group_channel.CreateGroupChannelPresenter;
import org.junit.Before;
import org.junit.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.util.List;

import static org.junit.Assert.*;


public class CreateGroupChannelInteractorTest {
    private InMemoryUserDataAccessObject userRepository;

    @Test
    public void testcreateChannelSuccess() {
        CreateGroupChannelDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        UserFactory factory = new SbUserFactory(defaultClient);
        User Paul = factory.create("Paul", "123");
        User Jonathan = factory.create("Jonathan", "123");
        // String userPaulId = "9fe8dffb-30a8-4125-8882-c24e0d5efc52";
        // String userJonathanId = "11415872-17cb-47ff-a986-ed7c1b63760c";
        userRepository.save(Paul);
        userRepository.save(Jonathan);
        System.out.println("Creating user: " + Paul.getUserId());
        System.out.println("Creating user: " + Jonathan.getUserId());


        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", List.of("Jonathan"), "9fe8dffb-30a8-4125-8882-c24e0d5efc52");

        CreateGroupChannelOutputBoundary successPresenter = new CreateGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                assertEquals(false, outputData.isUseCaseFailed());

                String createdChannelUrl = outputData.getChannelUrl();
                assertNotNull(createdChannelUrl);
            }
            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        CreateGroupChannelInputBoundary interactor = new CreateGroupChannelInteractor(successPresenter, userRepository);
        interactor.execute(inputData);
    }

    @Test
    public void testcreateChannelFailure() {
        CreateGroupChannelDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", List.of("NonExistentUser"), "9fe8dffb-30a8-4125-8882-c24e0d5efc52");
        CreateGroupChannelOutputBoundary failPresenter = new CreateGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                fail("Unexpected success when the user does not exist.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Username \"NonExistentUser\" does not exist.", errorMessage);
            }
        };

        CreateGroupChannelInputBoundary interactor = new CreateGroupChannelInteractor(failPresenter, userRepository);
        interactor.execute(inputData);
    }

    @Test
    public void testCreateGroupChannel_NoUsers() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", List.of(), "9fe8dffb-30a8-4125-8882-c24e0d5efc52");

        CreateGroupChannelOutputBoundary failPresenter = new CreateGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                fail("Unexpected success when there are no users.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("No valid user(s).", errorMessage);
            }
        };

        CreateGroupChannelInputBoundary interactor = new CreateGroupChannelInteractor(failPresenter, userRepository);
 
        interactor.execute(inputData);
    }
}

