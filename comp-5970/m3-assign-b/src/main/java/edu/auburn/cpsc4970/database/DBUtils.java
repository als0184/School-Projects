package edu.auburn.cpsc4970.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ResourceBundle;

public class DBUtils {

   /**
    * Logging class
    */
   private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);
   private static ResourceBundle systemProperties;
   
  /**
      * Utility method to retrieve a value from the resources/system.properties file.
      * @param propertyKey the key of the property to retrieve
      * @return the value associated with the key
      */
     
   public static String getProperty(String propertyKey) {
      if (systemProperties == null)
         systemProperties = ResourceBundle.getBundle("system");
   
      return systemProperties.getString(propertyKey);
   }


   public static String getNameForUser (String searchUser) {
   
      String userValue = null;
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      // Retrieve values from system.properties
      String dbUser = getProperty("db.user");
      String dbPassword = getProperty("db.password");
   
      try {
      
         // Load the database driver
         Class.forName("org.postgresql.Driver");
      
         // Registering drivers
         DriverManager.registerDriver(new org.postgresql.Driver());
      
         // Get connection to the remote database
         connection = DriverManager.getConnection("jdbc:postgresql://secretsdb.au-csse-cpsc4970.com:5432/user_db", dbUser, dbPassword);         
         // Setup query to execute
         preparedStatement = connection.prepareStatement("select name, password from users where login= ?");
         preparedStatement.setString(1, searchUser);
      
         logger.info("Looking up name for user: " + searchUser );
      
         resultSet = preparedStatement.executeQuery();
      
         while (resultSet.next()) {
            userValue = resultSet.getString(1);
         
            System.out.println("Found: "+userValue);
         
         }
      
         // Closing the connection
         preparedStatement.close();
         connection.close();
      }
      
      // Catch block to handle exceptions
      catch (Exception ex) {
         // Display message when exceptions occurs
         System.err.println(ex);
      }
      finally {
         // Quietly try to close connections.
         try { resultSet.close(); } 
         catch (Exception e) { /* Ignored */ }
         try { preparedStatement.close(); } 
         catch (Exception e) { /* Ignored */ }
         try { connection.close(); } 
         catch (Exception e) { /* Ignored */ }
      }
   
      return userValue;
   }
}
