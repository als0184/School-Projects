package edu.auburn.cpsc4970.auth;

import javax.security.auth.login.LoginException;

/**
 *  Interface for login providers
 */
public interface AuthProviderInterface {

    /**
     *
     * @param userName - username to validate
     * @param passWord - password associated with user
     * @throws Exception - {@link LoginException} if user authentication fails. IOException if database file can not be read
     */
    public UserSession login (String userName, String passWord) throws Exception;

}
