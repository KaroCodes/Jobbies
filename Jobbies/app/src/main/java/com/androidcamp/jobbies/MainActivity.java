package com.androidcamp.jobbies;

import android.content.Intent;
import android.net.Uri;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Firebase.setAndroidContext(MainActivity.this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        (if (user == null) {
            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
            startActivity(intent);
        }

        Button topButton = (Button) findViewById(R.id.find_button);
        Button bottomButton = (Button) findViewById(R.id.offer_button);

        Animation animUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        Animation animDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        topButton.startAnimation(animDown);
        bottomButton.startAnimation(animUp);
    }

    public void onFindClickHandler (View v)
    {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void onOfferClickHandler (View v)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent AuthenticationActivity = new Intent(MainActivity.this, AuthenticationActivity.class);
            startActivity(AuthenticationActivity);

        }
        else {
            Intent intent = new Intent(MainActivity.this, AddNewJobActivity.class);
            startActivity(intent);
            Firebase.setAndroidContext(MainActivity.this);

            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
/*        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.androidcamp.jobbies/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onStop() {
        super.onStop();

      /*  // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.androidcamp.jobbies/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }

    //navigation drawer

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

        try {
            String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            String emailAddress = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            Uri pic_url = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

            ImageView personImage = (ImageView) findViewById(R.id.personImage);
            Picasso.with(getApplicationContext()).load(pic_url).into(personImage);

            TextView personDisplayName = (TextView) findViewById(R.id.personDisplayNameTextView);
            personDisplayName.setText(displayName);

            TextView personEmailTextView = (TextView) findViewById(R.id.emailTextView);
            personEmailTextView.setText(emailAddress);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Intent AuthenticationActivity = new Intent(MainActivity.this, AuthenticationActivity.class);
            startActivity(AuthenticationActivity);
        }

        if (id == R.id.find) {
            Intent MyOffersActivity = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(MyOffersActivity);
        } else if (id == R.id.offer) {
            Intent MyOffersActivity = new Intent(MainActivity.this, AddNewJobActivity.class);
            startActivity(MyOffersActivity);
        } else if (id == R.id.account_settings) {

        } else if (id == R.id.my_offers) {
                Intent MyOffersActivity = new Intent(MainActivity.this, MyOffers.class);
                startActivity(MyOffersActivity);
        } else if (id == R.id.applicants) {

        } else if (id == R.id.my_applications) {
            Intent intent = new Intent(MainActivity.this, MyApplications.class);
            startActivity(intent);
        } else if (id == R.id.app_settings) {

        } else if (id == R.id.log_off) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
