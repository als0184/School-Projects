package edu.auburn.cpsc4970.database;

import edu.auburn.cpsc4970.auth.AULoginException;
import edu.auburn.cpsc4970.auth.UserSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.auburn.cpsc4970.auth.AWSUtils;

import java.sql.*;

public class DBUtils {

   /**
    * Logging class
    */
   private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

   // DB Credentials
   private static String dbUser = null;
   private static String dbPassword = null;
   private static String dbUrl = null;


   public static String getNameForUser (String searchUser) {
   
      String userValue = null;
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
   
      try {
      
         // Get connection to the remote database
         setDBCredentials();
         connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      
         // Setup query to execute
         preparedStatement = connection.prepareStatement("select name, password from users where login='" + searchUser+"'");
      
         logger.debug("Looking up name for user: {}",searchUser );
      
         resultSet = preparedStatement.executeQuery();
      
         while (resultSet.next()) {
            userValue = resultSet.getString(1);
         
            logger.debug("Found: {}",userValue);
         
         }
      
         // Closing the connection
         preparedStatement.close();
         connection.close();
      }
      
      // Catch block to handle exceptions
      catch (Exception ex) {
         // Display message when exceptions occurs
         ex.printStackTrace();
         logger.error("Error looking up user: {}",ex.toString());
      }
      finally {
      // Quietly try to close connections.
         try {
            if (resultSet != null) {
               resultSet.close();
            }
         } catch (Exception e) { /* Ignored */ }
         try {
            if (preparedStatement != null) {
               preparedStatement.close();
            }
         } catch (Exception e) { /* Ignored */ }
         try {
            if (connection != null) {
               connection.close();
            }
         } catch (Exception e) { /* Ignored */ }
      }
   
      return userValue;
   }

   public static String getPasswordForUser (String searchUser) throws AULoginException {
   
      String password = "";
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
   
      try {
      
         // Get connection to the remote database
         setDBCredentials();
         connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      
         logger.debug("Looking up password for user: {}", searchUser );
      
         // Setup query to execute
         preparedStatement = connection.prepareStatement("select password from users where login = ?");
         preparedStatement.setString(1,searchUser);
      
         resultSet = preparedStatement.executeQuery();
      
         while (resultSet.next()) {
            password += resultSet.getString(1);
         }
               // Log the information we retrieved from the database.
         logger.info("Found password for specified user.");
      
      }
      
      // Catch block to handle exceptions
      catch (Exception ex) {
         // Display message when exceptions occurs
         logger.error("Error retrieving password: {}",ex.toString());
         throw new AULoginException(ex.getMessage());
      }
      finally {
      // Quietly try to close connections.
         try {
            if (resultSet != null) {
               resultSet.close();
            }
         } catch (Exception e) { /* Ignored */ }
         try {
            if (preparedStatement != null) {
               preparedStatement.close();
            }
         } catch (Exception e) { /* Ignored */ }
         try {
            if (connection != null) {
               connection.close();
            }
         } catch (Exception e) { /* Ignored */ }
      }
      return password;
   }

   public static int getPermissionForUser (String searchUser) throws AULoginException {
   
      int permisssion = UserSession.NONE;
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
   
      try {
      
         // Get connection to the remote database
         setDBCredentials();
         connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
         logger.debug("Looking up password for user: {}", searchUser );
      
         // Setup query to execute
         preparedStatement = connection.prepareStatement("select permission from users where login = ?");
         preparedStatement.setString(1,searchUser);
      
         resultSet = preparedStatement.executeQuery();
      
         int numRows = 0;
         while (resultSet.next()) {
            permisssion = resultSet.getInt(1);
            logger.info("Found permission: {} for user {}",permisssion, searchUser);
         }
      
         return permisssion;
      }
      
      // Catch block to handle exceptions
      catch (Exception ex) {
         // Display message when exceptions occurs
         logger.error("Error retrieving password: {}",ex.toString());
         throw new AULoginException(ex.getMessage());
      }
      finally {
      // Quietly try to close connections.
         try {
            if (resultSet != null) {
               resultSet.close();
            }
         } catch (Exception e) { /* Ignored */ }
         try {
            if (preparedStatement != null) {
               preparedStatement.close();
            }
         } catch (Exception e) { /* Ignored */ }
         try {
            if (connection != null) {
               connection.close();
            }
         } catch (Exception e) { /* Ignored */ }
      }
   
   }

   /**
    * @param searchUser
    * @return
    */
   public static boolean userExists (String searchUser) {
   
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
   
      try {
      
         // Get connection to the remote database
         setDBCredentials();
         connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
      
         // Setup query to execute
         preparedStatement = connection.prepareStatement("select name from users where login=?");
         preparedStatement.setString(1,searchUser);
      
         logger.info("Looking up name for user: " + searchUser );
      
         resultSet = preparedStatement.executeQuery();
      
         logger.info("Rows Returned: {}",resultSet.getFetchSize());
      
         if (!resultSet.next()) 
            return false;
      
         return true;
      }
      
      // Catch block to handle exceptions
      catch (Exception ex) {
         // Display message when exceptions occurs
         ex.printStackTrace();
         logger.error("Error retrieving user: {}",ex);
      }
      finally {
      // Quietly try to close connections.
         try {
            if (resultSet != null) {
               resultSet.close();
            }
         } catch (Exception e) { /* Ignored */ }
         try {
            if (preparedStatement != null) {
               preparedStatement.close();
            }
         } catch (Exception e) { /* Ignored */ }
         try {
            if (connection != null) {
               connection.close();
            }
         } catch (Exception e) { /* Ignored */ }
      }
   
      return false;
   }

   private static void setDBCredentials() {
   
      // If credentials already set we are done.
      if (dbUser!=null) 
         return;
   
      // Get JSON string value
      String jsonString = AWSUtils.getSecret("msb0094-secret");
   
      if (jsonString == null) 
         return;
   
      // Parse JSON string object into credential values.
      JSONObject jsonObject = new JSONObject(jsonString);
      dbUser = jsonObject.getString("postgres-user");
      dbPassword = jsonObject.getString("postgres-password");
      dbUrl = jsonObject.getString("postgres-jdbc-url");
   
   }


}
