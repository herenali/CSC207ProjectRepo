package view;

import entity.SbUserManager;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.choose_group_channel.ChooseGroupChannelController;
import interface_adapter.logout.LogoutController;
import interface_adapter.send_message.SendMessageController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import org.openapitools.client.model.*;

import javax.swing.*;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ChatViewTest {
    private LoggedInViewModel loggedInViewModel;
    private SbUserManager sbUserManager;
    private SendMessageController sendMessageController;
    private ChooseGroupChannelController chooseGroupChannelController;
    private LogoutController logoutController;
    private ChatView chatView;

    @BeforeEach
    public void init(){
        chatView = new ChatView(loggedInViewModel);
        chatView.setSendMessageController(sendMessageController);
        chatView.setChooseGroupChannelController(chooseGroupChannelController);
        chatView.setLogoutController(logoutController);
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
