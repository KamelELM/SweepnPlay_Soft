package com.univers.sweepplay.Main_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.univers.sweepplay.Device.JoinActivity;
import com.univers.sweepplay.Device.SelectPairingModeActivity;
import com.univers.sweepplay.Device.SettingsActivity;
import com.univers.sweepplay.Login.LoginActivity;
import com.univers.sweepplay.Login.RegisterActivity;
import com.univers.sweepplay.R;

public class MainActivityScroll extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = MainActivityScroll.this;

    private NestedScrollView nestedScrollView;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatButton appCompatButtonCreate;
    private AppCompatButton appCompatButtonJoin;
    private AppCompatButton appCompatButtonSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scroll);
        getSupportActionBar().hide();

        initViews();
        initListeners();
    }

    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);

        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
        appCompatButtonCreate = findViewById(R.id.appCompatButtonCreate);
        appCompatButtonJoin = findViewById(R.id.appCompatButtonJoin);
        appCompatButtonSettings = findViewById(R.id.appCompatButtonSettings);
    }


    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatButtonLogin.setOnClickListener(this);
        appCompatButtonCreate.setOnClickListener(this);
        appCompatButtonJoin.setOnClickListener(this);
        appCompatButtonSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;

            case R.id.appCompatButtonLogin:
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.appCompatButtonCreate:
                Intent intentSelect = new Intent(getApplicationContext(), SelectPairingModeActivity.class);
                startActivity(intentSelect);
                break;

            case R.id.appCompatButtonJoin:
                Intent intentJoin = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intentJoin);
                break;

            case R.id.appCompatButtonSettings:
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);
                break;
        }
    }
}