package com.androidcamp.jobbies;

import android.location.Address;

import com.androidcamp.jobbies.timeframe.TimeFrame;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class JobDescription {

    private String title;
    private String description;
    private Address address;
    private Payment payment;
    private JobCategory category;
    private TimeFrame timeFrame;
    private boolean isVoluntary;

    public LatLng getLatLng() {
        return new LatLng(address.getLatitude(), address.getLongitude());
    }

    public String getShortDescription() {
        return this.title + "\n" + this.address.getCountryName();
    }
}
