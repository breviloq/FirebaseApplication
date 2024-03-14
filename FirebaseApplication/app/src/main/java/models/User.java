package models;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String passwd;
    private String firstName;
    private String key;

    public User(){}

    public User(String username, String passwd, String firstName) {
        this.username = username;
        this.passwd = passwd;
        this.firstName = firstName;
    }

    public User(String username, String passwd, String firstName, String key) {
        this.username = username;
        this.passwd = passwd;
        this.firstName = firstName;
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
