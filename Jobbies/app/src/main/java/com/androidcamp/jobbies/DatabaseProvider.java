package com.androidcamp.jobbies;


import android.annotation.TargetApi;
import android.os.Build;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alina Dubatovka on 8/4/16.
 */
public class DatabaseProvider {
    private Firebase rootRef;

    public DatabaseProvider() {
        rootRef = new Firebase("https://jobbies-1485d.firebaseio.com/");
    }

    public void addOffer(JobDescription jobDescription) {
        Firebase offersRef = rootRef.child("offers");

        Map<String, Object> jobValues = jobDescription.toMap();
        //jobValues.put("isDone", "false");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        jobValues.put("ownerId", user.getUid());
        Map<String, Object> jobUpdates = new HashMap<>();

        String jobKey = offersRef.push().getKey();
        jobUpdates.put(jobKey, jobValues);
        offersRef.updateChildren(jobUpdates);
    }

    public void setJobDone(String jobId, String workerId) {
        rootRef.child("offers/" + jobId + "/isDone").setValue("true");
        rootRef.child("offers/" + jobId + "/workerId").setValue(workerId);
        //TODO notify owner
    }

    public void addUser(User user) {
        //TODO check if user already exists
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("users/" + user.getId(), user.toMap());
        rootRef.updateChildren(userUpdates);
    }

    public interface GetJobListener {
        void apply(Job job);
    }

    public void getJobs(final GeoLocation location, final int price, final Date tf, final JobCategory category,
                               final GetJobListener callback) {
        Query query = rootRef.child("offers/");
        query.orderByChild("posting_time").limitToFirst(1000);
        query.addValueEventListener(new ValueEventListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    final String key = child.getKey();
                    final JobDescription jobDescription = child.getValue(JobDescription.class);
                    /*if (!jobDescription.getIsVoluntary() && jobDescription.getPayment().getPrice() < price) {
                        return;
                    }
                    if (jobDescription != null && jobDescription.getDates()[0].after(tf)) {
                        return;
                    }*/
                    if (category != null && category.equals(jobDescription.getCategory())) {
                        return;
                    }

                    /*if (jobDescription.getLatLng() == null) {
                        return;
                    }
                    Location jobLocation = new Location(LocationManager.GPS_PROVIDER);
                    jobLocation.setLatitude(jobDescription.getLatLng().latitude);
                    jobLocation.setLongitude(jobDescription.getLatLng().longitude);

                    if (jobDescription.getLatLng() != null && location != null) {
                        Location currLocation = new Location(LocationManager.GPS_PROVIDER);
                        currLocation.setLatitude(location.latitude);
                        currLocation.setLongitude(location.longitude);

                        if (currLocation.distanceTo(jobLocation) > 1000) {
                            return;
                        }
                    }*/
                    System.out.println(jobDescription.getOwnerId());
                    getUserById(jobDescription.getOwnerId(), new GetUserListener() {
                        @Override
                        public void apply(User user) {
                            callback.apply(new Job(key, jobDescription, user));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public interface GetUserListener {
        void apply(User user);
    }

    public void getUserById(String userId, final GetUserListener callback) {
        Query query = rootRef.child("users/" + userId).orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.apply(user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
