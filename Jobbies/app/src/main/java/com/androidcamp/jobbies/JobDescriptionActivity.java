package com.androidcamp.jobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JobDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);

        Intent intent = getIntent();
        String value = intent.getStringExtra("title"); //if it's a string you stored.

        TextView tv = (TextView) findViewById(R.id.text_title);
        tv.setText(value);

        tv = (TextView) findViewById(R.id.job_description);
        tv.setText(intent.getStringExtra("description"));

        tv = (TextView) findViewById(R.id.address);
        tv.setText(intent.getStringExtra("address"));
    }
}
