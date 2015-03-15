package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DBHelper implements java.io.Serializable {

    private static final long serialVersionUID  = 1L;
    // private static String USER = "jadrn048";
    // private static String PASS = "outlet";
    // private static String CONNECTION_STRING =
    // "jdbc:mysql://opatija:3306/jadrn048";
    private static String     USER              = "root";
    private static String     PASS              = "";
    private static String     CONNECTION_STRING = "jdbc:mysql://localhost:3306/jadrn048";
    private static String     DRIVER_CLASS      = "com.mysql.jdbc.Driver";

    public static Vector<String[]> doQuery(String query) {
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        Vector<String[]> results = new Vector<String[]>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ResultSetMetaData md = resultSet.getMetaData();
            int numCols = md.getColumnCount();

            while (resultSet.next()) {
                String[] tmp = new String[numCols];
                for (int i = 0; i < numCols; i++) {
                    tmp[i] = resultSet.getString(i + 1);
                }
                results.add(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
            }
        }

        return results;
    }

    public static int doUpdate(String query) {
        Connection connection = getConnection();
        Statement statement = null;
        int result = -1;

        try {
            statement = connection.createStatement();
            result = statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
            }
        }

        return result;
    }

    private static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER_CLASS).newInstance();
            connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName(DRIVER_CLASS);
            System.out.println("MySQL JDBC driver loaded ok.");

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
