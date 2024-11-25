package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import entity.SbUser;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_group_channel.CreateGroupChannelPresenter;
import interface_adapter.edit_message.EditMessageController;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import entity.SbUserManager;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.choose_group_channel.ChooseGroupChannelController;
import interface_adapter.create_group_channel.CreateGroupChannelController;
import interface_adapter.logout.LogoutController;
import interface_adapter.send_message.SendMessageController;
import use_case.create_group_channel.CreateGroupChannelInputBoundary;
import use_case.create_group_channel.CreateGroupChannelInputData;
import use_case.create_group_channel.CreateGroupChannelInteractor;
import use_case.create_group_channel.CreateGroupChannelOutputBoundary;

/**
 * The View for when the user is logged into the program.
 */
public class ChatView extends JPanel implements PropertyChangeListener {
    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private LogoutController logoutController;
    private ChooseGroupChannelController chooseGroupChannelController;
    private CreateGroupChannelController createGroupChannelController;
    private SendMessageController sendMessageController;
    private EditMessageController editMessageController;

    private final JButton logOutButton;
    private final JButton newChatButton;
    private final JButton profileButton;

    private JList<String> chatList;
    // private final JTextArea chatArea;
    private final JPanel chatArea;

    private final Map<String, List<String>> chatMessages = new HashMap<>();
    private JTextField messageInputField;
    private JButton sendButton;

    public ChatView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Chats");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.setLayout(new BorderLayout());

        // top panel for buttons (New Chat, Profile, Log Out)
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        newChatButton = new JButton("New Chat");
        profileButton = new JButton("Profile");
        logOutButton = new JButton("Log Out");
        topPanel.add(newChatButton);
        topPanel.add(profileButton);
        topPanel.add(logOutButton);

        // add panel for sending messages
        final JPanel chatInputPanel = new JPanel();
        chatInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        messageInputField = new JTextField(15);
        sendButton = new JButton("Send");

        chatInputPanel.add(messageInputField);
        chatInputPanel.add(sendButton);
        this.add(chatInputPanel, BorderLayout.SOUTH);

        // left panel for the chats
        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // final String[] sampleChats = {"Chat 1", "Chat 2", "Chat 3"}; // replace with actual chats

        // fetch chats from sendbird
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");
        final SbUserManager sbUserManager = new SbUserManager(defaultClient);
        final String currentUserId = loggedInViewModel.getState().getUserId();
        final DefaultListModel chats = new DefaultListModel();

        if (currentUserId.length() > 0) {
            final List<SendBirdGroupChannel> groupChannels = sbUserManager
                    .listGroupChannelsByUserId(loggedInViewModel.getState().getUserId()).getChannels();
            for (int i = 0; i < groupChannels.size(); i++) {
                final SendBirdGroupChannel groupChannel = groupChannels.get(i);
                final StringBuilder chatName = new StringBuilder();
                chatName.append(groupChannel.getName());
                chatName.append(": ");
                chatName.append(groupChannel.getChannelUrl());
                chats.add(i, chatName.toString());
            }
            chatList = new JList<>(chats);
        }
        else {
            chatList = new JList<>();
        }

        // chatList = new JList<>(sampleChats);
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.setSelectedIndex(0);
        chatList.addListSelectionListener(e -> updateChatArea());

        final JScrollPane chatListScrollPane = new JScrollPane(chatList);
        leftPanel.add(chatListScrollPane, BorderLayout.CENTER);

        // current chat open
        chatArea = new JPanel();

        final JScrollPane chatAreaScrollPane = new JScrollPane(chatArea);
        // chatArea.setText("No chat selected.");
        chatArea.removeAll();
        chatArea.add(new JLabel("No chat selected."));
        chatArea.revalidate();
        chatArea.repaint();

        // add to panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(chatAreaScrollPane, BorderLayout.CENTER);

