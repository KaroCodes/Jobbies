package com.androidcamp.jobbies;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by demouser on 8/4/16.
 */
public class UserIDs {
    private static UserIDs sInstance;
    private static final Object lock = new Object();

    public static UserIDs getsInstance() {
        synchronized (lock) {
            if (sInstance == null) {
                sInstance = new UserIDs();
            }
            return sInstance;
        }
    }

    private UserIDs() {

    }

    public String getCurrentUserId() {
        //return FirebaseAuth.getInstance().getCurrentUser().getUid();
        return "fake_id";
    }

    public String getCurrentUserName() {
        //return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        return "fake_name";
    }
}
