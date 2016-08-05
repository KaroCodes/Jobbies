package com.androidcamp.jobbies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyApplications extends AppCompatActivity {

    private ListView mList;
    private ArrayList<String> message = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        mList = (ListView) findViewById(R.id.list_view);
        myAdapter adapter = new myAdapter();
        mList.setAdapter(adapter);
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference applicants = rootRef.child("applicants");
        applicants.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    String applicant_id = (String)d.child("applicant_id").getValue();
                    if(applicant_id.equals(UserIDs.getsInstance().getCurrentUserId())) {
                        String job_id = (String)d.child("job_id").getValue();
                        //if(rootRef.child("offers").child(job_id).child("isDone").toString() == null) {
                            String offerer = (String) d.child("owner_id").getValue();
                            message.add("You applied for the job " + job_id + " offered by " + offerer + ".");
                        //}
                    }

                }
                if(message.size() == 0) {
                    message.add("You didn't applied for any jobs recently :(");
                }
                mList.invalidateViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class myAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return message.size();
        }

        @Override
        public Object getItem(int i) {
            return message.get(i);
        }

        @Override
        public long getItemId(int i) {
            return message.get(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView tv = new TextView(MyApplications.this);
            tv.setText(message.get(i));
            return tv;
        }
    }
}
