# M2 Assignment A

In this assignment you are creating a basic Gitlab build pipeline. The objective is to give you an understanding of the configuration and execution of a multi-stage continuous integration (CI) pipeline.  Software build ipelines are at the core of most software development projects.  They not only ensure consistent, repeatable software build process by eliminating manual tasks, but also ensure quality and security checks are performed when any change is made to the software. In future assignments we will be incorporating automated security checks into our build pipeline.

Similar to Maven, Gitlab follows the **[convention](https://facilethings.com/blog/en/convention-over-configuration)** method of configuring pipelines.  If it sees a **gitlab-ci.yml** in the root directory of a repository then it understands how to trigger and execute pipelines.  

For most of the remaining assignments you will be doing your development on branches, pushing them to the remote assignment repository, and merging them into main.  The purpose is to familiarize yourself with the best practice of reviewing all changes for security issues before merging into a main/master branch.

Grading will be based on commit history and pipeline execution history reflecting completing the steps below.

# Create A Basic Gitlab Pipeline

1. Examine the repository on Gitlab.  You should only see a **README.md** file present.

2. Clone the repository to your local development environment.  The repository url can be copied from the **Clone** button on your project repository page.
   ```
   git clone https://gitlab.com/au-csse-applied-cyber-security/2023-spring-a/<your project id>/Assignment-2a.git
   ```
3. Create branch **new-pipeline** and checkout. Configure a remote tracking branch as in the previous assignment. Alternatively you can create a branch on Gitlab and pull it down.
   Also link your branch to the remote repo:
```commandline
git push --set-upstream origin new-pipline
```
4. Create a .gitignore file for any of your local files (IDE) not part of the repository. 

5. Create a **.gitlab-ci.yml** file. 

6. Pipelines run in stages linked buy a workflow.  Add a single pipeline stage:
   ```yaml
   stages:
      - build
   ```
7. Specify the default Docker [image](https://docs.gitlab.com/ee/ci/yaml/#image) to use.  We are using the basic [Alpine](https://hub.docker.com/_/alpine) linux Docker image.  This image will be used to run our Java Maven build targets the same as you run them locally.  You can also specify an image on each individual stage.
   ```yaml
   image: alpine
   ```
8. For this basic pipeline you are only printing messages using the [script](https://docs.gitlab.com/ee/ci/yaml/#script).  Each step in the pipeline is called a **job** and assigned to a **stage**.  Jobs with the same stage name are run in parallel.   
   ```yaml
   <name of your job>:
      stage: build
      script:
         - echo: "Running <name of your stage>"
         - exit 0  # Return Success
   ```
9. Completed file contents should look similar to this (No cut and paste :)).  Make sure you adhere to [YAML syntax](https://docs.ansible.com/ansible/latest/reference_appendices/YAMLSyntax.html)
    ```yaml
    stages:
      - build
    
    image: alpine
    
    myBuildStage:
      stage: build
      script:
        - echo "Running myBuildStage"
        - exit 0  # Return Success
    ```
10. Using the git commands learned in the previous module. Add, Commit, Push, and Merge the **new-pipeline** to the remote repo.
    ```
    git add .gitlab-ci.yml
    git commit -m "<commit message>"
    git push origin main
    ```
11. Pipelines are automatically executed on commits to the **main** branch. Check pipeline successfully executed in your Gitlab project by clicking on the "CI/CD" in the left panel.  Drill down into the specifc job by cliking on the status icon.  You can see the individual job execution by clicking on it in the pipeline workflow.  Check your message successfully printed out.  
     ```
     https://gitlab.com/au-csse-applied-cyber-security/2023-spring-a/<your project name>/cpsc4970-assignment-2b
     ```
12. Create branch **error-pipeline** and checkout. Configure a remote tracking branch.

13. Have the script return an error code to indicate it failed by changing the exit status code to a negative number.
    ```yaml
    <name of your stage>:
      stage: build
      script:
        - echo "Running <name of your stage>"
        - exit -23  # Return Error - non zero value
    ```
14. Add, Commit, Push, and Merge

15. Check that your pipeline failed in your Gitlab project. Drill down into pipeline stage to see the error code you specified printed out.

# Creating Multi Stage Pipelines

16. Create new branch called **pipeline-update-1** and work on it for the following changes.  Link your branch to the remote repo.

17. Pipeline [workflows](https://docs.gitlab.com/ee/ci/yaml/workflow.html) and [jobs](https://docs.gitlab.com/ee/ci/jobs/job_control.html) can have rules to control when they are executed. You can review the links to see how complex you can control pipeline executions. We are only going to run pipelines when changes are merged into main.  Add the following to you **.gitlab-ci.yml** file to add this control:
```yaml
workflow:
  rules:
    - if: $CI_COMMIT_BRANCH == "main"
      when: always
```
18. Add a **test** stage to the **stages** config of your pipeline to run after your **build** stage using the script to echo a message that the stage is running.
       ```yaml
       stages:
          - build
          - test
       ```
    ```yaml
    <name of your job>:
      stage: test
      script:
        - echo "Running <name of your stage>"
        - exit -1  # Return Error - non zero value
    ```
19. Commit the changes and push the branch to the remote repo.

20. Create merge request and merge your branch into **main**.

21. Check the pipeline graph has successfully executed with your message being echo'd in the stage execution.

22. Create a new branch **pipeline-update-2**, checkout for the next set of changes, and link to remote repo

23. Add another job and assign it the **test** stage in your **.gitlab-ci.yml**.  This is done by adding another section to the file:
    ```yaml
        <new job name>:
          stage: test
          script:
            - echo "Running <new job name>"
            - exit 0  # Return Success 
    ```
24. Commit the changes and push the branch to the remote repo.

25. Create merge request and merge your branch into **main**.

26. Check the pipeline graph and that it has successfully executed with your message being echo'd in the stage execution.  Also check that now two stages are shown in parallel in the **pipelines** section.

27. Create a new branch **pipeline-update-3**, checkout for the next set of changes, and link to remote repo

28. Add another job and assign it the **deploy** stage in your **.gitlab-ci.yml**.  Add the stage entry and associate stage section similar to **test** above. Remember job names are up to you.

29. Commit the changes and push the branch to the remote repo.

30. Create merge request and merge your branch into **main**.

31. Check the pipeline graph and that it has successfully executed with your message being echo'd in the stage execution.

# Adding Custom Stages
32. Create a new branch **pipeline-update-4**, checkout for the next set of changes, and link to remote repo

33. Add a "**\<your project name\>Stage**" stage in your **.gitlab-ci.yml**.  
    ```yaml
    stages:
       - build
       - test
       - <your project name>Stage
       - deploy
    ```
34. Add a job and associate with your new stage.  It should print out a message and successfully execute similar to previous examples in this assignment.

35. Commit the changes and push the branch to the remote repo.

36. Create merge request and merge your branch into **main**.

37. Check the pipeline graph and that it has successfully executed with your message being echo'd in the stage execution.

38. Add another job and assign it to **\<your project name\>Stage** in your **.gitlab-ci.yml**.

39. Commit, push, and merge to **main**.

40. Check the pipeline graph and that it has successfully executed with your message being echo'd in the stage execution and the pipeline view shows the new stage executing in parallel.
