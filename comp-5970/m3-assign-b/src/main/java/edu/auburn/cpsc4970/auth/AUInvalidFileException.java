package edu.auburn.cpsc4970.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception handling for invalid file specification
 */
public class AUInvalidFileException extends Exception {

    private final Logger logger = LoggerFactory.getLogger(AUInvalidFileException.class);

    AUInvalidFileException(String message) {
        super(message);

        logger.error("Invalid File Error: "+ getMessage());
    }
}
