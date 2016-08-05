package com.androidcamp.jobbies;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    final String filter="filter";
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng latLng;

    private GoogleMap mMap;
    public static Geocoder gc;
    private LatLng current;
    private boolean changeLocation;
    private String show_onmap_addr;

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

        changeLocation = true;

        if (mGoogleApiClient == null)
        {
            buildGoogleApiClient();
        }

        gc = new Geocoder(MapsActivity.this);
        show_onmap_addr = null;
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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        // Create an instance of GoogleAPIClient.
        buildGoogleApiClient();


        DatabaseProvider databaseProvider = new DatabaseProvider();
        databaseProvider.getJobs(null, 0, 0, null, null, new DatabaseProvider.GetJobListener() {
            @Override
            public void apply(final Job job) {
                addMarker(job);

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent myIntent = new Intent(MapsActivity.this, JobDescriptionActivity.class);
                        myIntent.putExtra("title", job.getTitle());
                        myIntent.putExtra("description", job.getShortDescription());
                        myIntent.putExtra("address", job.getDescription().getAddress_str());
                        myIntent.putExtra("ID", job.getId());
                        myIntent.putExtra("owner", job.getOwnerId());
                        myIntent.putExtra("time", job.getDate().toString());
                        MapsActivity.this.startActivity(myIntent);
                    }
                });
                Log.d("IS CALLED", "APPLY CALLED");
            }
        });


        Intent intent = getIntent();
        show_onmap_addr = intent.getStringExtra("adress");
        if (show_onmap_addr != null)
            Log.d("ADDRESSS IS", show_onmap_addr);
        /*
        if (show_onmap_addr != null)
        {
            Log.d("ADDRESSSSSS", show_onmap_addr);
            LatLng new_loc = new LatLng(0, 0);
            try {
                new_loc = new LatLng(gc.getFromLocationName(show_onmap_addr, 1).get(0).getLatitude(), gc.getFromLocationName(show_onmap_addr, 1).get(0).getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new_loc).zoom(10).build();

            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            show_onmap_addr = null;
        }
        else
        {
            Log.d("DEBUGGGG", "NULLLLLLL");
        }
        */
    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
    }

    public void addMarker(Job job) {
        String address_str = job.getDescription().getAddress_str();
        List<Address> addresses = new List<Address>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Address> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] ts) {
                return null;
            }

            @Override
            public boolean add(Address address) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Address> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, Collection<? extends Address> collection) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Address get(int i) {
                return null;
            }

            @Override
            public Address set(int i, Address address) {
                return null;
            }

            @Override
            public void add(int i, Address address) {

            }

            @Override
            public Address remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Address> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Address> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<Address> subList(int i, int i1) {
                return null;
            }
        };
        try {
            addresses = gc.getFromLocationName(address_str, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double longitude = 0;
        double latitude = 0;
        if(addresses.size() > 0) {
            latitude= addresses.get(0).getLatitude();
            longitude= addresses.get(0).getLongitude();
        }

        LatLng jobAddress = new LatLng(latitude, longitude);
        Marker jobMarker = mMap.addMarker(new MarkerOptions()
                .position(jobAddress)
                .title(job.getTitle())
                .snippet(job.getShortDescription()));
    }

    //public ArrayList<Job> getJobs() {
    //    return jobs;
    //}

    /*public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }*/

    //TODO: retrieve from the database
    public void retreiveJobs() {

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
        getMenuInflater().inflate(R.menu.action_bar_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_volunteer) {
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            intent.putExtra(filter,5);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (id == R.id.find) {

            Intent MyOffersActivity = new Intent(MapsActivity.this, ListActivity.class);
            startActivity(MyOffersActivity);
        }
        else if (user == null) {
            Intent AuthenticationActivity = new Intent(MapsActivity.this, AuthenticationActivity.class);
            startActivity(AuthenticationActivity);
        }
        else if (id == R.id.offer) {
                Intent MyOffersActivity = new Intent(MapsActivity.this, AddNewJobActivity.class);
                startActivity(MyOffersActivity);
            } else if (id == R.id.account_settings) {
                // Handle the camera action
            } else if (id == R.id.my_offers) {
                Intent MyOffersActivity = new Intent(MapsActivity.this, MyOffers.class);
                startActivity(MyOffersActivity);
            } else if (id == R.id.applicants) {

            } else if (id == R.id.my_applications) {
                Intent intent = new Intent(MapsActivity.this, MyApplications.class);
                startActivity(intent);
            } else if (id == R.id.app_settings) {

            }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        //place marker at current position
        //mGoogleMap.clear();
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        //zoom to current position:
        if (changeLocation)
        {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(10).build();

            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }
        changeLocation = false;

        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseNotificationHandler.getsInstance(MapsActivity.this).
                registerDatabaseListener(UserIDs.getsInstance().getCurrentUserId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseNotificationHandler.getsInstance(MapsActivity.this).
                unregisterDatabaseListener(UserIDs.getsInstance().getCurrentUserId());

    }
}
