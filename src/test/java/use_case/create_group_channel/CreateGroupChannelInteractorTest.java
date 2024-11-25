package use_case.create_group_channel;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_group_channel.CreateGroupChannelController;
import interface_adapter.create_group_channel.CreateGroupChannelPresenter;
import interface_adapter.create_group_channel.CreateGroupChannelViewModel;
import interface_adapter.create_group_channel.CreateGroupChannelState;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openapitools.client.model.ListMyGroupChannelsResponse;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class CreateGroupChannelInteractorTest {
    private CreateGroupChannelInteractor createGroupChannelInteractor;
    private CreateGroupChannelPresenter createGroupChannelPresenter;
    private InMemoryUserDataAccessObject userRepository;

    @Before
    public void setUp() {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");


        userRepository = new InMemoryUserDataAccessObject();


        createGroupChannelPresenter = new CreateGroupChannelPresenter(new ViewManagerModel(), new CreateGroupChannelViewModel());
        createGroupChannelInteractor = new CreateGroupChannelInteractor(createGroupChannelPresenter, userRepository);
    }

    @Test
    public void testcreateChannel() {
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

        CreateGroupChannelInputData inputData = new CreateGroupChannelInputData("Testing Channel", "Jonathan", null, Paul.getUserId());

        CreateGroupChannelOutputBoundary successPresenter = new CreateGroupChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupChannelOutputData outputData) {
                assertEquals("Chat created successfully.", outputData.getMessage());
                assertEquals(false, outputData.isUseCaseFailed());

                String createdChannelUrl = outputData.getChannelUrl();
                assertNotNull(createdChannelUrl);


                SbUserManager userManager = new SbUserManager(defaultClient);

                ListMyGroupChannelsResponse paulChannels = userManager.listGroupChannelsByUserId(Paul.getUserId());
                assertNotNull(paulChannels);
                System.out.println(paulChannels);

//                ListMyGroupChannelsResponse jonathanChannels = userManager.listGroupChannelsByUserId(userJonathanId);
//                assertTrue(jonathanChannels.getChannels().stream()
//                        .anyMatch(channel -> channel.getChannelUrl().equals(createdChannelUrl)));
            }
            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        CreateGroupChannelInputBoundary interactor = new CreateGroupChannelInteractor(successPresenter, userRepository);
        interactor.execute(inputData);
    }
}
