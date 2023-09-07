package edu.auburn.cpsc4970.database;

import edu.auburn.cpsc4970.auth.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBUtils {

    /**
     * Logging class
     */
    private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);


    public static String getNameForUser (String searchUser) {

        String userValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            // Load the database driver
            Class.forName("org.postgresql.Driver");

            // Registering drivers
            //DriverManager.registerDriver(new org.postgresql.Driver());

            String dbUser = Utils.getProperty("db.user");
            String dbPassword = Utils.getProperty("db.password");
            String dbUrl = Utils.getProperty("db.url");

            // Get connection to the remote database
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Setup query to execute
            preparedStatement = connection.prepareStatement("select name, password from users where login ='" + searchUser+"'");

            logger.info("Looking up name for user: " + searchUser );

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userValue = resultSet.getString(1);

                logger.info("Found: "+userValue);

            }

            // Closing the connection
            preparedStatement.close();
            connection.close();
        }

        // Catch block to handle exceptions
        catch (Exception ex) {
            // Display message when exceptions occurs
            ex.printStackTrace();
            System.err.println(ex);
        }
        finally {
            // Quietly try to close connections.
            try { resultSet.close(); } catch (Exception e) { /* Ignored */ }
            try { preparedStatement.close(); } catch (Exception e) { /* Ignored */ }
            try { connection.close(); } catch (Exception e) { /* Ignored */ }
        }

        if (userValue == null) return "";

        return userValue;
    }
}
