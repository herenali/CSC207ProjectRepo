package view;

import interface_adapter.change_password.LoggedInViewModel;
import org.junit.jupiter.api.BeforeEach;


import org.junit.jupiter.api.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ChatViewTest {
    private LoggedInViewModel loggedInViewModel;
    private ChatView chatView;

    @BeforeEach
    public void init(){
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient()
                .addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        loggedInViewModel = new LoggedInViewModel();
        chatView = new ChatView(loggedInViewModel);
    }

    @Test
    public void testChatViewInitialization() {
        assertNotNull(chatView, "ChatView should be initialized");
    }
}
