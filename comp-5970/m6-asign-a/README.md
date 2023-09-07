# Assignment 6a 

In this assignment we are changing our AuthProvider library to use AWS Secrets Management tool for looking up credentials when we need to connect to a database.  Normally, we would use a database connection pool such as [Apache Commons DBCP](https://commons.apache.org/proper/commons-dbcp/) in an application server (Tomcat) that would be initialized on startup.  For our assignments, we have been connecting every time we need to run a query, very inefficient, but for learning purposes.  

### Assignment objectives:
- Create our own secret in AWS Secrets Manager
- Modify our AuthProvider library to integrate in AWS secrets management tool.
- Configure build pipeline to perform dependency scanning and vulnerabiltiy checking
- Resolve dependency issues and vulnerability issues.


Grading will be based on commit history, pipeline execution, and dependency report  reflecting completing the steps below.

# Create Database Secret in AWS Secret Manager

1. Clone the repository to your local development environment.

2. Run mvn package to ensure our AuthProvider builds correctly.

3. Login in AWS with URL below and with specified username/password.  The user only has permissions to create and modify secrets in AWS Secrets Manager.
    ```text
   https://135372068798.signin.aws.amazon.com/console
   Login User: cpsc4970user
   Password: S@mford91
    ```

4. Navigate to the AWS Secrets Manager Tool either by selecting it in the services menu on the top left or using the search field at the top of the page.

5. Select the "Store a new secret" button to setup a new secret with the following configuration:
    ```
    Secret Type: "Other type of secret"
   
    Add 3 rows of key=value pairs:
   
    postgres-user=student_read
    postgres-password=S@mford91!
    postgres-jdbc-url=jdbc:postgresql://secretsdb.au-csse-cpsc4970.com:5432/user_db
    ```

6. Select "Next" and give the Secret a name.  Replacing **XXX** with your initials.
   ```
   XXX-secret
   ```

7. Hit "Next" and then "Next" again as we will not be setting up secret rotation.  Take notice of this screen as it shows how secrets can be automatically updated.  A best practice is to change secrets on a policy driven schedule.  This ensures any secrets known to individuals will expire at some point.  As we can see a person must manually enter the secrets and as such it represents a risk that they record the secret and can use it maliciously after the leave the organization.

8. The next page is a summary of our configuration.  At the bottom is sample code for various languages that can be used to access the secret.  Hitting "Store" should return you to the page with your secret listed.

# Implementing application integration to Secrets Manager

9. Create branch "feature-secrets-integration" and checkout

10. AWS provices a java library (a dependency!) to integrate into Secrets Manager.  Let's add it as a dependency into our pom.xml.
   ```xml
   <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>secretsmanager</artifactId>
      <version>2.20.109</version>
   </dependency>
   ```

11. We can tell Maven to bring down any dependencies defined the **pom.xml**.  Run the follow Maven command to see the dependencies downloaded.
   ```commandline
   mvn dependency:resolve
   ```
- This is an example where you can see the AWS Secrets Manager dependecy has other dependencies that must be downloaded.  Dependency scanning is important to validate all these other dependencies are checked.

12. It is good practice to create utility classes for commonly used code logic. Create an AWSUtils utility class to look up the values from AWS Secrets Manager.  Add the following class under the edu.auburn.cpsc4970 package:
   ```java
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
   ```


11. Let's add a test to make sure our utility class can retrieve the secret from AWS.
   ```java
   package edu.auburn.cpsc4970.auth;
   
   import org.json.JSONObject;
   import org.junit.Test;
   
   import static org.junit.Assert.assertEquals;
   import static org.junit.Assert.assertTrue;
   
   public class AWSUtilsTest {
   
      @Test
      public void testRetrieveValue () {
   
         String jsonString = AWSUtils.getSecret("XXX-secret");
   
         if (jsonString==null) {
            assertTrue("Null value returned from AWSUtils.getSecret", false);
         }
   
         JSONObject obj = new JSONObject(jsonString);
         String user = obj.getString("postgres-user");
         assertEquals("student_read", user);
      }
   }
   ```
- **Replace XXX with your initials in AWSUtils.getSecret() call**

13. For the test to run it needs to have AWS API Credentials to access the Secrets Manager.  For our cpsc4970-user you need to set the following environment variables below.  Remember these values only remain for your current terminal/console window.  You will need to reset them if you exit or run a new terminal/console.
Linux/Mac
   ```commandline
   $ export AWS_ACCESS_KEY_ID=AKIAR7BGN267BPMZLU6S
   $ export AWS_SECRET_ACCESS_KEY=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
   $ export AWS_DEFAULT_REGION=us-east-2
   ```
Windows
   ```commandline
   C:\> SET  AWS_ACCESS_KEY_ID=AKIAR7BGN267BPMZLU6S
   C:\> SET  AWS_SECRET_ACCESS_KEY=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
   C:\> SET  AWS_DEFAULT_REGION=us-east-2
   ```

14. Run mvn test and make sure the test passes. If successful, you should see the JSON return object logged to the console.


15. Now let's update our DBUtils to lookup secrets from the Secrets Manager instead of having them in the code or a local properties file.  Add the following method to our DBUtils to lookup the Postgres connection details:
   ```java
       private static void setDBCredentials() {
   
           // If credentials already set we are done.
           if (dbUser!=null) return;
   
           // Get JSON string value
           String jsonString = AWSUtils.getSecret("XXX-secret");
   
           if (jsonString == null) return;
   
           // Parse JSON string object into credential values.
           JSONObject jsonObject = new JSONObject(jsonString);
           dbUser = jsonObject.getString("postgres-user");
           dbPassword = jsonObject.getString("postgres-password");
           dbUrl = jsonObject.getString("postgres-jdbc-url");
   
           }
   ```
- Dont forget the package import at the top: "import edu.auburn.cpsc4970.auth.AWSUtils;"

- Replace **XXX** with your initials in AWSUtils.getSecret() call


15. You will also need to add private String class variables for the dbUser, dbPassword, dbUrl values.
   ```java
       // DB Credentials
       private static String dbUser = null;
       private static String dbPassword = null;
       private static String dbUrl = null;
   ```


16. In the DBUtils class replace the hardcode values with these credential values.  Remember to call the setCredentials() method before the DriverManager.getConnection()

17. Run mvn test to ensure your DBUtils tests pass with the credentials.

18. Congrats!  You have now successfully integrated into a Secrets Manager or Key Management Store (KMS)

19. Add, Commit, Push, and Merge branch to **main**.

# Create Build Pipeline to Build and Deploy our AuthProvider Library

20. Create branch **pipeline** and checkout

21. Let's update our pom.xml to have a released version of our library.  Update the version **1.3**  

22. Using previous assignment as examples create a gitlab pipeline file with the following stages
- compile
- test
- build
- depend-scan
- deploy

The script for each stage is a maven goal/target:   

> **mvn \<target\> -s ci_settings.xml**

24. Add, Commit, Push and Merge branch to **main**.

23. Make sure your pipeline completes successfully. Check Vulnerability report. Check package was deployed to package registry with the correct version  


