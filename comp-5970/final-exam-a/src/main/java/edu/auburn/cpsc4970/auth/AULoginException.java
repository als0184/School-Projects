package edu.auburn.cpsc4970.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception handling for login errors
 */
public class AULoginException extends Exception {

    private final Logger logger = LoggerFactory.getLogger(AULoginException.class);

    public AULoginException(String message) {
        super(message);

    }
}
