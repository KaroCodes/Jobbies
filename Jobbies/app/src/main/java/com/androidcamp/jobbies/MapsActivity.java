package com.androidcamp.jobbies;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

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
}
