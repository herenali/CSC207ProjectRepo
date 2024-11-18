package data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import entity.User;
import entity.UserFactory;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * DAO for user data implemented using a File to persist the data.
 */
public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
                                                 LoginUserDataAccessInterface,
                                                 ChangePasswordUserDataAccessInterface,
                                                 LogoutUserDataAccessInterface {

    private static final String HEADER = "username,password,userId";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();
    // A map of usernames to user data (username, password, userId)
    private final Map<String, List<String>> accountsWithUserId = new HashMap<>();
    private String currentUsername;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) throws IOException {

        csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);
        headers.put("userId", 2);

        if (csvFile.length() == 0) {
            save();
        }
        else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be%n: %s%but was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String username = String.valueOf(col[headers.get("username")]);
                    final String password = String.valueOf(col[headers.get("password")]);
                    final String userId = String.valueOf(col[headers.get("userId")]);
                    final List<String> userData = new ArrayList<>();
                    userData.add(username);
                    userData.add(password);
                    userData.add(userId);
                    accountsWithUserId.put(username, userData);
                }
            }
        }
    }

    private void save() {
        final BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (List<String> userData : accountsWithUserId.values()) {
                final String username = userData.get(0);
                final String password = userData.get(1);
                final String userId = userData.get(2);
                final String line = String.format("%s,%s,%s", username, password, userId);
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        final List<String> userData = new ArrayList<>();
        userData.add(user.getName());
        userData.add(user.getPassword());
        userData.add(user.getUserId());
        accountsWithUserId.put(user.getName(), userData);
        this.save();
    }

    @Override
    public String getPassword(String username) {
        if (accountsWithUserId.containsKey(username)) {
            return accountsWithUserId.get(username).get(1);
        }
        else {
            return null;
        }
    }

    @Override
    public String getUserId(String username) {
        if (accountsWithUserId.containsKey(username)) {
            return accountsWithUserId.get(username).get(2);
        }
        else {
            return null;
        }
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public boolean existsByName(String identifier) {
        return accountsWithUserId.containsKey(identifier);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        final List<String> newUserData = new ArrayList<>();
        if (accountsWithUserId.containsKey(username)) {
            final List<String> oldUserData = accountsWithUserId.get(username);
            newUserData.add(oldUserData.get(0));
            newUserData.add(newPassword);
            newUserData.add(oldUserData.get(2));
            accountsWithUserId.put(username, newUserData);
        }
    }
}
