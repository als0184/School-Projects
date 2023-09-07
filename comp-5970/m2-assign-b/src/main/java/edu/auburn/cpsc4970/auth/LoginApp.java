package edu.auburn.cpsc4970.auth;

import javax.security.auth.login.LoginException;
import java.io.InputStream;

/**
 * App for call login handlers locally.
 *
 */
public class LoginApp
{
    public static void main( String[] args )
    {
        AuthProviderInterface authProvider = AuthProviderFactory.getAuthProvider();

        try {
            authProvider.login("Jordan","1957");
            authProvider.login("Jordan","19s57");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
