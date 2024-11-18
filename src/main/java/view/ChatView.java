package view;

import app.SendMessages;
import entity.SbUserManager;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.choose_group_channel.ChooseGroupChannelController;
import interface_adapter.create_group_channel.CreateGroupChannelController;
import interface_adapter.logout.LogoutController;
import interface_adapter.send_message.SendMessageController;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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

    private final JButton logOutButton;
    private final JButton newChatButton;
    private final JButton profileButton;

    private final JList<String> chatList;
    private final JTextArea chatArea;

    private final Map<String, List<String>> chatMessages = new HashMap<>();
    private JTextField messageInputField;
    private JButton sendButton;
    private SendMessages sendMessages = new SendMessages();

    public ChatView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Chats");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.setLayout(new BorderLayout());

        // top panel for buttons (New Chat, Profile, Log Out)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        newChatButton = new JButton("New Chat");
        profileButton = new JButton("Profile");
        logOutButton = new JButton("Log Out");
        topPanel.add(newChatButton);
        topPanel.add(profileButton);
        topPanel.add(logOutButton);

        // add panel for sending messages
        JPanel chatInputPanel = new JPanel();
        chatInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        messageInputField = new JTextField(15);
        sendButton = new JButton("Send");

        chatInputPanel.add(messageInputField);
        chatInputPanel.add(sendButton);
        this.add(chatInputPanel, BorderLayout.SOUTH);

        // left panel for the chats
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        String[] sampleChats = {"Chat 1", "Chat 2", "Chat 3"}; // replace with actual chats

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
            chatList = new JList<>(chats);
        } else {
            chatList = new JList<>();
        }

        // chatList = new JList<>(sampleChats);
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.setSelectedIndex(0);
        chatList.addListSelectionListener(e -> updateChatArea());

        JScrollPane chatListScrollPane = new JScrollPane(chatList);
        leftPanel.add(chatListScrollPane, BorderLayout.CENTER);

        // current chat open
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatAreaScrollPane = new JScrollPane(chatArea);

        // add to panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(chatAreaScrollPane, BorderLayout.CENTER);

        sendButton.addActionListener(evt -> {
            final String messageText = messageInputField.getText().trim();
            if (!messageText.isEmpty()) {
                final String groupChannelUrl = loggedInViewModel.getState().getGroupChannelUrl();
                if (groupChannelUrl != null && !groupChannelUrl.isEmpty()) {
                    sendMessageController.execute(currentUserId, groupChannelUrl, messageText);
                }
                else {
                    chatArea.setText("No group channel selected.");
                }
                messageInputField.setText("");
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
                    }
                }
        );

        newChatButton.addActionListener(evt -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            String[] options = {"Single Chat", "Group Chat"};
            int chatType = JOptionPane.showOptionDialog(null, "Select Chat Type:", "New Chat",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            JTextField chatNameField = new JTextField(15);
            panel.add(new JLabel("Chat Name: "));
            panel.add(chatNameField);
            if (chatType == 0) {
                JTextField userNameField = new JTextField(15);
                panel.add(new JLabel("Enter Username: "));
                panel.add(userNameField);
                int result = JOptionPane.showConfirmDialog(null, panel, "New Single Chat", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String chatName = chatNameField.getText().trim();
                    String targetUser = userNameField.getText().trim();
                    if (!chatName.isEmpty() && !targetUser.isEmpty()) {
                        createGroupChannelController.createSingleChat(chatName, targetUser);
                        updateChatArea();
                    }
                }
            }
            else if (chatType == 1) {
                JTextField usersField = new JTextField(20);
                panel.add(new JLabel("Enter Users (comma-separated): "));
                panel.add(usersField);
                int result = JOptionPane.showConfirmDialog(null, panel, "New Group Chat", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String chatName = chatNameField.getText().trim();
                    String usersInput = usersField.getText().trim();
                    if (!chatName.isEmpty() && !usersInput.isEmpty()) {
                        List<String> users = List.of(usersInput.split(","));
                        if (users.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "You must enter at least one user.");
                            return;
                        }
                        createGroupChannelController.createGroupChat(chatName, users);
                        updateChatArea();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "You must enter at least one user.");
                    }
                }
            }
        });

        // profileButton.addActionListener();
        profileButton.addActionListener(evt -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            String currentUsername = loggedInViewModel.getState().getUsername();
            String currentPassword = loggedInViewModel.getState().getPassword();

            JTextField userNameField = new JTextField(currentUsername, 15);
            JPasswordField passwordField = new JPasswordField(currentPassword, 15);
            userNameField.setEditable(false);
            passwordField.setEditable(false);

            JButton changeUsernameButton = new JButton("Change");
            JButton changePasswordButton = new JButton("Change");

            panel.add(new JLabel("Username: "));
            panel.add(userNameField);
            panel.add(changeUsernameButton);
            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Password: "));
            panel.add(passwordField);
            panel.add(changePasswordButton);
            panel.add(Box.createVerticalStrut(10));

            changeUsernameButton.addActionListener(changeEvt -> {
                String newUsername = JOptionPane.showInputDialog(null, "Enter new username: ", currentUsername);
                if (!newUsername.isEmpty()) {
                    userNameField.setText(newUsername);
                    loggedInViewModel.getState().setUsername(newUsername);
                }
            });

            changePasswordButton.addActionListener(changeEvt -> {
                String newPassword = JOptionPane.showInputDialog(null, "Enter new password: ");
                if (!newPassword.isEmpty()) {
                    passwordField.setText(newPassword);
                    loggedInViewModel.getState().setPassword(newPassword);
                }
            });

            int result = JOptionPane.showConfirmDialog(null, panel, "Profile", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                loggedInViewModel.getState().setUsername(userNameField.getText());
                loggedInViewModel.getState().setPassword(new String(passwordField.getPassword()));
            }
        });
    }

    private void updateChatArea() {
        final String selectedChat = chatList.getSelectedValue();
        chatArea.setText("Display messages for: " + selectedChat);

        if (selectedChat != null) {
            final String groupChannelUrl = selectedChat.substring(selectedChat.lastIndexOf(": ") + 1);
            loggedInViewModel.getState().setGroupChannelUrl(groupChannelUrl);

            chooseGroupChannelController.execute(groupChannelUrl);
            final List<List<String>> usersAndMessages = loggedInViewModel.getState().getUsersAndMessages();
            final StringBuilder chatAreaBuilder = new StringBuilder();
            for (List<String> userAndMessage : usersAndMessages) {
                chatAreaBuilder.append(userAndMessage.get(0));
                chatAreaBuilder.append(": ");
                chatAreaBuilder.append(userAndMessage.get(1));
                chatAreaBuilder.append("\n");
            }
            chatArea.setText(chatAreaBuilder.toString());
        }
        else {
            chatArea.setText("No chat selected.");
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        if (evt.getPropertyName().equals("state")) {
//            final LoggedInState state = (LoggedInState) evt.getNewValue();
//            username.setText(state.getUsername());
//        }
//        else if (evt.getPropertyName().equals("password")) {
//            final LoggedInState state = (LoggedInState) evt.getNewValue();
//            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
//        }
    }
}