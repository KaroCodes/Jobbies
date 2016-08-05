package com.androidcamp.jobbies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by demouser on 8/4/16.
 */
public class JobDescriptionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        Intent myI = getIntent();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView title = (TextView) findViewById(R.id.job_description_title);
        title.setText(myI.getStringExtra("title"));
        TextView description = (TextView) findViewById(R.id.job_description_description);
        description.setText(myI.getStringExtra("description"));
        TextView time = (TextView) findViewById(R.id.job_description_time);
        time.setText(myI.getStringExtra("time"));
        TextView address = (TextView) findViewById(R.id.job_description_adress);
        address.setText(myI.getStringExtra("address"));

        final String owner = getIntent().getStringExtra("owner");
        final String job_name = getIntent().getStringExtra("title");

        findViewById(R.id.apply_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JobDescriptionActivity.this, "Apply!", Toast.LENGTH_SHORT).show();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                
                // notifications:user_id -> name, job_title
                DatabaseReference listRef = rootRef.child("notifications:" + owner);
                DatabaseReference child = listRef.push();
                HashMap<String, Object> fields = new HashMap<>();
                fields.put("name", UserIDs.getsInstance().getCurrentUserName());
                fields.put("job", job_name);
                child.updateChildren(fields);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseNotificationHandler.getsInstance(JobDescriptionActivity.this).
                registerDatabaseListener(UserIDs.getsInstance().getCurrentUserId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseNotificationHandler.getsInstance(JobDescriptionActivity.this).
                unregisterDatabaseListener(UserIDs.getsInstance().getCurrentUserId());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (id == R.id.find) {

            Intent MyOffersActivity = new Intent(JobDescriptionActivity.this, ListActivity.class);
            startActivity(MyOffersActivity);
        }
        else if (user == null) {
            Intent AuthenticationActivity = new Intent(JobDescriptionActivity.this, AuthenticationActivity.class);
            startActivity(AuthenticationActivity);
        }
        else if (id == R.id.offer) {
                Intent MyOffersActivity = new Intent(JobDescriptionActivity.this, AddNewJobActivity.class);
                startActivity(MyOffersActivity);
            } else if (id == R.id.account_settings) {
                // Handle the camera action
            } else if (id == R.id.my_offers) {
                Intent MyOffersActivity = new Intent(JobDescriptionActivity.this, MyOffers.class);
                startActivity(MyOffersActivity);
            } else if (id == R.id.applicants) {

            } else if (id == R.id.my_applications) {

            } else if (id == R.id.app_settings) {

            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
