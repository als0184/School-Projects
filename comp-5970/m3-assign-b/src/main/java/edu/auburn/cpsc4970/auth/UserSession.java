package edu.auburn.cpsc4970.auth;

public class UserSession {

    private String userName;
    private String password;

    UserSession (String userName, String password) {

        System.out.println("Created new user session for %s, %s");

        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
