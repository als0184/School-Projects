# Assignment 5a - DAST Security Testing a Java Spring Boot Web Application Deployed to AWS Elastic Beanstalk

In this assignment you will be building a basic web application with Java Spring Boot, configuring a Gitlab build pipeline to deploy to AWS Elastic Beanstalk, and execute a ZAP DAST scan.

### Assignment objectives:
- Build a based Spring Boot Web Application
- Build and run the web application locally
- Create a Gitlab pipeline to build and deploy the application to AWS Elastic Beanstalk
- Execute a ZAP DAST scan through a Gitlab pipeline


It is recommended you use Intellij, Eclipse, or VS Code (in that order) for this assignment.  The syntax highlighting and error checking is extremely helpful and productive for building more complex Java applications.

Your application will be deployed to [AWS Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk/), which is a cloud based web application hosting server provided by AWS.  Application environments have already been setup for each of your applications.  

Grading will be based on commit history, pipeline execution, and successful AWS deployment reflecting completing the steps below.

# Clone and Build Web Application

1. Clone the repository to your local development environment.


This project was created with the following [Spring Initializr](https://start.spring.io/) options:

- Project: Maven
- Language: Java
- Version 2.7.0
- Group: edu.auburn.comp4970
- Artifact: webapp5a-**xxx**
- Name: webapp5a-**xxx**
- Packaging: war
- Java: 11
- Dependencies: Spring Web, Thymeleaf

There are several additional files present:

- **src/main/resources/static/sitemap.xml** - File contains the reference for website pages.  DAST scanning looks at this file for pages to scan.
- **src/main/resources/application.properties** - Contains Spring Boot configuration information.  The build pipeline will change the port from the local running port 8080 to the deploy port of 5000.


2. Create branch **feature-webapp** and checkout.

3. Modify pom.xml to build your own artifact.  Change the Maven **artifact** and **name** tags to your initials in your Auburn email (replace "XXX").  Maven uses this to build the **war** file name.  For example:
   ```xml
   <name>webapp5a-pwb0016</name>
   <artifactId>webapp5a-pwb0016</artifactId>
   ```
   

4. Run the Maven command "**mvn package**" to verify the web application archive (.war file) is successfully generated.  You should see the following line towards the end of the log messages:

    >[INFO] Building war: /<leading path>/target/webapp5a-XXX-0.0.1-SNAPSHOT.war

   >  You can also look under the **target** directory and the **war** file should be generated
 

5. Maven can run the Spring Boot webserver locally because the **pom.xml** includes a plugin for it (**\<build\>** tag).  This plugin provides addition Maven targets to specify.  Spring Boot runs with an embedded web server. Run the web application with the maven command "mvn spring-boot:run".  You should see the following line towards the end of the log messages:

    >2022-06-21 16:13:53.106  INFO 1341 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

6. Browse to http://localhost:8080.  You should get a error page because we have not coded any responses for http url requests.  

7. **"ctrl-c"** to cancel out of running the web application.

# Adding a Web Page
8. Create an **index.html** file in the **main/resources/templates** directory with the below.  The templates directory is the root directory of the web pages served up by the webserver.
   ```html
   <!DOCTYPE html>
   <html>
   <body>
   <h1>My Spring Boot Web App XXX</h1>
   </body>
   </html>
   ```
- Replace "XXX" with your initials.

9. Rebuild and run your application with **mvn spring-boot:run**.  Browse to **http://localhost:8080/** and validate the page is displayed.
 

# Adding a Web Page and URL Handler
10. Create the following Java class in the **edu.auburn.comp4970.webapp5a** package under **main/java** directory. 

    Spring Boot makes it easy to develop web applications through the use of annotations.  Annotations indicate how web requests should be routed and handled by specified classes and methods.
- ***@Controller*** annotation indicates this class handles URL requests to the web server.
- ***@GetMapping("/login")*** method indicates this method will be call for any web requests to **/login**
   ```java
   package edu.auburn.cpsc4970.webapp5a;
   
   import org.springframework.stereotype.Controller;
   import org.springframework.ui.Model;
   import org.springframework.web.bind.annotation.GetMapping;
   
   @Controller
   public class WebPageController {
   
      @GetMapping("/login")
      public String index(Model model) {
         return "login";
      }
   }
   ```

We are using the [Thymeleaf](https://www.thymeleaf.org/index.html) with [Spring Boot Web MVC](https://spring.io/guides/gs/serving-web-content/) as our web page templating engine.  Thus, the ***return "login";*** specifies the web page to serve back (**login.html**).

11. Add a **login.html** page to the **resources/templates** directory
   ```html
   <!DOCTYPE HTML>
   <html xmlns:th="http://www.thymeleaf.org">
   <head>
       <title>CPSC4970 Login Form</title>
       <meta name="viewport" content="width=device-width, initial-scale=1">
       <style>
           body {font-family: Arial, Helvetica, sans-serif;}
           form {border: 3px solid #f1f1f1;}
   
           input[type=text], input[type=password] {
               width: 100%;
               padding: 12px 20px;
               margin: 8px 0;
               display: inline-block;
               border: 1px solid #ccc;
               box-sizing: border-box;
           }
   
           button {
               background-color: #04AA6D;
               color: white;
               padding: 14px 20px;
               margin: 8px 0;
               border: none;
               cursor: pointer;
               width: 100%;
           }
   
           button:hover {
               opacity: 0.8;
           }
   
           .cancelbtn {
               width: auto;
               padding: 10px 18px;
               background-color: #f44336;
           }
   
           .imgcontainer {
               text-align: center;
               margin: 24px 0 12px 0;
           }
   
           img.avatar {
               width: 40%;
               border-radius: 50%;
           }
   
           .container {
               padding: 16px;
           }
   
           span.psw {
               float: right;
               padding-top: 16px;
           }
   
           /* Change styles for span and cancel button on extra small screens */
           @media screen and (max-width: 300px) {
               span.psw {
                   display: block;
                   float: none;
               }
               .cancelbtn {
                   width: 100%;
               }
           }
       </style>
   </head>
   <body>
   
   <h2>Login Form</h2>
   
   <form action="/login_handler" method="get">
   
       <div class="container">
           <label for="uname"><b>Username</b></label>
           <input type="text" placeholder="Enter Username" name="uname" required>
   
           <label for="psw"><b>Password</b></label>
           <input type="password" placeholder="Enter Password" name="psw" required>
   
           <button type="submit">Login</button>
       </div>
   
       <div class="container" style="background-color:#f1f1f1">
           <button type="button" class="cancelbtn">Cancel</button>
       </div>
   </form>
   
   </body>
   </html>
 ```

12. Add another URL handler method in our WebPageController class to handle the login submit:
       ```java
           @GetMapping("/login_handler")
           public String loginHandler(Model model) {
               return "index";
           }
       ```
- This method simply returns us to the **index.html** page when the login request is sent back. The HTML form tag specifies the URL handler ***<form action="/login_handler" method="get">*** 
# Adding an Error Page
13. Add an error page to handle an errors that occur on the server.  Spring Boot automatically looks for an **error.html** in the **resources/template** directory if one is not configured.  Add the following content to the file:
    ```html
    <!DOCTYPE html>
    <html>
    <body>
    <h1>Uhoh...Something happened</h1>
    <h2>Our Auburn Engineers are taking a look.</h2>
    <a href="/">Go Home</a>
    </body>
    </html>
    ```
14. Rebuild and run your application with **mvn spring-boot:run**.  Browse to the following **http://localhost:8080/** and validate our index.html web page is returned. 


15. Browse to **http://localhost:8080/login** and validate our web page is returned.

16. Enter any username/password and select the login button.  You should be returned to the **index.html**.

17. Add, Commit, Push, and Merge changes to remote repository.

# Build the Web Application via Gitlab Pipeline

18. Create branch "pipeline-config" and checkout. 
19. Build a basic gitlab-ci.yml pipeline to build the web application.
    ```yaml
    #
    # Gitlab pipeline config
    #
    
    stages:
      - build
    
    cache:
      paths:
        - .m2/repository
        - target
    
    variables:
      MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository"
    
    build:
      image: maven:3.8-openjdk-11
      stage: build
      script:
        - mvn package -s ci_settings.xml
      only:
        - main   
    ```
20. Add, Commit, Push, and Merge to remote repository.


21. Validate your pipeline runs successfully and creates the war file package by drilling down into the pipeline stage execution log.
    ```text
    [INFO] --- maven-war-plugin:3.3.2:war (default-war) @ webapp5a-xxx ---
    [INFO] Packaging webapp
    [INFO] Assembling webapp [webapp5a-pwb] in [/builds/cpsc4970-sum-a/aubie/assignment-5a/target/webapp5a-xxx-0.0.1-SNAPSHOT]
    [INFO] Processing war project
    [INFO] Building war: /builds/cpsc4970-sum-a/aubie/assignment-5a/target/webapp5a-xxx-0.0.1-SNAPSHOT.war
    [INFO] 
    [INFO] --- spring-boot-maven-plugin:2.7.0:repackage (repackage) @ webapp5a-xxx ---
    [INFO] Replacing main artifact with repackaged archive
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  15.003 s
    [INFO] Finished at: 2022-06-22T02:43:46Z
    [INFO] ------------------------------------------------------------------------
    ```
# Deploying your web application to AWS Elastic Beanstalk
22. Create branch "aws-ebs-config" and checkout

23. Add pipeline stage **deploy**

24. Create a stage instance of **deploy** to deploy the artifact to the web application. Modify the snipped below and replace **"XXX"** with your initials.
    ```yaml
    aws-elb-deploy:
      image: registry.gitlab.com/gitlab-org/cloud-deploy/aws-base:latest
      stage: deploy
      script:
    # Update war file to AWS S3 for staging deployment into Elastic Beanstalk web application server
      - aws s3 cp target/webapp5a-jmd0128-0.0.1-SNAPSHOT.war s3://m5a-bucket/webapp5a-XXX-0.0.1-SNAPSHOT.war
    # Update Application version in Elastic Beanstalk with file from S3
      - aws elasticbeanstalk create-application-version --region us-east-2 --application-name m5-webapp --version-label "XXX-$CI_COMMIT_SHORT_SHA" --source-bundle S3Bucket="m5a-bucket",S3Key="webapp5a-XXX-0.0.1-SNAPSHOT.war"
    # Reload/deploy Elastic Beanstalk environment with the new version 
      - aws elasticbeanstalk update-environment --region us-east-2 --environment-name XXX-env --version-label "XXX-$CI_COMMIT_SHORT_SHA"
      dependencies:
        - build
      only:
        - main
      ``` 
25. REMEMBER to replace all occurences of **XXX** with your Auburn email (e.g. pwb0016).  AWS Elastic Beanstalk and filenames are important to match the commands above.
26. We also need to change the HTTP port from our local 8080 to Elastic Beanstalk 5000.  Pipelines can be used to make configuration changes for deployment environments.  Add the following to the **variables** section you pipeline file:
    ```yaml 
    EBS_PORT: 5000
    ```  

    - Add the following lines to the beginning of the **build** job script:
    ```yaml
        # Replace local port 8080 to AWS Elastic Beanstalk 5000
        - sed -i "s|8080|$EBS_PORT|g" src/main/resources/application.properties
        - cat src/main/resources/application.properties
    ```
- [sed](https://www.gnu.org/software/sed/manual/sed.html) is a command line tool for modifying text.  **cat** is a check to print out the file contents to the build log to make sure the changes occur.

27. Add, Commit, Push, and Merge to remote repository.


28. Validate your pipeline runs successfully.  If the pipeline fails, inspect the pipeline stage log for error message, check all values in **.gitlab-ci.yml**.


29. Browse to the following url and your web application index.html page should be returned!
- https://XXX.au-csse-cpsc4970.com/
Note: replace ***XXX*** with your initials.


30. Congrats! You have successfully deployed your webapp on AWS!

# Adding in DAST Scanning

Gitlab uses ZAP as it's embedded scanning tool.  We will be looking more indepth at ZAP DAST tool in the next module.  For this week, we will just configure it to execute within our pipeline.

31. Create branch 'pipeline-dast' and checkout.


32. Add a **dast** stage to your pipeline


33. We need to specify the URL to scan.  This is an environment variable added to the **variables** section of the pipeline file.  Replace the ***XXX*** with your initials.
    ```yaml
      DAST_WEBSITE: "http://XXX.au-csse-cpsc4970.com/"
    ```
34. To add DAST scanning by ZAP we just need to add the following line in the pipeline config file:
    ```yaml
    include:
      - template: DAST.gitlab-ci.yml
    ```
35. Add, Commit, Push, and Merge to remote repository.


36. Validate your pipeline runs successfully.  If the pipeline fails, inspect the stage failure and check the DAST_WEBSITE setting.


37. In your git lab 5a project you can review the **Security and Compliance-->Vulnerability Report** to see the results of the DAST scanning.  You should have a PII disclosure and Application error disclosure 

# Resolve Vulnerabilities

38. Create branch **resolve-vulnerabilities**

37. Remove the PII information from specific html page.

38. Remove the application error content from the html page

39. Commit, push, merge and check the errors are resolved on rescan (right side activity) column

40. Set the status of these issues to "Resolve" by selecting and setting status.

