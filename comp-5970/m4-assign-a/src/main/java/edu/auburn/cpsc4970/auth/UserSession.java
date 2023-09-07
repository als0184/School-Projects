package edu.auburn.cpsc4970.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSession {
    /**
     * Logging class
     */
    Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthenticator.class);

    private String userName;
    private String password;

    UserSession(String userName, String password) {


        logger.info("Created new user session for: {}", userName);

        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
