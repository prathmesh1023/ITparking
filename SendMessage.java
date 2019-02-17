package com.prathmesh.itparking;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessage extends AppCompatActivity {
    String username,primary,secondary,message,action;
    TextView actiont;
    Button pw,sw,pt,st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        username = data.getString("username");
        primary =data.getString("mprimary");
        secondary = data.getString("msecondary");
        message = data.getString("message");
        action = data.getString("action");
        actiont= (TextView) findViewById(R.id.action);
        actiont.setText(action);
        pw=(Button) findViewById(R.id.pwhatsapp);
        sw=(Button) findViewById(R.id.swhatsapp);
        pt=(Button) findViewById(R.id.psms);
        st=(Button) findViewById(R.id.ssms);

        pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatsapp(primary,message);
            }
        });

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatsapp(secondary,message);
            }
        });

        pt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(primary,message);
            }
        });

        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(secondary,message);
            }
        });
    }


    private void whatsapp(String tm,String msg) {
        try {

            String toNumber = "91"+tm; // Replace with mobile phone number without +Sign or leading zeros.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+msg));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendSMS(String tm,String msg) {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  ,tm);
        smsIntent.putExtra("sms_body" , msg);

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendMessage.this,"SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
    }