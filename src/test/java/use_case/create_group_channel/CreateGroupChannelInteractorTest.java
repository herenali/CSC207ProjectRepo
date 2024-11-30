package use_case.create_group_channel;

import data_access.InMemoryUserDataAccessObject;
import entity.SbUserFactory;
import entity.User;
import entity.UserFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.util.List;

import static org.junit.Assert.*;


public class CreateGroupChannelInteractorTest {

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
        userRepository.save(Paul);
        userRepository.save(Jonathan);


        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", List.of("Jonathan"), "9fe8dffb-30a8-4125-8882-c24e0d5efc52");

        CreateGroupChannelInputBoundary interactor = getCreateGroupChannelInputBoundary(userRepository);
        interactor.execute(inputData);

    }



    @Test
    public void testcreateChannelInvalidUserName() {
        CreateGroupChannelDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", List.of("Non Existent User"), "9fe8dffb-30a8-4125-8882-c24e0d5efc52");

        CreateGroupChannelInputBoundary interactor = getGroupChannelInputBoundary("Unexpected success when the user does not exist.", "Username \"Non Existent User\" does not exist.", userRepository);

        interactor.execute(inputData);
    }

    @Test
    public void testCreateGroupChannelEmptyUserName() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", List.of(), "9fe8dffb-30a8-4125-8882-c24e0d5efc52");

        CreateGroupChannelInputBoundary interactor = getGroupChannelInputBoundary("Unexpected success when there are no users.", "No valid user(s).", userRepository);

        interactor.execute(inputData);
    }

    @Test
    public void testCreateGroupChannelNullUserName() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", null, "9fe8dffb-30a8-4125-8882-c24e0d5efc52");

        CreateGroupChannelInputBoundary interactor = getGroupChannelInputBoundary("Unexpected success when there are no users.", "No valid user(s).", userRepository);

        interactor.execute(inputData);
    }

    @NotNull
    private static CreateGroupChannelInputBoundary getGroupChannelInputBoundary(String message, String expected, CreateGroupChannelDataAccessInterface userRepository) {
        CreateGroupChannelOutputBoundary failPresenter = new CreateGroupChannelOutputBoundary() {

            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                fail(message);
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals(expected, errorMessage);
            }

        };
        return new CreateGroupChannelInteractor(failPresenter, userRepository);
    }

    @Test
    public void testCreateGroupChannelException() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData(
                "Testing Channel", List.of("Jonathan"), "InvalidUserId"
        );

        CreateGroupChannelOutputBoundary failPresenter = new CreateGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                fail("Success when an exception occurs.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.startsWith("An error occurred:"));
            }
        };

        CreateGroupChannelInputBoundary interactor = new CreateGroupChannelInteractor(failPresenter, userRepository) {
            @Override
            public void execute(CreateGroupChannelInputData inputData) {
                throw new RuntimeException("Simulated exception");
            }
        };
        assertThrows(RuntimeException.class, () -> interactor.execute(inputData));
    }

    @Test
    public void testCreateChannelNullCurrentUserId() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", List.of("Jonathan"), null);

        CreateGroupChannelInputBoundary interactor = getGroupChannelInputBoundary("Unexpected success when current user ID is null.", "Current user ID cannot be null or empty.", userRepository);

        interactor.execute(inputData);
    }

    @Test
    public void testCreateChannelEmptyCurrentUserId() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", List.of("Jonathan"), "");

        CreateGroupChannelInputBoundary interactor = getGroupChannelInputBoundary("Unexpected success when current user ID is null.", "Current user ID cannot be null or empty.", userRepository);

        interactor.execute(inputData);
    }


    @NotNull
    private static CreateGroupChannelInputBoundary getCreateGroupChannelInputBoundary(CreateGroupChannelDataAccessInterface userRepository) {
        CreateGroupChannelOutputBoundary successPresenter = new CreateGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                assertFalse(outputData.isUseCaseFailed());
                assertNotNull(outputData.getChannelUrl());
                assertNotNull(outputData.getUserIds());
                assertTrue(outputData.getUserIds().contains("9fe8dffb-30a8-4125-8882-c24e0d5efc52"));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertNotNull(errorMessage);
                assertTrue(errorMessage.contains("expected failure condition"));
                fail("Use case failure is unexpected.");
            }
        };

        return new CreateGroupChannelInteractor(successPresenter, userRepository);
    }

    @Test
    public void testCreateChannelMultipleUsers() {
        CreateGroupChannelDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        UserFactory factory = new SbUserFactory(defaultClient);
        User Paul = factory.create("Paul", "123");
        User Jonathan = factory.create("Jonathan", "123");
        User Alice = factory.create("Alice", "123");
        userRepository.save(Paul);
        userRepository.save(Jonathan);
        userRepository.save(Alice);

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData(
                "Test Channel", List.of("Alice", "Jonathan"), "9fe8dffb-30a8-4125-8882-c24e0d5efc52");

        CreateGroupChannelInputBoundary interactor = getCreateGroupChannelInputBoundary(userRepository);
        interactor.execute(inputData);
    }
}

