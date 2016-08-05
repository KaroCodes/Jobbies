package com.androidcamp.jobbies;

import android.content.Context;
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

public class ListFragment extends Fragment {
    private myAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_view, container, false);
        ListView listview=(ListView) view.findViewById(R.id.list_view);
        mAdapter = new myAdapter(1);
        listview.setAdapter(mAdapter);

        return view;
    }

}
