package com.androidcamp.jobbies;

import android.content.Intent;
import android.location.Geocoder;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private ArrayList<JobDescription> jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder gc = new Geocoder(MapsActivity.this);

        final JobDescription job = new JobDescription("New job", "some other job", "Tower Bridge, Tower Bridge Road, London", gc);

        addMarker(job);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(job.getLatLng()));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent myIntent = new Intent(MapsActivity.this, JobDescriptionActivity.class);
                myIntent.putExtra("title", job.getTitle());
                myIntent.putExtra("description", job.getDescription());
                myIntent.putExtra("address", job.getAddress_str());
                MapsActivity.this.startActivity(myIntent);
            }
        });
    }

    public void addMarker(JobDescription job)
    {
        LatLng jobAddress = job.getLatLng();
        Marker jobMarker = mMap.addMarker(new MarkerOptions()
                .position(jobAddress)
                .title(job.getTitle())
                .snippet(job.getShortDescription()));
    }

    public ArrayList<JobDescription> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<JobDescription> jobs) {
        this.jobs = jobs;
    }

    //TODO: retrieve from the database
    public void retreiveJobs()
    {

    }

    public void displayAllJobs()
    {
        Geocoder gc = new Geocoder(MapsActivity.this);
        for (int i = 0; i < jobs.size(); i++)
        {
            jobs.get(i).setGeocoder(gc);
            final JobDescription job = jobs.get(i);
            addMarker(job);

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent myIntent = new Intent(MapsActivity.this, JobDescriptionActivity.class);
                    myIntent.putExtra("title", job.getTitle());
                    myIntent.putExtra("description", job.getDescription());
                    myIntent.putExtra("address", job.getAddress_str());
                    MapsActivity.this.startActivity(myIntent);
                }
            });

        }
        //TODO: move the camera to a specific job
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(job.getLatLng()));

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
