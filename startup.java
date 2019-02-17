package com.prathmesh.itparking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class startup extends AppCompatActivity {
    String vehicle = "", passwordStored = "", response = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                //editor.clear();  //for bebugging
                //editor.commit(); //for bebugging
                vehicle = pref.getString("vehicleno", null);
                passwordStored = pref.getString("password", null);
                response = pref.getString("response", null);
                if (vehicle == null || response == null) {
                    Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(in);
                } else {

                    Intent in = new Intent(getApplicationContext(), profileActivity.class);
                    startActivity(in);
                }
                startup.this.finish();
            }
        }, 3000);

    }
}
