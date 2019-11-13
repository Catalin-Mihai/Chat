package com.example.catalin.chat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {

    private String name;
    private int group_ID;
    private ArrayList<User> usersList;
    private int ID;
    public static final String API_filename = "getcontacts.php";

    public Contact()
    {
        group_ID = -1;
        usersList = null;
        name = "None";
        ID = 0;
    }

    public static Contact GetFromJson(JSONObject jsonObject) throws JSONException {
        Contact contact = new Contact();
        contact.setID(jsonObject.getInt("ID"));
        contact.setGroup_ID(jsonObject.getInt("GrupID"));
        contact.setName(jsonObject.getString("GrupName"));
        return contact;
    }

    public Contact(int group_ID)
    {
        this.group_ID = group_ID;
        usersList = null;
        name = "None";
        ID = 0;
    }

    public Contact(int group_ID, String name)
    {
        this.group_ID = group_ID;
        this.usersList = null;
        this.name = "None";
        this.ID = 0;
    }

    public Contact(int group_ID, ArrayList<User> usersList)
    {
        this.group_ID = group_ID;
        this.usersList = usersList;
        this.name = "None";
        this.ID = 0;
    }

    public Contact(int group_ID, String name, ArrayList<User> usersList)
    {
        this.group_ID = group_ID;
        this.usersList = usersList;
        this.name = name;
        this.ID = 0;
    }

    public Contact(int group_ID, String name, ArrayList<User> usersList, int ID)
    {
        this.group_ID = group_ID;
        this.usersList = usersList;
        this.name = name;
        this.ID = ID;
    }




    public ArrayList<User> getusersList() {
        return usersList;
    }

    public void setusersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    public int getGroup_ID() {
        return group_ID;
    }

    public void setGroup_ID(int group_ID) {
        this.group_ID = group_ID;
    }

    public void AddUserTousersList(User user)
    {
        usersList.add(user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

}
