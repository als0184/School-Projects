package edu.auburn.cpsc4970.auth;

/**
 * Login factory for providing concrete login handlers.
 */
public class AuthProviderFactory {

    public static AuthProviderInterface getAuthProvider() {

        return new UsernamePasswordAuthenticator();

    }
}
