package use_case.login;

import entity.User;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
//            final String pwd = userDataAccessObject.get(username).getPassword();
            final String pwd = userDataAccessObject.getPassword(username);
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {
//                final User user = userDataAccessObject.get(loginInputData.getUsername());
//                userDataAccessObject.setCurrentUsername(user.getName());
                userDataAccessObject.setCurrentUsername(username);
                final String userId = userDataAccessObject.getUserId(username);
                final LoginOutputData loginOutputData = new LoginOutputData(username, userId, false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
