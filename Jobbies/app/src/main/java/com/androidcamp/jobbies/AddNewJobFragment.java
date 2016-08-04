package com.androidcamp.jobbies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class AddNewJobFragment extends Fragment {

    private JobDescription jobDescription;

    public AddNewJobFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_new_job, container, false);

        Spinner categorySpinner = (Spinner) v.findViewById(R.id.category_spinner);

        /*ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,
                JobCategory.getCategories());

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(aa);

        ArrayAdapter<String> adapter = ArrayAdapter.createFromResource(
                this, JobCategory.getCategories(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);*/

        return v;
    }

}
