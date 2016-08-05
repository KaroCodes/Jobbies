package com.androidcamp.jobbies;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by Anisa Llaveshi on 8/4/16.
 */
public class JobDescription_test {

    private String title;
    private String description;
    //private Address address;
    private Geocoder geocoder;
    private List<Address> addresses;
    private String address;

    public JobDescription_test(String title, String description, String address)
    {
        this.title = title;
        this.description = description;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Geocoder getGeocoder() {
        return geocoder;
    }

    public void setGeocoder(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public LatLng getLatLng() {
        try {
            addresses = geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double longitude = 0;
        double latitude = 0;
        if(addresses.size() > 0) {
            latitude= addresses.get(0).getLatitude();
            longitude= addresses.get(0).getLongitude();
        }

        return new LatLng(latitude, longitude);
    }

    public String getShortDescription() {
        return this.title;
    }
}