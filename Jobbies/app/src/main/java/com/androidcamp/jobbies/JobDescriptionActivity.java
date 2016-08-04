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
        String value = intent.getStringExtra("key"); //if it's a string you stored.

        TextView tv = (TextView) findViewById(R.id.test_text);
        tv.setText(value);
    }
}
