package util;
import java.security.MessageDigest;

import model.User;

public class PasswordUtilities {

    public static boolean isValidLogin(String username, String password) {

        String encryptedPassword = getEncryptedPassword(password);
        User user = new User(username, encryptedPassword);
        User userInDB = User.fetchUserFromDB(username, encryptedPassword);

        return user.equals(userInDB);
    }

    public static String getEncryptedPassword(String str) {
        try {
            MessageDigest d = MessageDigest.getInstance("MD5");
            byte[] b = str.getBytes();
            d.update(b);
            return byteArrayToHexString(d.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] b) {
        String str = "";
        for (byte aB : b) {
            int value = aB & 0xFF;
            if (value < 16) {
                str += "0";
            }
            str += Integer.toHexString(value);
        }

        return str.toUpperCase();
    }

    public static void addUserEntryToDB(String username, String password) {
        try {
            String encryptedPassword = getEncryptedPassword(password);
            DBHelper.doUpdate("INSERT INTO `user` (username, password) VALUES ('"
                    + username + "', '" + encryptedPassword + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PasswordUtilities.addUserEntryToDB("cs645", "sp2015");
        PasswordUtilities.addUserEntryToDB("pwang", "123");
        PasswordUtilities.addUserEntryToDB("abc", "123");
        PasswordUtilities.addUserEntryToDB("sdsu", "cs645");
        PasswordUtilities.addUserEntryToDB("test", "cs645");
    }
}
