package com.androidcamp.jobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onFindClickHandler (View v)
    {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void onOfferClickHandler (View v)
    {
        Intent intent = new Intent(MainActivity.this, AddNewJobActivity.class);
        startActivity(intent);
    }
}
