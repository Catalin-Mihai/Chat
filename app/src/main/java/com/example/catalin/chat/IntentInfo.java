package com.example.catalin.chat;

import java.io.Serializable;

public class IntentInfo implements Serializable {

    private User user;
    private Contact contact;

    IntentInfo(User user, Contact contact)
    {
        this.user = user;
        this.contact = contact;
    }


    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
