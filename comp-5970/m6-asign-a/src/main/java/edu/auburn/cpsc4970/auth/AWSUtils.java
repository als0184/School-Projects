package edu.auburn.cpsc4970.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.*;

public class AWSUtils {

    private AWSUtils(){}
    /**
     * Logging class
     */
    private static final Logger logger = LoggerFactory.getLogger(AWSUtils.class);

    public static String getSecret(String secretName) {

        String returnValue = null;

        Region region = Region.of("us-east-2");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder().region(region).build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
        
        GetSecretValueResponse getSecretValueResponse = null;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
            returnValue=getSecretValueResponse.secretString();
            logger.debug("Secret: {}", returnValue);
        } catch (SecretsManagerException e) {
            logger.error("Error retrieving secret: {}",e.toString());
            return null;
        }

        return returnValue;
    }

}

