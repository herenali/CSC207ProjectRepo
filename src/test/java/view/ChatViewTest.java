package view;


import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.create_group_channel.CreateGroupChannelPresenter;
import interface_adapter.create_group_channel.CreateGroupChannelController;
import data_access.InMemoryUserDataAccessObject;
import org.junit.jupiter.api.BeforeEach;


import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import use_case.create_group_channel.*;
import use_case.create_group_channel.CreateGroupChannelInteractor;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ChatViewTest {
    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private CreateGroupChannelController createGroupChannelController;
    private ChatView chatView;
    private InMemoryUserDataAccessObject userRepository;


    @BeforeEach
    public void init(){
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient()
                .addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");


        userRepository = new InMemoryUserDataAccessObject();
        loggedInViewModel = new LoggedInViewModel();
        viewManagerModel = new ViewManagerModel();


        CreateGroupChannelPresenter presenter = new CreateGroupChannelPresenter(viewManagerModel, loggedInViewModel);
        CreateGroupChannelInteractor interactor = new CreateGroupChannelInteractor(presenter, userRepository);
        createGroupChannelController = new CreateGroupChannelController(interactor);


        chatView = new ChatView(loggedInViewModel);
        chatView.setCreateGroupChannelController(createGroupChannelController);
    }
}
