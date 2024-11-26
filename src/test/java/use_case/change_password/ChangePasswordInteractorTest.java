package use_case.change_password;

import data_access.FileUserDataAccessObject;
import entity.SbUserFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordInteractorTest {
    private ChangePasswordInteractor changePasswordInteractor;
    private String testPassword;
    private String newPassword;
    private String testUsername;
    private User testUser;

    @BeforeEach
    void setUp() {
        final String apiToken = "e4fbd0788231cf40830bf62f866aa001182f9971";
        final String applicationId = "049E2510-3508-4C99-80F9-A3C24ECA7677";
        final ApiClient defaultClient = Configuration.getDefaultApiClient().addDefaultHeader("Api-Token", apiToken);
        defaultClient.setBasePath("https://api-" + applicationId + ".sendbird.com");

        UserFactory userFactory = new SbUserFactory(defaultClient);

        testPassword = "password123";
        newPassword = "newpassword321";
        testUsername = "user1";
        testUser = userFactory.create(testUsername, testPassword);

        try {
            ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface = new FileUserDataAccessObject("userDataFile", userFactory);
            LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
            ChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenter(loggedInViewModel);

            changePasswordInteractor = new ChangePasswordInteractor(changePasswordDataAccessInterface, changePasswordPresenter, userFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void execute() {
        assertEquals(testPassword, testUser.getPassword());

        ChangePasswordInputData changePasswordInputData = new ChangePasswordInputData(testUsername, newPassword);
        changePasswordInteractor.execute(changePasswordInputData);

        // assertEquals(newPassword, testUser.getPassword());
    }
}