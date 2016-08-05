package com.androidcamp.jobbies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.identity.intents.AddressConstants;

public class ListFragment extends Fragment {
    private myAdapter mAdapter;
final String filter="filter";
    final String fromPrice="fromPrice";
    final int getAllJobs=1;
    final int getJobsByUser=2;
    final int getApplicationsByUser=3;
    final int getApplicatsByUser=4;
    final int getJobsVolunteer=5;
    final int filterByPrice=6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_view, container, false);
        ListView listview=(ListView) view.findViewById(R.id.list_view);

        /* Intent thisIntent=getActivity().getIntent();
         int number=(int) thisIntent.getExtras().get(filter);
        int price=(int) thisIntent.getExtras().get(fromPrice);

        if(number==getJobsVolunteer){
            mAdapter = new myAdapter(5,0);
        }
        else if(number==getAllJobs) {
            mAdapter = new myAdapter(1,0);
        }

        else{
            mAdapter = new myAdapter(6,0);
        }*/
        mAdapter = new myAdapter(1,0,0);
       listview.setAdapter(mAdapter);
        return view;
    }

}
