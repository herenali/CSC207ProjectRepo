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
            String newChatName = JOptionPane.showInputDialog("New Chat: ");
            if (newChatName != null && !newChatName.trim().isEmpty()){
                DefaultListModel<String> model = (DefaultListModel<String>) chatList.getModel();
                model.addElement(newChatName);
                chatList.setSelectedValue(newChatName, true);
                chatArea.setText("New chat: " + newChatName);
            }
        });
        // profileButton.addActionListener();
        profileButton.addActionListener(evt -> {
            JOptionPane.showMessageDialog(this, "Profile: \nUsername: " + loggedInViewModel.getState().getUsername(), "User Profile", JOptionPane.INFORMATION_MESSAGE);
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