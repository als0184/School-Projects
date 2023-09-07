# M2 Assignment B

In this assignment you are creating a project that builds a Java Password Authentication package and making changes to eliminate code vulnerabilities.

Assignment objectives:
- Build Java package locally once you have cloned the repository.
- Configure a Gitlab pipeline to automatically build the application.
- Inspect the source code and correct the following security issues:
   - Weak Password policy
   - Information leakage
   - Lack of audit and logging
   - Exception handling
   
Grading will be based on commit history and pipeline execution history reflecting completing the steps below.

# About the code base

This Java code base builds a package (.jar) that contains a method for authenticating users.  It could be used within a web application.  However, it contains several security flaws that need to be mitigated.  Using the common security areas described from the lectures you are asked to fix these issues using the source code inspection and control practices you have been learning.

The code base uses a Java [Factory Pattern](https://www.tutorialspoint.com/design_pattern/factory_pattern.htm) to provide a concrete class (**UsernamePasswordAuthenticator**) that implements a simple login **AuthLoginInterface**.  The database of users is stored in a simple  **users.json** file.  Logging is provided by SLF4J logging framework with configuration in the **simplelogger.properties** file.

It is necessary to understand how Java Exceptions work and are handled.  For a refresher see [this article](https://www.digitalocean.com/community/tutorials/exception-handling-in-java) or search for other explanations. 

The structure of the source files follows the [Maven standard directory convention](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html). When performing tasks Maven searches in these directories to look for classes to compile, tests to execute, files to package, etc.  Within the Maven [Project Object Model](https://maven.apache.org/pom.html) file (pom.xml) 




# Create Gitlab build pipeline for Assignment B and correct failed tests

The first part of the assignment is to configure Maven to working in a pipeline and become familiar with correcting code and JUnit tests.

1. If needed add any of your IDE files/directories to **.gitignore**

2. Run a maven clean target. Verify the output:
   ```commandline
   [INFO] Scanning for projects...
   [INFO]
   [INFO] ------------------< edu.auburn.cpsc4970:AuthProvider >------------------
   [INFO] Building AuthProvider 1.0-SNAPSHOT
   [INFO] --------------------------------[ jar ]---------------------------------
   [INFO]
   [INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ AuthProvider ---
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time:  0.133 s
   [INFO] Finished at: 2023-07-06T16:25:30-04:00
   [INFO] ------------------------------------------------------------------------
   ```
3. Run a maven **compile** target.  Verify the output:
    ```commandline
    [INFO] Scanning for projects...
    [INFO] 
    [INFO] ------------------< edu.auburn.cpsc4970:AuthProvider >------------------
    [INFO] Building AuthProvider 1.0-SNAPSHOT
    [INFO] --------------------------------[ jar ]---------------------------------
    [INFO] 
    [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ AuthProvider ---
    [INFO] Using 'UTF-8' encoding to copy filtered resources.
    [INFO] Copying 3 resources
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ AuthProvider ---
    [INFO] Nothing to compile - all classes are up to date
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  0.247 s
    [INFO] Finished at: 2023-07-06T16:30:10-04:00
    [INFO] ------------------------------------------------------------------------
    ```
4. Inspect the **target** directory.  This is the directory Maven creates to store all the artifacts during executing it's build tasks.  To see all the files create use the find command (linux/mac):
   ```commandline
   find target -print  
   ````
   windows:
    ```commandline
    dir /s target
    ```
5. Run Maven **clean** target and notice the target directory is removed.  This is a common practice if the build is having issues.

6. Create branch **pipeline**, setup tracking branch, and checkout.

7. Create a **.gitlab-ci.yml** to build the java application

8. Create a **test** stage to the [stages](https://docs.gitlab.com/ee/ci/yaml/#stages) section
    ```yaml
    stages:         
      - test
    ```

9. Add **login-test** job specifically to run the Maven **test** target.  Things to note:
   - Specifying a new Docker [image](https://docs.gitlab.com/ee/ci/yaml/#image) within the stage definition for Maven  
   - In the [script](https://docs.gitlab.com/ee/ci/yaml/#script) we are running the same maven **"mvn test"** command you run locally.
   - New [only](https://docs.gitlab.com/ee/ci/yaml/#only--except) rule  specifies the job is triggered only on main branch
    ```yaml
      login-test:
      image: maven:3.8-openjdk-11
      stage: test
      only:
        - main
      script:
        - mvn test
    ```

10. Git Add/Commit/Push/Merge

11. Verify the Gitlab pipeline was triggered on merge to main and caused errors.
    ```
    Results :

    Failed tests:   testUsernameNullValue(edu.auburn.cpsc4970.UsernamePasswordAuthenticatorTest): (..)
      testPasswordNullValue(edu.auburn.cpsc4970.UsernamePasswordAuthenticatorTest): (..)


    Tests run: 6, Failures: 2, Errors: 0, Skipped: 0
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD FAILURE
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  4.526 s
    [INFO] Finished at: 2022-06-01T01:16:39Z
    [INFO] ------------------------------------------------------------------------
    ```
12. Correct the errors failing in the pipeline on a local **bug-fix-test-errors** branch.  You will need examine the test class to see the validation.  To correct the errors you will need to:
- Fix the infamous Java Null Pointer Exception (NPE)
- Throw the appropriate message in the **AULoginException** class

13. Push the **bug-fix-test-errors** branch to the remote repo.  The pipeline should not be triggered.

14. Create a merge request. Your are now doing a code review and should check the commit diff to verify the code changes with **main** branch. Add a comment before approving the merge to **main**.

15. Check that the pipeline executes and passed.

# Add build stage to your pipeline

16. Create branch **pipeline-add-build** and use for the next code changes.

17. Add a "**build**" stage after the test state.  This controls the order of execution

18. Add a job definition to run the Maven **package** target.  This creates a jar file after the compilation and testing steps are complete.
    ```yaml
    login-build:
      image: maven:3.8-openjdk-11
      stage: build
      only:
        - main
      script:
        - mvn package
    ``` 

19. After the **stages** section add the ability to cache maven dependency's between job/pipeline execution.  This enables Maven to only download dependencies on the first pipeline execution by enabling a local [cache](https://docs.gitlab.com/ee/ci/yaml/#cache) to be kept in your project. "[variables](https://docs.gitlab.com/ee/ci/yaml/#variables)" are values based to all jobs.  
- MAVEN_OPTS tells maven where the local dependencies are located.
- $CI_PROJECT_DIR is an environment variable specify the directory location
- **cache** tells Gitlab to save this directory between pipelines, which will cause maven not to download dependencies each speeding up subsequent runs of the pipeline.
    ```yaml
    variables:
      MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository"
    
    # Cache downloaded dependencies and plugins between builds.
    # To keep cache across branches add 'key: "$CI_JOB_NAME"'
    cache:
      paths:
        - .m2/repository
    ```
    
20. Add/Commit/Push the branch and verify pipeline executed both stages successfully.  You should see the following output:

  ```yaml
      [INFO] Building jar: /builds/cpsc4970-sum-a/aubie/assignment-2b/target/AuthProvider-1.0-SNAPSHOT-jar-with-dependencies.jar
      [INFO] ------------------------------------------------------------------------
      [INFO] BUILD SUCCESS
      [INFO] ------------------------------------------------------------------------
      [INFO] Total time:  7.895 s
      [INFO] Finished at: 2022-06-01T02:08:21Z
      [INFO] ------------------------------------------------------------------------
  ```

# Source code inspection and correction for security issues.

For this part of the assignment you should inspect the output from running the unit tests.  The unit tests will cause log messages to appear similar to when running in production.

21. Create the following 4 branches:
- **bug-info-leak** 
- **bug-exception-leak** 
- **feature-password-complexity**  - any additional complexity of your choosing.  
- **feature-log-login-success**

It is important to understand when and where you create your branches.  As you make changes on a branch and push it to the remote repository the other branches will not have those changes.  After you merge **main** in Gitlab you should pull the changes into your local **main** branch.  You may have to do a merge if there are conflicts. Alternatively, you can do a **git rebase <branch>** to move your current branch current commit pointer (**HEAD**) to the latest commit on the specified branch.    

22. On each specific branch fix the security vulnerability it indicates based on the lecture for common vulnerabilities and the description of the assignment.

23. After code changes run maven locally to make sure all tests still pass.

24. You can run the java application locally to see the security issues by examining the output.  The **LoginApp" has a **"public static void main( String[] args )"** so it can be run with Maven. With Maven you can use the follow command:
    ```yaml
    mvn exec:java
    ```
    This Maven target knows which class by this **pom.xml** entry:
    ```xml
    <exec.mainClass>edu.auburn.cpsc4970.auth.LoginApp</exec.mainClass>
    ```
- Make sure your run **mvn package** so there is jar file in the **target** directory for the exec:java target to run against.
    
25. Push each branch to the remote repository.  Create a merge request and process it **with a comment** on a description of why the code was change and the correction you made.

26. Check to make sure the pipeline completes successfully

