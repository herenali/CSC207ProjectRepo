package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import entity.SbGroupChannelManager;
import entity.SbMessageManager;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.choose_group_channel.ChooseGroupChannelController;
import interface_adapter.create_group_channel.CreateGroupChannelController;
import interface_adapter.edit_message.EditMessageController;
import interface_adapter.logout.LogoutController;
import interface_adapter.send_message.SendMessageController;

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
    private SbMessageManager sbMessageManager;

    private final JButton logOutButton;
    private final JButton newChatButton;
    private final JButton profileButton;

    private JList<String> chatList;
    private final JPanel chatArea;

    private JTextField messageInputField;
    private JButton sendButton;
    private JButton deleteChannelButton;

    public ChatView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Chats");

        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.setLayout(new BorderLayout());

        // customize popup message appearance
        UIManager.put("OptionPane.messageFont", UIFactory.getTextFont());
        UIManager.put("OptionPane.background", UIFactory.backgroundColour);
        UIManager.put("Panel.background", UIFactory.backgroundColour);
        UIManager.put("OptionPane.okButtonText", "Save");
        UIManager.put("Button.border", null);

        // top panel for buttons (New Chat, Profile, Log Out, Delete Channel)
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(97, 130, 184));
        topPanel.setForeground(new Color(50, 50, 50));

        final Color defaultColour = new Color(245, 247, 250);
        final Color pressedColour = new Color(191, 201, 219);
        final Color hoverColour = new Color(218, 224, 235);
        final Color textColour = new Color(17, 37, 69);

        newChatButton = UIFactory.createButton("New Chat", defaultColour, pressedColour, hoverColour, textColour);
        profileButton = UIFactory.createButton("Profile", defaultColour, pressedColour, hoverColour, textColour);
        logOutButton = UIFactory.createButton("Log Out", defaultColour, pressedColour, hoverColour, textColour);
        deleteChannelButton = UIFactory.createButton("Delete", defaultColour, pressedColour, hoverColour, textColour);

        topPanel.add(newChatButton);
        topPanel.add(profileButton);
        topPanel.add(logOutButton);
        topPanel.add(deleteChannelButton);

        // left panel for the chats
        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        leftPanel.setPreferredSize(new Dimension(200, this.getHeight()));

        // fetch chats from sendbird
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");
        this.sbMessageManager = new SbMessageManager(defaultClient);
        final SbGroupChannelManager sbGroupChannelManager = new SbGroupChannelManager(defaultClient);
        final String currentUserId = loggedInViewModel.getState().getUserId();
        final DefaultListModel chats = new DefaultListModel();

        if (currentUserId.length() > 0) {
            final List<SendBirdGroupChannel> groupChannels = sbGroupChannelManager
                    .listChannels(loggedInViewModel.getState().getUserId()).getChannels();
            for (int i = 0; i < groupChannels.size(); i++) {
                final SendBirdGroupChannel groupChannel = groupChannels.get(i);
                final StringBuilder chatName = new StringBuilder();
                chatName.append(groupChannel.getName());
                chatName.append("                                                     : ");
                chatName.append(groupChannel.getChannelUrl());
                chats.add(i, chatName.toString());
            }
            chatList = new JList<>(chats);
        }
        else {
            chatList = new JList<>();
        }

        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.setSelectedIndex(0);
        chatList.addListSelectionListener(e -> updateChatArea());

        chatList.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));

        final JScrollPane chatListScrollPane = new JScrollPane(chatList);
        chatListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatListScrollPane.getViewport().setBackground(new java.awt.Color(240, 240, 240));
        chatListScrollPane.setBorder(null);
        leftPanel.add(chatListScrollPane, BorderLayout.CENTER);

        // add panel for sending messages
        final JPanel chatInputPanel = new JPanel();
        chatInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        chatInputPanel.setBackground(Color.WHITE);

        messageInputField = new JTextField(50);
        messageInputField.setPreferredSize(new Dimension(800, 30));
        messageInputField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        sendButton = UIFactory.createButton("Send");

        chatInputPanel.add(messageInputField);
        chatInputPanel.add(sendButton);

        // current chat open
        chatArea = new JPanel();
        chatArea.setBorder(null);
        chatArea.setPreferredSize(new Dimension(800, 470));
        chatArea.setBackground(UIFactory.backgroundColour);

        final JScrollPane chatAreaScrollPane = new JScrollPane(chatArea);
        chatAreaScrollPane.setBorder(null);
        chatArea.removeAll();
        chatArea.add(new JLabel("No chat selected."));
        chatArea.revalidate();
        chatArea.repaint();

        final JPanel rightPanel = new JPanel();
        rightPanel.setBorder(null);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(chatAreaScrollPane);
        rightPanel.add(chatInputPanel);

        // add to panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);

        sendButton.addActionListener(evt -> {
            final String messageText = messageInputField.getText().trim();
            if (!messageText.isEmpty()) {
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

        profileButton.setToolTipText("Press to view or change you username and password. ");
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

        newChatButton.setToolTipText("Press to create either a new single or group chat. ");
        newChatButton.addActionListener(evt -> {
            final JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            final String[] options = {"Single Chat", "Group Chat"};
            final int chatType = JOptionPane.showOptionDialog(null, "Select Chat Type:", "New Chat",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            final JTextField chatNameField = new JTextField(15);
            panel.add(new JLabel("Chat Name: "));
            panel.add(chatNameField);

            if (chatType == 0 || chatType == 1) {
                final JTextField usersField = new JTextField(chatType == 0 ? 15 : 20);
                final String labelText = chatType == 0 ? "Enter Username: " : "Enter UserNames (comma-separated): ";
                panel.add(new JLabel(labelText));
                panel.add(usersField);

                final int result = JOptionPane.showConfirmDialog(null, panel,
                        chatType == 0 ? "New Single Chat" : "New Group Chat", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    final String chatName = chatNameField.getText().trim();
                    final String usersInput = usersField.getText().trim();

                    if (!chatName.isEmpty()) {
                        List<String> users = null;
                        if (chatType == 0) {
                            users = List.of(usersInput);
                            if (users.size() != 1 || users.get(0).isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Error: Single Chat must have exactly one username.");
                                return;
                            }
                        }
                        else if (chatType == 1) {
                            users = List.of(usersInput.replace(" ", "").split(","));
                            if (users.size() <= 1 || users.stream().anyMatch(String::isEmpty)) {
                                JOptionPane.showMessageDialog(null, "Error: Group Chat must have more than one username.");
                                return;
                            }
                        }
                        createGroupChannelController.execute(chatName, users, loggedInViewModel.getState().getUserId());
                    }
                }
            }
        });

        deleteChannelButton.setToolTipText("Press to delete an existing chat. ");
        deleteChannelButton.addActionListener(evt -> {
            final int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete the selected channel?",
                    "Delete Channel",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                String selectedChat = chatList.getSelectedValue();
                if (selectedChat != null) {
                    String groupChannelUrl = selectedChat.substring(selectedChat.lastIndexOf(": ") + 2);
                    try {
                        sbGroupChannelManager.deleteChannelByUrl(groupChannelUrl);
                        JOptionPane.showMessageDialog(null, "Channel deleted.");

                        ((DefaultListModel<String>) chatList.getModel()).removeElement(selectedChat);

                        chatArea.removeAll();
                        chatArea.add(new JLabel("No chat selected."));
                        chatArea.revalidate();
                        chatArea.repaint();
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Channel not deleted " + e.getMessage());
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No channel selected for deletion.");
                }
            }
        });
    }

    /**
     * Updates chat area with newly sent/edited messages.
     */
    private void updateChatArea() {
        final String selectedChat = chatList.getSelectedValue();
        chatList.setSelectionBackground(new Color(204, 214, 230));
        chatList.setSelectionForeground(Color.BLACK);

        if (selectedChat != null) {
            final String groupChannelUrl = selectedChat.substring(selectedChat.lastIndexOf(": ") + 2);
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
                messagePanel.setMaximumSize(new Dimension(800, 30));
                messagePanel.setBackground(UIFactory.backgroundColour);
                // messagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                final JLabel messageLabel = new JLabel("<html><b>" + user + ":</b> " + message + "</html>");
                messageLabel.setFont(UIFactory.getTextFont());
                messageLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
                messagePanel.add(messageLabel);

                final String currentUserId = loggedInViewModel.getState().getUserId();
                if (userId.equals(currentUserId)) {
                    final JButton editButton = UIFactory.createMessageButton("edit");

                    editButton.addActionListener(evt -> {
                        final String newMessage = JOptionPane.showInputDialog("Edit Message:", message);

                        if (newMessage != null && !newMessage.trim().isEmpty() && !newMessage.equals(message)) {
                            editMessageController.execute(currentUserId, Integer.valueOf(messageId), groupChannelUrl, newMessage);
                            updateChatArea();
                        }
                    });

                    messagePanel.add(editButton);

                    // Delete message
                    final JButton deleteButton = UIFactory.createMessageButton("delete");
                    deleteButton.addActionListener(evt -> {
                        final int confirmation = JOptionPane.showConfirmDialog(
                                null,
                                "Are you certain you want to delete this message?",
                                "Delete Message",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirmation == JOptionPane.YES_OPTION) {
                            try {
                                sbMessageManager.deleteMessage("group_channels", groupChannelUrl, messageId);
                                updateChatArea();
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Message deleted",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                            catch (Exception ex) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Message not deleted" + ex.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    });
                    messagePanel.add(deleteButton);
                }
                chatArea.add(messagePanel);
            }
            chatArea.revalidate();
            chatArea.repaint();
        }
        else {
            chatArea.removeAll();
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("login") || (evt.getPropertyName().equals("newChat"))) {
            // fetch chats from sendbird

            // Initialize API client
            final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
            final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
            final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
            defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

            // Initialize SendBird managers
            final SbGroupChannelManager sbGroupChannelManager = new SbGroupChannelManager(defaultClient);

            // Get the current user ID
            final String currentUserId = loggedInViewModel.getState().getUserId();
            DefaultListModel chats = new DefaultListModel();

            if (currentUserId.length() > 0) {
                final List<SendBirdGroupChannel> groupChannels = sbGroupChannelManager
                        .listChannels(loggedInViewModel.getState().getUserId()).getChannels();

                // Add group channels to the chats list
                for (int i = 0; i < groupChannels.size(); i++) {
                    SendBirdGroupChannel groupChannel = groupChannels.get(i);
                    StringBuilder chatName = new StringBuilder();
                    chatName.append(groupChannel.getName());
                    chatName.append("                                                     : ");
                    chatName.append(groupChannel.getChannelUrl());
                    chats.add(i, chatName.toString());
                }
                // Update the chat list UI
                chatList.setModel(chats);
            }
            else {
                chatList = new JList<>();
            }
        }
    }
}
