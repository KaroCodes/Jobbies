package com.androidcamp.jobbies;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by demouser on 8/4/16.
 */
public class FirebaseNotificationHandler {
    private static FirebaseNotificationHandler sInstance;
    private static final Object lock = new Object();

    public static FirebaseNotificationHandler getsInstance(Context context) {
        synchronized (lock) {
            if (sInstance == null) {
                sInstance = new FirebaseNotificationHandler(context);
            }
            return sInstance;
        }
    }

    private boolean mRegistered = false;
    private Context mContext;
    private final ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            // TODO: Do not show a Notification if it has been dismissed by the user already.
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(mContext)
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("Application!")
                            .setAutoCancel(true)
                            .setContentText("Somebody applied for your job.");
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(mContext, NotificationActivity.class);

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
            // Adds the Intent that starts the Activity to the top of the stack3
            stackBuilder.addNextIntent(resultIntent);

            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            Intent deleteIntent = new Intent(mContext, NotificationService.class);
            PendingIntent deletePendingIntent = PendingIntent.getService(
                    mContext,
                    0,
                    deleteIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setAutoCancel(true);
            mBuilder.setDeleteIntent(deletePendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the Notification later on.
            mNotificationManager.notify(19, mBuilder.build());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private FirebaseNotificationHandler(Context context) {
        mContext = context.getApplicationContext();
    }

    public void registerDatabaseListener(String userID) {
        if (mRegistered) return;
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference listRef = rootRef.child("notifications:" + userID);

        listRef.addChildEventListener (mListener);
        mRegistered = true;

    }

    public void unregisterDatabaseListener(String userID) {
        if (!mRegistered) return;
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference listRef = rootRef.child("notifications:" + userID);
        listRef.removeEventListener(mListener);
        mRegistered = false;
    }

}
