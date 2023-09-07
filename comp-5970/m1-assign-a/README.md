# Assignment-1a

This assignment has you performing basic git commands for adding, committing, branching, and working with remote repositories.  The grading will be based on the commit history and branches that are pushed to your Gitlab assignment 1a repository.

Do the follow:

# Basic Git Commands

1. Install git
   - Configure your username and email
   - Setup alias in .gitconfig for pretty print
    ```
    [alias]
       lg1 = log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold green)(%ar)%C(reset) %C(white)%s%C(reset) %C(dim white)- %an%C(reset)%C(bold yellow)%d%C(reset)' --all
       lg2 = log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold cyan)%aD%C(reset) %C(bold green)(%ar)%C(reset)%C(bold yellow)%d%C(reset)%n''          %C(white)%s%C(reset) %C(dim white)- %an%C(reset)' --all
       lg = !"git lg1"
    ```

2. Setup Gitlab Account
  - Generate ssh key on local system.  [How to](https://docs.gitlab.com/ee/user/ssh.html)
  - Add ssh key to account
3. Initialize a new git local repository by cloning assignment 1a repository to local git environment. You can copy the url from git lab "Clone" button on the repository page. 

    ```
    git clone https://gitlab.com/cpsc4970-sum-a/<your project>/CPSC4970-Assignment-1a.git
    ```

  - If you get an authentication error you may have to put your Gitlab username (\<gitlab username\>)in front of the http url domain.

    ```
    git clone https://<gitlab username>@gitlab.com/cpsc4970-sum-a/<your project>/CPSC4970-Assignment-1a.git
    ```
    
  - Your username can be found in the profile dropdown on the upper right corner navigation.

4. After each **git commit** you should review the git log to understand how your local and remote (**origin**) differs since each will have changes the other does not.  It is important to understand when local/remote branches are out of sync and how to use **push**, **pull**, and **fetch** to send and receive changes.  This is a key concept which makes git a strong tool for distributed development.

5. Add and commit a .gitignore file.  This file tells git what files to ignore while it keeps track of added/modified/deleted files.  Push changes to main
    ```
    git push
    ```

6. Create two branches name **feature-1** and **bug-1**

7. Add and commit a text file to each branch containing a quote from one of your favorite movies.  File name can be the name of the movie.

8. Examine the git log after each commit.  Watch how the tree changes after every commit and how the **HEAD** changes.  The **origin** (remote repo) is not behind your local branch.
    ```
    git lg
    git lg2
    ```
9. Switch to **main** branch and merge the branches
    ```
    git merge feature-1
    git merge bug-1
    ```
10. Push the changes to the remote Gitlab repository (origin).
    ```
    git push origin main
    ```
11. Perform a **git fetch origin** to only pull down the commit information from the remote repository and not the file changes. Examine the git log to notice how the **origin/main** has changed in relation to the **HEAD**.

12. The **origin** is now ahead of the local **main** branch.  We must pull the changes down to have them in sync before further changes.  Pull merged changes into your local repository since it has changed.
    - Review git log
    - Change to **main**
    - Pull remote changes down
```
git pull origin
```
- Review the git log to see **HEAD -> main** and **origin/main** are now pointing to the same commit.

# Working with Remotes

13. Review remote repositories your local git is referencing:

    '''
    git remote -v
    '''
14. Create **feature-2** and **bug-2** branches.   

15. For branches you need first specify the remote tracking branches. Do the following for both branches
    ```
    git push -u origin <branch name>
             or
    git push --set-upstream origin <branch name>
    ```
16. Review on Gitlab that all branches are now present.

17. For **feature-2** add a new movie file and quote inside.  For **bug-2** add a second quote or comment. 

18. Push changes from each branch to remote repository
    ```
    git push origin <branch>
         or
    git push origin   # for current branch
    ```
19. Create a merge request (pull request) in Gitlab to merge branch into the main branch.
    - Assign yourself as the processor
20. Process the pull request
    - Review the changes by selecting the "Changes" tab at the top of the screen
    - Add a comment about the change
    - Merge the change in to main by selecting "Merge" button.
21. Pull merged changes into your local repository since it has changed.
    - Review git log
    - Switch to **main**
    - Pull remote changes down
    ```
    git pull origin
    ```
22. Create a branch **my-feature**.  Add a few text files, modifying files after initial commits, thus having multiple commits. Push the branch to the remote repository.  Create a merge request (pull request) and process it to have changes merged into **main** branch

Grading will be based on commit history reflecting completing the steps above and completed quiz.
