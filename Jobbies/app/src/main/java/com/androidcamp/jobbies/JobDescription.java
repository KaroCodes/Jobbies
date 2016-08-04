package com.androidcamp.jobbies;

import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class JobDescription {

    private String title;
    private String description;
    private Geocoder geocoder;
    private Address address;
    private Payment payment;
    private JobCategory category;
    private Date[] dates;
    private boolean isVoluntary;


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

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public boolean isVoluntary() {
        return isVoluntary;
    }

    public void setVoluntary(boolean voluntary) {
        isVoluntary = voluntary;
    }

    public LatLng getLatLng() {
        return new LatLng(address.getLatitude(), address.getLongitude());
    }

    public String getShortDescription() {
        return this.title + "\n" + this.address.getCountryName();
    }
}
