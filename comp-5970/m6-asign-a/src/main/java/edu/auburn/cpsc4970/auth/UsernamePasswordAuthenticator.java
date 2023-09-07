package edu.auburn.cpsc4970.auth;

import edu.auburn.cpsc4970.database.DBUtils;
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
    public UserSession login(String username, String password) throws AULoginException {

        logger.info("Login for {}",username);

        if (username == null) throw new AULoginException("Username can not be null");
        if (password == null) throw new AULoginException("Password can not be null");

        if(!DBUtils.userExists(username)) {
            throw new AULoginException("User \"" + username + "\" does not exist.");
        }

        String userPassword = DBUtils.getPasswordForUser(username);

        if (!password.equals(userPassword)) {
            throw new AULoginException("Password for \"" + username + "\" is invalid");
        }

        UserSession userSession = new UserSession(username);

        return userSession;
    }

    /**
     * Passwords need to be at least 2 characters long
     * @param password to be checked.
     * @return
     */
    public boolean checkPasswordComplexity(String password) {

        if (password.length() <= 2) return false;

        return true;
    }

}
