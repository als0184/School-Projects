# M1 Assignment B

This assignment has you setting up and using Apache Maven to build a basic Java Hello World application.  The Java application was created by using the Maven archetype [maven-archetype-quickstart](https://maven.apache.org/archetypes/maven-archetype-quickstart/), which provides a ready to build Java application with a single Java class and associated test.  This is the simplest type of Maven configuration.
```
mvn archetype:generate -DgroupId=edu.auburn.cpsc4970 -DartifactId=assign1b -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

Your are welcome to start using an IDE as you edit source code files.  But it is encouraged to still use git and maven at the command line to re-enforce the concepts.  IDE's essentially run these commands for you.

Do the follow:
# Basic Maven Commands

1. Clone the following repository for the assignment. Use the http link by copying from Gitlab under the "Clone" button
    ```
    git clone https://gitlab.com/cpsc4970-sum-a/<your project>/CPSC4970-Assignment-1b.git
    ```
   You may need to put your Gitlab username as in cloning in assignment A

2. Examine the .gitignore file
   - It contains files that should be ignored by git and should be edited for any IDE configuration files or directories.  The existing **.idea** directory is for Intellij IDE.
   - The **target** directory is where Maven outputs all of it's working files and build artifacts. 
   - These files/directories should be added to the .gitignore so they are **ignored** and not tracked by git. 


3. Make sure you are running Java JDK 11
    ```
    java -version
    ```
   ```
   java version "11.0.8" 2020-07-14 LTS
   Java(TM) SE Runtime Environment 18.9 (build 11.0.8+10-LTS)
   Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.8+10-LTS, mixed mode)
   ```
4. Check Maven version
   ```
   mvn -version
   ```   
   ```
   Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3 2018-10-24T20:41:47+02:00)
   Maven home: /usr/local/Cellar/maven/3.6.0/libexec
   Java version: 11.0.8, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-11.0.8.jdk/Contents/Home
   ```
5. Now let's compile the Java application created by the Maven archtype.  Run Maven target **compile**:
    ```
    mvn compile
    ```
   **target** directory should have been created on successful build.  Examine it's contents.  The compiled classes can be found under the **classes** directory


7. Running the JUnit test classes is done by the  Maven **test** target:
    ```
    mvn test
    ```
   Examine **target** directory and examine it's contents.  You will notice additional directories related to running automated tests.

8. The Java application can also be executed through Maven.  Run the Java main class with the **exec:java** target:
   ```
   mvn exec:java
   ```

9. To remove all the compiled classes and other generated files run Maven **clean** target:
    ```
    mvn clean
    ```
   The **target** directory is now removed and all working files are deleted.  This command is helpful to run to make sure you are starting with no files present in the target directoy and may be causing issues.

7. Package your Java application into a jar file by running the Maven **package** target.
   ```
   mvn package
   ```
   The **target** directory will now contain the following file:
   ```
   assign1b-1.0-SNAPSHOT.jar
   ```
   Run the following to examine the contents:
   ```
   tar -tf target/assign1b-1.0-SNAPSHOT.jar
   ```
8. Install the package in your local Maven repository with the **install** target
   ```
   mvn install
   ```
   Examine the **~/.m2/repository/** directory and look for the directoy and files related to the **assign1-1b.0-SNAPSHOT.jar** package. Hint: drill down on the package structure.

# Modifying the Maven Build

9. Create and checkout a branch called "feature-add"


10. Change the artifact id (assign1-1b) in the pom.xml. Run mvn package and notice artifact name change in target directory.


11. Add a method to the App class that adds two integers


12. Add an addition junit test to the test class that tests the method.


13. Build and validate your code functions and passes the unit tests.


14. Push the branch to the remote repository.


15. Create and process a merge request to **main** branch on Gitlab


Grade will be based on review of commit history and branch on Gitlab
