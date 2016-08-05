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

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
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
        map.put("imageUrl", getImageURL());
        return map;
    }
}
