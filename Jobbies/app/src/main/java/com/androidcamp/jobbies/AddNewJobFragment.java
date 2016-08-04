package com.androidcamp.jobbies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class AddNewJobFragment extends Fragment {

    private JobDescription jobDescription;

    public AddNewJobFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_job, container, false);
    }

}
