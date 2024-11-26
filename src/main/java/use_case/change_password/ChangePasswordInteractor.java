package use_case.change_password;

import entity.UserFactory;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = changePasswordDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        final String username = changePasswordInputData.getUsername();
        final String newPassword = changePasswordInputData.getPassword();
        userDataAccessObject.changePassword(username, newPassword);

        final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(username,
                                                                                  false);
        userPresenter.prepareSuccessView(changePasswordOutputData);
    }
}
