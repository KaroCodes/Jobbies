package com.androidcamp.jobbies;

import java.util.HashMap;
import java.util.Map;

/**
 * Class providing all user information
 */
public class User {
    private String id;
    private String name;
    private String surname;
    private String imageURL;
    private String email;

    private User(String id, String name, String surname, String imageURL, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.imageURL = imageURL;
        this.email = email;
    }

    public User(String id) {
        this(id, null, null, null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", getId());
        map.put("name", getName());
        map.put("surname", getSurname());
        map.put("email", getEmail());
        map.put("image_url", getId());
        return map;
    }
}
