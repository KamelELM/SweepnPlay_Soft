package com.univers.sweepplay.Main_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.univers.sweepplay.R;

public class FirstActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 500;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent( getApplicationContext(), MainActivity.class);
                startActivity(i);}
        },SPLASH_TIME_OUT );
    }
}
