package edu.auburn.cpsc4970.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session class that holds information about the current user logged on.
 */
public class UserSession {
    /**
     * Logging class
     */
    Logger logger = LoggerFactory.getLogger(UserSession.class);

    private int permissionLevel = NONE;
    public static final int NONE = 0;
    public static final int USER = 1;
    public static final int ADMIN = 2;

    private String userName;

    UserSession(String userName) {

        logger.info("Created new user session for: {}", userName);

        this.userName = userName;
        this.permissionLevel = NONE;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPermission (int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public int getPermissionLevel () {
        return permissionLevel;
    }

}
