package com.univers.sweepplay.Main_Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;

import com.univers.sweepplay.Device.JoinActivity;
import com.univers.sweepplay.Device.SelectPairingModeActivity;
import com.univers.sweepplay.Device.SettingsActivity;
import com.univers.sweepplay.Login.LoginActivity;
import com.univers.sweepplay.Login.RegisterActivity;
import com.univers.sweepplay.R;

public class MainActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton log = findViewById(R.id.btn_log);
        ImageButton create = findViewById(R.id.btn_create);
        ImageButton join = findViewById(R.id.btn_join);
        ImageButton settings = findViewById(R.id.btn_settings);

        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class); // haut
                startActivity(i);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class); // gauche
                startActivity(i);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), JoinActivity.class); // droite
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SelectPairingModeActivity.class); // bas
                startActivity(i);
            }
        });

    }

}