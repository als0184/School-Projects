# M4 Assignment A - Dependency Vulnerability Scanning

In this assignment you are configuring dependencies for the AuthProvider package, incorporating vulnerabilitiy scanning, and resolving vulnerabilities found as part of the scanning.  

### Assignment objectives:
- Modify maven project object model (pom.xml) dependencies
- Configure build pipeline to perform dependency scanning and vulnerabiltiy checking
- Resolve dependency issues and vulnerability issues.


Grading will be based on commit history, pipeline execution, and dependency report reflecting completing the steps below.

# Adding missing dependencies

1. Clone the repository to your local development environment.

2. Review the pom.xml file for the <dependencies> and <plugin> xml sections

3. Run maven compile and make sure it successfully compiles.

3. Run the Junit tests by running maven test target.

4. The classic "**java.lang.NoClassDefFoundError**" error.  If you examine this error you will notice it can not find our database driver class.  The JVM has searched the class path (-cp command line option) at run time and did not find the class org.postgres.Driver. We need to add this dependency in our pom.xml.

4. Create a branch “**add-missing-dependency**” and checkout branch

5. Add the org.postgressql.Driver jdbc driver dependency in the pom.xml by searching ("PostgreSQL JDBC Driver") on [Maven Central](https://mvnrepository.com/) for **version 42.3.1**. There is a maven xml snippet on the page.

You will notice Maven downloading the dependencies from Maven Central and caching them in your local repository (~/m2):
```commandline
[INFO] ------------------< edu.auburn.cpsc4970:AuthProvider >------------------
[INFO] Building AuthProvider 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
Downloading from central: https://repo.maven.apache.org/maven2/org/postgresql/postgresql/42.3.1/postgresql-42.3.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/postgresql/postgresql/42.3.1/postgresql-42.3.1.pom (2.7 kB at 5.6 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/postgresql/postgresql/42.3.1/postgresql-42.3.1.jar
Downloaded from central: https://repo.maven.apache.org/maven2/org/postgresql/postgresql/42.3.1/postgresql-42.3.1.jar (1.0 MB at 1.3 MB/s)
```

6. Run the maven test again to see if the test passes.

7. Add, Commit, Push, and Merge the changes into the **main** branch. Pull changes to local repo.

# Change Logging Framework

We have decided to switch to [Log4J]() open source library for logging. 

8. Create a branch “**feature-log4j**” and checkout branch.

9. Make the following dependency changes to your project object model (pom.xml).  Add the following log4j dependencies (**version 2.14.1**) by looking them up on  [Maven Central](https://mvnrepository.com/).  

     - [**log4j-api**](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api/2.14.1)
     - [**log4j-core**](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core/2.14.1)
     - [**slf4j-reload4j**](https://mvnrepository.com/artifact/org.slf4j/slf4j-reload4j/2.0.7)
 

Note: Since we use [slf4j](https://logging.apache.org/log4j/2.x/) switching out logging frameworks is seamless without any configuration.  At runtime slf4j framework will search the class path for a logging framework to use.

9. Remove the slf4j-simple dependency from the **pom.xml** since we have replaced it with log4j.

10. Run mvn test to see the new logging statements. You can change the format of the statements in the **log4j.properties** file.  Your should see the new format:
```commandline
 2023-02-05 12:03:31 ERROR AULoginException - Login Error: "Username can not be null"
 2023-02-05 12:03:31 INFO UsernamePasswordAuthenticator - Checking "Jordan" with password "null"
```

You will notice Maven downloading the dependencies from Maven Central and caching them in your local repository:
```commandline
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.pom (14 kB at 37 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j/2.14.1/log4j-2.14.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j/2.14.1/log4j-2.14.1.pom (68 kB at 641 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-slf4j-impl/2.14.1/log4j-slf4j-impl-2.14.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-slf4j-impl/2.14.1/log4j-slf4j-impl-2.14.1.pom (12 kB at 260 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-core/2.14.1/log4j-core-2.14.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-core/2.14.1/log4j-core-2.14.1.pom (23 kB at 332 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.jar
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-core/2.14.1/log4j-core-2.14.1.jar
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-slf4j-impl/2.14.1/log4j-slf4j-impl-2.14.1.jar
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-slf4j-impl/2.14.1/log4j-slf4j-impl-2.14.1.jar (24 kB at 189 kB/s)
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-core/2.14.1/log4j-core-2.14.1.jar (1.7 MB at 3.8 MB/s)
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4
```


11. A **log4j.properties** files needs to be added for configuring log4j.  Add the following file in the **resources** directory of the project:
```properties
# Extra logging related to initialization of Log4j
# Name of the configuration
name = ConsoleLogConfig

log4j.rootLogger=DEBUG, stdout

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %p %c{1} - %m%n
``` 

14. Run the following command to see a report of your dependencies.  This is accomplished with the maven dependency plugin in the **<plugins>** section
```yaml
mvn dependency:tree
```

Make sure the slf4j-simple is not being reported.

15. Add, Commit, Push, and Merge the pom.xml and log4j properties files to remote repo and merge into **main**.

# Add Dependency Vulnerability Scanning and Vulnerability Resolution

16. Switch back to **main** branch and pull changes from the repository

17. Create a branch **build-pipeline** and checkout

18. Create a gitlab-ci.yml file to compile and test 

19. Add stage **depend-scan** to the **stages** section after **test** in your **gitlab-ci.yml** file

20. Add the following line to pipeline file to add vulnerability scanning
    ```yaml
    include: '/templates/Dependency-Scanning.gitlab-ci.yml'
    ```

20. Add, Commit, Push, and Merge changes into **main**

21. Make sure pipeline executes successfully

22. Your Gitlab project should have **Secure** section on the lefthand navigation.  Select the **Dependency List** from the menu to view the Dependency report

23. Review the **Vulnerability Report** and read through the explanations and related CVEs.  

24. Update **main** and create a branch **depend-updates** and checkout

25. For each dependency with vulnerabilities update your pom.xml with a more current version.  You can query [Maven Central](https://mvnrepository.com/) for a list of versions.

26. Add, Commit, Push, and Merge changes into **main**

27. Make sure pipeline executes successfully and dependency report shows no vulnerabilities present.

# Publish AuthProvider to Gitlab Package Registry

After the AuthProvider successful build it needs to be published by [Maven Deploy](https://maven.apache.org/plugins/maven-deploy-plugin/index.html) to your own repository (Gitlab **Package Registry**) on Gitlab.  This allows it to be consumed by other applications by configuring it as a dependency

28. Create a branch **pipeline-publish** and checkout

29. Add the following to the **pom.xml** within the **\<project\>** tag to add pointers to the Gitlab repository where the AuthProvider package will be published.

The **${CI_PROJECT_ID}** is an environment variable that gets set when the pipeline is executed. 

```yaml

    <repositories>
        <repository>
            <id>gitlab-maven</id>
            <url>https://gitlab.com/api/v4/projects/${CI_PROJECT_ID}/packages/maven</url>
        </repository>
    </repositories>

    <distributionManagement>
        <repository>
            <id>gitlab-maven</id>
            <url>https://gitlab.com/api/v4/projects/${CI_PROJECT_ID}/packages/maven</url>
        </repository>
        <snapshotRepository>
            <id>gitlab-maven</id>
            <url>https://gitlab.com/api/v4/projects/${CI_PROJECT_ID}/packages/maven</url>
        </snapshotRepository>
    </distributionManagement>
```
30. Add the stage "publish" to gitlab-ci.yml

31. Add an instance of "publish" with the following config.

The new option on mvn "-s ci_settings.xml" includes an environment [job token](https://docs.gitlab.com/ee/ci/jobs/ci_job_token.html) that allows the pipeline to publish to the **Package Registry**.  Maven will use it's **deploy** target to upload our AuthProvider library.
    ```yaml
    deploy_to_gitlab_package_registry:
      image: maven:3.6-jdk-11
      stage: publish
      script:
        - 'mvn deploy -s ci_settings.xml'
      only:
        - main
    ```

32. Add, Commit, Push, and Merge changes into **main**

33. Your Gitlab project has a **Deploy** section on the lefthand navigation.  Select the **Package Registry** from the menu to check your AuthProvider artifact is present

34. Click on the artifact to view information about it.

# Publish New AuthProvider Version to Gitlab Package Registry

In order for new versions of our library to be published we need to update the version number.  Maven uses the **\<version\>** xml tag to determine the version being published.  When code changes are made and released a new version is typically given.

35. Update main and create a branch **update-version** and checkout

36. Change the **\<version\>** in your **pom.xml** to 1.1.  

37. Add, Commit, Push, and Merge changes into **main**

38. Select the **Package Registry** from the menu to check your new AuthProvider artifact is present.  
