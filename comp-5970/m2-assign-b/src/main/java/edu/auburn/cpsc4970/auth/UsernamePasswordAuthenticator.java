package edu.auburn.cpsc4970.auth;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Authenticator using a json file based database
 */
public class UsernamePasswordAuthenticator implements AuthProviderInterface{
   /**
    * Logging class
    */
   private final Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthenticator.class);

   /**
    * Login handler taking a simple username and password to validate
    * @param username
    * @param password
    * @return User session context
    * @throws AULoginException
    * @throws IOException
    */
   @Override
   public UserSession login(String username, String password) throws AULoginException, IOException {
   
      logger.info("Checking Username with supplied Password");
   
         // Check for null password
      if (password == null) {
         throw new AULoginException("Password can not be null.");
      }
   
         // Check for null username
      if (username == null) {
         throw new AULoginException("Username can not be null.");
      }
   
      // Find the user database file on the classpath
      InputStream in = this.getClass().getClassLoader().getResourceAsStream("users.json");
   
      // Read in json database of
      String result = IOUtils.toString(in, StandardCharsets.UTF_8);
      HashMap<String,String> userList = Utils.parseUserJson(result);
   
      // Check for valid user
      if (!userList.containsKey(username))
         throw new AULoginException ("User does not exist.");
   
      // Check for valid password
      if (!password.equals(userList.get(username)))
         throw new AULoginException ("Password is invalid for specified user");
   
      UserSession userSession = new UserSession(username, password);
      
      // login success...
      logger.info("Login Success!");
   
      return userSession;
   }

/**
  * Passwords need to be at least 10 characters long
  * @param password to be checked.
  * @return
  */
   public boolean checkPasswordComplexity(String password) {
      if (password.length() <= 10 && !containsSymbol(password)) {
         return false;
      }
          
      return true;
   }
      
/**
  * Checks if the password contains at least one symbol.
  * @param password to be checked.
  * @return true if the password contains at least one symbol, false otherwise.
  */
   private boolean containsSymbol(String password) {
      for (char c : password.toCharArray()) {
         if (isSymbol(c)) {
            return true;
         }
      }
      return false;
   }
      
/**
  * Checks if the character is a symbol.
  * @param c the character to be checked.
  * @return true if the character is a symbol, false otherwise.
  */
   private boolean isSymbol(char c) {
      String symbols = "!@#$%^&*=";
      return symbols.contains(Character.toString(c));
   }
}
