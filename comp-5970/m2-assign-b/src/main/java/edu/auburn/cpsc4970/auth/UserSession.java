package edu.auburn.cpsc4970.auth;

public class UserSession {

    private String userName;
    private String password;

    UserSession (String userName, String password) {

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
