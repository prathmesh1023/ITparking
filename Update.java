package com.prathmesh.itparking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    String choice,vehicleno;
    TextView c,status;
    EditText old,new_value;
    Button update_info;
    ProgressDialog pd;
    public String url = "http://192.168.0.102/settings.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        choice = data.getString("choice");
        vehicleno = data.getString("vehicleno");
        c = (TextView) findViewById(R.id.action);
        status=(TextView) findViewById(R.id.status);
        old = (EditText) findViewById(R.id.old);
        new_value = (EditText) findViewById(R.id.new_value);
        update_info = (Button) findViewById(R.id.update);
        switch (choice)
        {
            case "1":
                c.setText("Change User's Primary number");
                break;
            case "2":
                c.setText("Change User's Secondary number");
                break;
            case "3":
                c.setText("Change Primary Emergency number");
                break;
            case "4":
                c.setText("Change Secondary Emergency number");
                break;
            case "5":
                c.setText("Change User's Address");
                break;
        }

        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choice.equals("5")) {
                    if (TextUtils.isEmpty(old.getText()))
                        old.setError("This field is required!");
                    else if (TextUtils.isEmpty(new_value.getText()))
                        new_value.setError("This field is required!");
                    else {
                        update(old.getText().toString(), new_value.getText().toString());
                        pd = new ProgressDialog(Update.this);
                        pd.setTitle("Update");
                        pd.setMessage("Updating...Please Wait");
                        pd.show();
                    }
                }
                else {
                    if (TextUtils.isEmpty(old.getText()) || old.getText().length() != 10)
                        old.setError("This field is required!");
                    else if (TextUtils.isEmpty(new_value.getText()) || new_value.getText().length() != 10)
                        new_value.setError("This field is required!");
                    else {

                        update(old.getText().toString(), new_value.getText().toString());
                        pd = new ProgressDialog(Update.this);
                        pd.setTitle("Update");
                        pd.setMessage("Updating...Please Wait");
                        pd.show();
                    }

                }
                }
        });

    }

    private void update(final String o, final String n) {
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public String status1;

            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    JSONObject json = new JSONObject(response);
                    status1 = json.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(response.contains("Update Successful"))
                {
                    status.setText("Update Successful");
                    Toast.makeText(getApplicationContext(),"Update Successful",Toast.LENGTH_LONG).show();
                }
                else{
                    status.setText("Error While Updating");
                    Toast.makeText(getApplicationContext(),"Error While Updating",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("vehicleno",vehicleno);
                params.put("old",o);
                params.put("new",n);
                params.put("choice",choice);
                return  params;

            }
        };
        Volley.newRequestQueue(this).add(request);

    }
}