        sendButton.addActionListener(evt -> {
            final String messageText = messageInputField.getText().trim();
            if (!messageText.isEmpty()) {
//                sendMessages.sendMessage();
                final String updatedCurrentUserId = loggedInViewModel.getState().getUserId();
                final String groupChannelUrl = loggedInViewModel.getState().getGroupChannelUrl();
                if (groupChannelUrl != null && !groupChannelUrl.isEmpty()) {
                    sendMessageController.execute(updatedCurrentUserId, groupChannelUrl, messageText);
                    messageInputField.setText("");
                    updateChatArea();
                }
                else {
                    chatArea.add(new JLabel("No group channel selected."));
                }
                updateChatArea();
            }
        });

        logOutButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(logOutButton)) {
                        // 1. get the state out of the loggedInViewModel. It contains the username.
                        // 2. Execute the logout Controller.
                        final LoggedInState currentState = loggedInViewModel.getState();
                        logoutController.execute(
                                currentState.getUsername()
                        );

                        // Clear chat area
                        chatArea.add(new JLabel("No chat selected."));
                    }
                }
        );

        newChatButton.addActionListener(evt -> {
            final JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            final String[] options = {"Single Chat", "Group Chat"};
            final int chatType = JOptionPane.showOptionDialog(null, "Select Chat Type:", "New Chat",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            final JTextField chatNameField = new JTextField(15);
            panel.add(new JLabel("Chat Name: "));
            panel.add(chatNameField);

            if (chatType == 0) {
                final JTextField userNameField = new JTextField(15);
                panel.add(new JLabel("Enter Username: "));
                panel.add(userNameField);
                final int result = JOptionPane.showConfirmDialog(null, panel, "New Single Chat", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    final String chatName = chatNameField.getText().trim();
                    final String user = userNameField.getText().trim();
                    if (!chatName.isEmpty() && !user.isEmpty()) {
                        createGroupChannelController.createSingleChat(chatName, user, loggedInViewModel.getState().getUserId());
                        chats.addElement(chatName);
                        chatList.setModel(chats);
                        // chatList.revalidate();
                        // chatList.repaint();
                        // updateChatArea();
                    }
                }
            }
            else if (chatType == 1) {
                final JTextField usersField = new JTextField(20);
                panel.add(new JLabel("Enter Users (comma-separated): "));
                panel.add(usersField);
                final int result = JOptionPane.showConfirmDialog(null, panel, "New Group Chat", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    final String chatName = chatNameField.getText().trim();
                    final String usersInput = usersField.getText().trim();

                    if (!chatName.isEmpty() && !usersInput.isEmpty()) {
                        final List<String> users = List.of(usersInput.split(","));
                        if (users.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "You must enter at least one user.");
                            return;
                        }
                        createGroupChannelController.createGroupChat(chatName, users, loggedInViewModel.getState().getUserId());
                        chats.addElement(chatName);
                        chatList.setModel(chats);
                        // chatList.revalidate();
                        // chatList.repaint();
                        // updateChatArea();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "You must enter at least one user.");
                    }
                }
            }
        });

        profileButton.addActionListener(evt -> {
            final JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            final String currentUsername = loggedInViewModel.getState().getUsername();
            final String currentPassword = loggedInViewModel.getState().getPassword();

            final JTextField userNameField = new JTextField(currentUsername, 15);
            final JPasswordField passwordField = new JPasswordField(currentPassword, 15);
            userNameField.setEditable(false);
            passwordField.setEditable(false);

            final JButton changeUsernameButton = new JButton("Change");
            final JButton changePasswordButton = new JButton("Change");

            panel.add(new JLabel("Username: "));
            panel.add(userNameField);
            panel.add(changeUsernameButton);
            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Password: "));
            panel.add(passwordField);
            panel.add(changePasswordButton);
            panel.add(Box.createVerticalStrut(10));

            changeUsernameButton.addActionListener(changeEvt -> {
                final String newUsername = JOptionPane.showInputDialog(null, "Enter new username: ", currentUsername);
                if (!newUsername.isEmpty()) {
                    userNameField.setText(newUsername);
                    loggedInViewModel.getState().setUsername(newUsername);
                }
            });

            changePasswordButton.addActionListener(changeEvt -> {
                final String newPassword = JOptionPane.showInputDialog(null, "Enter new password: ");
                if (!newPassword.isEmpty()) {
                    passwordField.setText(newPassword);
                    loggedInViewModel.getState().setPassword(newPassword);
                }
            });

            final int result = JOptionPane.showConfirmDialog(null, panel, "Profile", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                loggedInViewModel.getState().setUsername(userNameField.getText());
                loggedInViewModel.getState().setPassword(new String(passwordField.getPassword()));
            }
        });
    }

    private void updateChatArea() {
        final String selectedChat = chatList.getSelectedValue();
        // chatArea.setText("Display messages for: " + selectedChat);
        chatArea.add(new JLabel("Display messages for: " + selectedChat));

        if (selectedChat != null) {
            final String groupChannelUrl = selectedChat.substring(selectedChat.lastIndexOf(": ") + 2);
            // System.out.println(groupChannelUrl);
            loggedInViewModel.getState().setGroupChannelUrl(groupChannelUrl);
            chooseGroupChannelController.execute(groupChannelUrl);
            final List<List<String>> usersAndMessages = loggedInViewModel.getState().getUsersAndMessages();
            final List<List<String>> userAndMessageIds = loggedInViewModel.getState().getUserAndMessageIds();

            chatArea.removeAll();
            chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));

            for (int i = 0; i < usersAndMessages.size(); i++) {
                final List<String> userAndMessage = usersAndMessages.get(i);
                final List<String> userAndMessageId = userAndMessageIds.get(i);
                final String user = userAndMessage.get(0);
                final String message = userAndMessage.get(1);
                final String userId = userAndMessageId.get(0);
                final String messageId = userAndMessageId.get(1);

                final JPanel messagePanel = new JPanel();
                messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                messagePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
                messagePanel.setSize(new Dimension());
                messagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                final JLabel messageLabel = new JLabel(user + ": " + message);
                messageLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
                messagePanel.add(messageLabel);

                final String currentUserId = loggedInViewModel.getState().getUserId();
                if (userId.equals(currentUserId)) {
                    final JButton editButton = new JButton("edit");
                    editButton.setPreferredSize(new Dimension(40, 20));

                    editButton.addActionListener(evt -> {
                        final String newMessage = JOptionPane.showInputDialog("Edit Message:", message);

                        if (newMessage != null && !newMessage.equals(message)) {
                            editMessageController.execute(currentUserId, Integer.valueOf(messageId), groupChannelUrl, newMessage);
                            updateChatArea();
                        }
                    });
                    messagePanel.add(editButton);
                }
                chatArea.add(messagePanel);
            }
            chatArea.revalidate();
            chatArea.repaint();
        } else {
            chatArea.add(new JLabel("No chat selected."));
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setChooseGroupChannelController(ChooseGroupChannelController chooseGroupChannelController) {
        this.chooseGroupChannelController = chooseGroupChannelController;
    }

    public void setCreateGroupChannelController(CreateGroupChannelController createGroupChannelController) {
        this.createGroupChannelController = createGroupChannelController;
    }

    public void setSendMessageController(SendMessageController sendMessageController) {
        this.sendMessageController = sendMessageController;
    }

    public void setEditMessageController(EditMessageController editMessageController) {
        this.editMessageController = editMessageController;
    }

    public JButton getNewChatButton(){
        return newChatButton;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("login")) {
            // fetch chats from sendbird
            final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
            final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
            final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
            defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");
            final SbUserManager sbUserManager = new SbUserManager(defaultClient);
            final String currentUserId = loggedInViewModel.getState().getUserId();
            DefaultListModel chats = new DefaultListModel();

            if (currentUserId.length() > 0) {
                final List<SendBirdGroupChannel> groupChannels = sbUserManager
                        .listGroupChannelsByUserId(loggedInViewModel.getState().getUserId()).getChannels();
                for (int i = 0; i < groupChannels.size(); i++) {
                    SendBirdGroupChannel groupChannel = groupChannels.get(i);
                    StringBuilder chatName = new StringBuilder();
                    chatName.append(groupChannel.getName());
                    chatName.append(": ");
                    chatName.append(groupChannel.getChannelUrl());
                    chats.add(i, chatName.toString());
                }
                chatList.setModel(chats);
            }
            else {
                chatList = new JList<>();
            }

        }
    }
}
