package com.androidcamp.jobbies;

import android.location.Address;
import android.location.Geocoder;

import com.androidcamp.jobbies.timeframe.TimeFrame;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class JobDescription {

    private String title;
    private String description;
    private Geocoder geocoder;
    private String address_str;
    private List<Address> addresses;
    private Address address;
    private Payment payment;
    private JobCategory category;
    private TimeFrame timeFrame;
    private boolean isVoluntary;

    public JobDescription(String title, String description, String address_str, Geocoder geocoder)
    {
        this.title = title;
        this.description = description;
        this.address_str = address_str;
        this.geocoder = geocoder;
        try {
            addresses = geocoder.getFromLocationName(address_str, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public JobCategory getCategory() {
        return category;
    }

    public void setCategory(JobCategory category) {
        this.category = category;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public boolean isVoluntary() {
        return isVoluntary;
    }

    public void setVoluntary(boolean voluntary) {
        isVoluntary = voluntary;
    }

    public LatLng getLatLng() {

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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getAddress_str() {
        return address_str;
    }

    public void setAddress_str(String address_str) {
        this.address_str = address_str;
    }
}
