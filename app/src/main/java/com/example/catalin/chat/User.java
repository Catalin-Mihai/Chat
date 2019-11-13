package com.example.catalin.chat;

import java.io.Serializable;

public class User implements Serializable {

    private int ID;
    private String userName;
    private String email;
    private String password;

    User(int ID, String userName, String email, String password)
    {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    User(int ID)
    {
        this();
        this.ID = ID;
    }

    User()
    {
        this.ID = -1;
        this.userName = null;
        this.email = null;
        this.password = null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
