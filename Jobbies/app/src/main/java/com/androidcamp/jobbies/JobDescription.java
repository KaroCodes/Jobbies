package com.androidcamp.jobbies;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private String ownerId;

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

    public boolean getIsVoluntary() {
        return isVoluntary;
    }

    public void setIsVoluntary(boolean voluntary) {
        isVoluntary = voluntary;
    }

    public LatLng getLatLng() {
        if (address == null) {
            return null;
        }
        return new LatLng(address.getLatitude(), address.getLongitude());
    }

    public String getShortDescription() {
        if (title == null || address == null) {
            return null;
        }
        return this.title + "\n" + this.address.getCountryName();
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", getTitle());
        map.put("description", getDescription());
        map.put("geocoder", getGeocoder());
        map.put("address", getAddress());
        map.put("payment", getPayment());
        map.put("category", getCategory());
        map.put("dates", getDates());
        map.put("isVoluntary", getIsVoluntary());
        map.put("short_description", getShortDescription());
        return map;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
