package com.androidcamp.jobbies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by demouser on 8/4/16.
 */
public class jobDescriptionFragment extends Fragment {

    private myAdapter mAdapter;
    TextView adressView;
    EditText phoneText;
    final String adresslabel="adress";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_job_description, container, false);
        phoneText=(EditText) v.findViewById(R.id.phone_edit_text);
        adressView= (TextView) v.findViewById(R.id.job_description_adress);
        Button findOnMapButton= (Button) v.findViewById(R.id.Show_on_map);

        phoneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneText.setText("");
            }
        });

        findOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapActivity = new Intent(getActivity(), MapsActivity.class);
                mapActivity.putExtra(adresslabel,adressView.getText());
                startActivity(mapActivity);
            }
        });
        return v;
    }
}
