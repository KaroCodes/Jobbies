package com.androidcamp.jobbies;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.android.gms.maps.model.LatLng;


import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class JobDescription {

    private String title;
    private String description;
    private String address_str;
    private Payment payment;
    private String category;
    private Date date;
    private boolean isVoluntary;
    private String ownerId;

    public JobDescription(){

    }

    public JobDescription(String title, String description, String address_str)
    {
        this.title = title;
        this.description = description;
        this.address_str = address_str;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getIsVoluntary() {
        return isVoluntary;
    }

    public void setIsVoluntary(boolean voluntary) {
        isVoluntary = voluntary;
    }

    @JsonIgnore
    public String getShortDescription() {
        return this.title + "\n" + this.getAddress_str() + "\n" + this.getDescription();
    }

    public String getAddress_str() {
        return address_str;
    }

    public void setAddress_str(String address_str) {
        this.address_str = address_str;
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", getTitle());
        map.put("description", getDescription());
        map.put("payment", getPayment());
        map.put("category", getCategory());
        map.put("date", getDate());
        map.put("isVoluntary", getIsVoluntary());
            map.put("address_str", getAddress_str());
        return map;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
