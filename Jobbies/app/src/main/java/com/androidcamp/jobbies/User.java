package com.androidcamp.jobbies;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Class providing all user information
 */
public class User {
    private String id;
    private String name;
    private String imageURL;
    private String email;

    public User() {

    }

    public User(FirebaseUser user) {
        id = user.getUid();
        name = user.getDisplayName();
        imageURL = user.getPhotoUrl().toString();
        email = user.getEmail();
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
        map.put("email", getEmail());
        map.put("imageUrl", getImageURL());
        return map;
    }
}
