package com.androidcamp.jobbies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationActivity extends AppCompatActivity {


    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> jobs = new ArrayList<>();
    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notofication);

        mList = (ListView) findViewById(R.id.applications_list);
        MyAdapter adapter = new MyAdapter();
        mList.setAdapter(adapter);

        FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference listRef = rootRef.child("notifications:" + UserIDs.getsInstance().getCurrentUserId());
        final ValueEventListener mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("NotificationActivity", "onDataChange");
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    String n = (String)d.child("name").getValue();
                    String j = (String)d.child("job").getValue();
                    Log.d("onDataChangeLoop", n + " ; " + j);
                    names.add(n);
                    jobs.add(j);
                    mList.invalidateViews();
                }
                listRef.removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR!!!!!!!", "Bad! onCalcel!");
            }
        };
        listRef.addListenerForSingleValueEvent(mListener);
    }



    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int i) {
            return names.get(i);
        }

        @Override
        public long getItemId(int i) {
            return names.get(i).hashCode();
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            TextView view;
            if(null == convertView) {
                view = new TextView(NotificationActivity.this);
            }
            else {
                view = (TextView)convertView;
            }

            view.setText(names.get(i) + " applied for " + jobs.get(i));
            return view;
        }
    }
}
