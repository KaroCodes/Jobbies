package com.androidcamp.jobbies;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

/**
 * Created by demouser on 8/4/16.
 */
public class myOffersFragment extends android.support.v4.app.Fragment{
   // private itemClicked myAactivity;
    private myAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_view, container, false);
        ListView listview=(ListView) v.findViewById(R.id.list_view);
        mAdapter = new myAdapter(2,-1,1);
        listview.setAdapter(mAdapter);

        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String countryName=(String) adapterView.getAdapter().getItem(i);
                myAactivity.showDetails(countryName);

            }
        });*/
        return v;
    }

    /*public interface itemClicked{
        public void showDetails(String name);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        if(activity instanceof itemClicked){
            myAactivity=(itemClicked) activity;
        }
        else{
            throw new IllegalStateException("the activiry should implement the itemclicked interface");
        }
    }*/

}
