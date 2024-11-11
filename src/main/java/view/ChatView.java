package view;

import app.SendMessages;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.logout.LogoutController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;

    //private final JLabel username;
    private final JButton logOutButton;
    private final JButton newChatButton;
    private final JButton profileButton;
    // private final JButton logOut;

    private final JList<String> chatList;
    private final JTextArea chatArea;

    private final JTextField passwordInputField = new JTextField(15);
    //private final JButton changePassword;

    // private final JButton send message
    private final Map<String, List<String>> chatMessages = new HashMap<>();
    private JTextField messageInputField;
    private JButton sendButton;
    private SendMessages sendMessages = new SendMessages();

    public ChatView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Chats");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

//        final LabelTextPanel passwordInfo = new LabelTextPanel(
//                new JLabel("Password"), passwordInputField);

//        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        // username = new JLabel();
//
//        final JPanel buttons = new JPanel();
//        logOut = new JButton("Log Out");
//        buttons.add(logOut);
//
//        changePassword = new JButton("Change Password");
//        buttons.add(changePassword);

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

        // TODO: Implement sendButton.addActionListener(e -> sendMessages.sendMessage());
        sendButton.addActionListener(evt -> {
            String messageText = messageInputField.getText().trim();
            if (!messageText.isEmpty()) {
                sendMessages.sendMessage();
                messageInputField.setText("");
                updateChatArea();
            }
        });

        // left panel for the chats
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        String[] sampleChats = {"Chat 1", "Chat 2", "Chat 3"}; // replace with actual chats
        chatList = new JList<>(sampleChats);
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.setSelectedIndex(0); // first chat
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

        // action listeners for buttons
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
        // TODO: implement this
        // newChatButton.addActionListener();
        newChatButton.addActionListener(evt -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JTextField chatNameField = new JTextField(15);
            JTextField userNameField = new JTextField(15);

            panel.add(new JLabel("New Chat Name: "));
            panel.add(chatNameField);
            panel.add(Box.createVerticalStrut(10));
            panel.add(new JLabel("Enter Username: "));
            panel.add(userNameField);

            int result = JOptionPane.showConfirmDialog(null, panel, "New Chat", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String newChatName = chatNameField.getText().trim();
                String newUserName = userNameField.getText().trim();

                if (!newChatName.isEmpty() && !newUserName.isEmpty()) {
                    DefaultListModel<String> model = (DefaultListModel<String>) chatList.getModel();
                    String chatEntry = newChatName + " (Chat with: " + newUserName + ")";
                    model.addElement(newChatName);
                    chatList.setSelectedValue(chatEntry, true);
                } else {
                    JOptionPane.showMessageDialog(null, "Error in either chat name or username. ");
                }
            }
        });

        // profileButton.addActionListener();
        profileButton.addActionListener(evt -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            String currentUseername = loggedInViewModel.getState().getUsername();
            String currentPassword = loggedInViewModel.getState().getPassword();

            JTextField userNameField = new JTextField(currentUseername, 15);
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
                String newUsername = JOptionPane.showInputDialog(null, "Enter new username: ", currentUseername);
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
        String selectedChat = chatList.getSelectedValue();
        chatArea.setText("Display messages for: " + selectedChat);
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    //        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final LoggedInState currentState = loggedInViewModel.getState();
//                currentState.setPassword(passwordInputField.getText());
//                loggedInViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//
//        changePassword.addActionListener(
//                // This creates an anonymous subclass of ActionListener and instantiates it.
//                evt -> {
//                    if (evt.getSource().equals(changePassword)) {
//                        final LoggedInState currentState = loggedInViewModel.getState();
//
//                        this.changePasswordController.execute(
//                                currentState.getUsername(),
//                                currentState.getPassword()
//                        );
//                    }
//                }
//        );
//
//
//
//        this.add(title);
//        this.add(usernameInfo);
//        this.add(username);
//
//        this.add(passwordInfo);
//        this.add(passwordErrorField);
//        this.add(buttons);
//    }
//
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
//
//    public void setChangePasswordController(ChangePasswordController changePasswordController) {
//        this.changePasswordController = changePasswordController;
//    }
}