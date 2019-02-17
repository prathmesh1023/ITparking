package com.prathmesh.itparking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MakeCall extends AppCompatActivity {
    String username,primary,secondary,action;
    TextView actiont;
    public  static final int REQUEST_CALL = 1;
    Button p,s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_call);
        Intent intent = getIntent();
        Bundle datas = intent.getExtras();
        username = datas.getString("username");
        primary =datas.getString("mprimary");
        secondary = datas.getString("msecondary");
        action = datas.getString("action");
        actiont= (TextView) findViewById(R.id.action);
        actiont.setText(action);
        p=(Button) findViewById(R.id.primary);
        s=(Button) findViewById(R.id.secondary);

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall(primary);
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall(secondary);
            }
        });
    }

    private void makephonecall(String no) {
        if(ContextCompat.checkSelfPermission(MakeCall.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MakeCall.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else{
            String dial = "tel:"+no;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                makephonecall(primary);
            }else{
                Toast.makeText(MakeCall.this,"Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }
}
