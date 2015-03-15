package model;

import java.util.Vector;

import util.DBHelper;

public class User {

    private String username;
    private String encryptedPassword;

    public User(String username, String password) {
        this.username = username;
        this.encryptedPassword = password;
    }

    public static User fetchUserFromDB(String username, String encryptedPassword) {
        try {
            Vector<String[]> result = DBHelper
                    .doQuery("SELECT username, password FROM `user` "
                            + "WHERE username = '"
                            + username
                            + "' AND password = '"
                            + encryptedPassword + "'");
            if (!result.isEmpty()) {
                String resultUsername = result.elementAt(0)[0];
                String resultPassword = result.elementAt(0)[1];
                return  new User(resultUsername, resultPassword);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean equals(User user) {
        return user != null && this.username.equals(user.username) && this.encryptedPassword.equals(user.encryptedPassword);
    }
}
