package com.prathmesh.itparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
//implements AdapterView.OnItemSelectedListener
public class settings extends AppCompatActivity  {
    Button mprimary,msecondary,eprimary,esecondary,user_address,upload;
//    Spinner sp;
//    EditText value;
    String vehicleno,name;
    public String url = "http://192.168.0.104/settings.php";
//    ArrayAdapter<String> adapter;
//    int choice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Intent intent=getIntent();
        Bundle data = intent.getExtras();
        vehicleno = data.getString("vehicleno");
        name = data.getString("name");

        mprimary = (Button) findViewById(R.id.mprimary);
        msecondary = (Button) findViewById(R.id.msecondary);
        eprimary = (Button) findViewById(R.id.eprimary);
        esecondary = (Button) findViewById(R.id.esecondary);
        user_address = (Button) findViewById(R.id.user_address);
        upload = (Button) findViewById(R.id.upload);

        mprimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   Intent intent = new Intent(settings.this, Update.class);
                   Bundle pass = new Bundle();
                   pass.putString("choice", "1");
                   pass.putString("vehicleno",vehicleno);
                   intent.putExtras(pass);
                    startActivity(intent);
                    }
        });

            msecondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this,Update.class);
                Bundle pass = new Bundle();
                pass.putString("choice","2");
                pass.putString("vehicleno",vehicleno);
                intent.putExtras(pass);
                startActivity(intent);
            }
        });

        eprimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this,Update.class);
                Bundle pass = new Bundle();
                pass.putString("choice","3");
                pass.putString("vehicleno",vehicleno);
                intent.putExtras(pass);
                startActivity(intent);
            }
        });

        esecondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this,Update.class);
                Bundle pass = new Bundle();
                pass.putString("choice","4");
                pass.putString("vehicleno",vehicleno);
                intent.putExtras(pass);
                startActivity(intent);
            }
        });

        user_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this,Update.class);
                Bundle pass = new Bundle();
                pass.putString("choice","5");
                pass.putString("vehicleno",vehicleno);
                intent.putExtras(pass);
                startActivity(intent);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(settings.this,Upload.class);
                Bundle pass = new Bundle();
                pass.putString("name",name);
                pass.putString("vehicleno",vehicleno);
                intent4.putExtras(pass);
                startActivity(intent4);
            }
        });


    }
}