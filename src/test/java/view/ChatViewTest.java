package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.create_group_channel.CreateGroupChannelPresenter;
import interface_adapter.create_group_channel.CreateGroupChannelController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import use_case.create_group_channel.*;
import use_case.create_group_channel.CreateGroupChannelInteractor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class ChatViewTest {
    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private CreateGroupChannelController createGroupChannelController;
    private CreateGroupChannelInputData createGroupChannelInputData;
    private ChatView chatView;

    @BeforeEach
    public void init(){
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient()
                .addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        loggedInViewModel = new LoggedInViewModel();
        viewManagerModel = new ViewManagerModel();

        CreateGroupChannelPresenter presenter = new CreateGroupChannelPresenter(viewManagerModel, loggedInViewModel);
        CreateGroupChannelInteractor interactor = new CreateGroupChannelInteractor(presenter);
        createGroupChannelController = new CreateGroupChannelController(interactor);

        chatView = new ChatView(loggedInViewModel);
        chatView.setCreateGroupChannelController(createGroupChannelController);
    }

    @Test
    public void testCreateGroupChat(){
        String currentUserId = loggedInViewModel.getState().getUserId();
        JTextField chatNameField = new JTextField("Group Chat");
        JTextField userField1 = new JTextField("ABC");
        JTextField userField2 = new JTextField("DEF");
        List<String> users = new ArrayList<>();
        users.add(userField1.getText());
        users.add(userField2.getText());
        CreateGroupChannelInputData inputData = createGroupChannelInputData.forGroupChat(chatNameField.getText(), users, currentUserId);
        createGroupChannelController.createGroupChat(chatNameField.getText(), users, currentUserId);;
        assertEquals("Group Chat", inputData.getChatName());
        assertEquals(2, users.size());
        assertEquals("ABC", users.get(0));
        assertEquals("DEF", users.get(1));
        System.out.println("Group chat created with users: " + inputData.getUsers());
    }

    @Test
    public void testNewChatButtonValidInput() {
        JTextField chatNameField = (JTextField) ((JPanel) chatView.getComponent(1)).getComponent(1);
        JTextField userNameField = (JTextField) ((JPanel) chatView.getComponent(1)).getComponent(3);
        chatNameField.setText("New Chat");
        userNameField.setText("ABC");
        JButton newChatButton = (JButton) chatView.getComponent(0);
        newChatButton.doClick();
        DefaultListModel model = (DefaultListModel) ((JList) chatView.getComponent(2)).getModel();
        assertEquals("New Chat", model.getElementAt(model.getSize() - 1));
    }

    @Test
    public void testNewChatButtonInvalidInput() {
        JTextField chatNameField = (JTextField) ((JPanel) chatView.getComponent(1)).getComponent(1);
        JTextField userNameField = (JTextField) ((JPanel) chatView.getComponent(1)).getComponent(3);
        chatNameField.setText("");
        userNameField.setText("ABC");
        JButton newChatButton = (JButton) chatView.getComponent(0);
        newChatButton.doClick();
        DefaultListModel model = (DefaultListModel) ((JList) chatView.getComponent(2)).getModel();
        assertEquals(0, model.getSize());
    }
}
