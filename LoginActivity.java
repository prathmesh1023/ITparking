package com.prathmesh.itparking;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    public EditText vehicle;
    public EditText pass;
    private Button login;
    private Button signup;
    public ProgressDialog pd;
    public static Activity fa;
    public String url = "http://192.168.0.102/login.php";
//    public String sendloc;
//    public double latitude,longitude;
//    public TextView loca;

//    LocationRequest locationRequest;
//    LocationCallback locationCallback;


    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
//    FusedLocationProviderClient fusedLocationProviderClient;
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1000: {
//                if (grantResults.length > 0) {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
//
//                    }
//                }
//            }
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fa = this;
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }


        // Set up the login form.
        vehicle = (EditText) findViewById(R.id.vehicle);
        pass = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        signup = (Button) findViewById(R.id.btnSingup);
       // loca = (TextView) findViewById(R.id.loca);
        vehicle.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

//        final AlertDialog builder = new AlertDialog.Builder(LoginActivity.this).create();
//
//        builder.setCancelable(false);
//        builder.setTitle("Enable Location to proceed");
//        builder.setMessage("Please enable your mobile phone's GPS to proceed");
//
//        builder.setButton(AlertDialog.BUTTON_NEGATIVE,"Close", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
//            }
//        });
//        builder.setButton(AlertDialog.BUTTON_POSITIVE,"Allow", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                displayLocationSettingsRequest(LoginActivity.this);
//
//                if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
//
//                    return;
//                }
//
//                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//
//            }
//        });
//        builder.show();



        SharedPreferences prefs = getSharedPreferences("loginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("vehicleno", String.valueOf(vehicle));
        editor.putString("password", String.valueOf(pass));
//        editor.putFloat("latitude", (float) latitude);
//        editor.putFloat("longitude", (float) longitude);
//        editor.putString("url", String.valueOf(url));
//        editor.putString("sendloc", String.valueOf(sendloc));
        editor.commit();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            //validate(name.getText().toString(), pass.getText().toString());
            public void onClick(View view) {
                // SharedPreferences.Editor editor = sharedpreferences.edit();
                Intent intent = new Intent(LoginActivity.this, DataInput.class);
                startActivity(intent);
            }

        });//singup onclickListener ends


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                        LoginSender s = new LoginSender(LoginActivity.this,longitude,latitude, url,sendloc,String.valueOf(vehicle),String.valueOf(pass));
//                        s.execute();
                 pd = new ProgressDialog(LoginActivity.this);
                pd.setTitle("Send");
                pd.setMessage("Sending...Please Wait");
//                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.show();
                loginUser();
            }
        });


//        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
//        } else {
//            buildLocationRequest();
//            buildLocationCallback();
//
//        }
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LoginActivity.this);


    }//onCreate ends

//    private void buildLocationCallback() {
//        locationCallback = new LocationCallback(){
//            @Override
//            public void onLocationResult(LocationResult locationResult)
//            {
//
//                    for(Location location:locationResult.getLocations()) {
//                        latitude=location.getLatitude();
//                        longitude=location.getLongitude();
//                    }
//                Geocoder gcd = new Geocoder(LoginActivity.this, Locale.getDefault());
//                List<Address> addresses = null;
//                try {
//                    addresses = gcd.getFromLocation(latitude,longitude,4);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//              //  if (addresses.size() > 0) {
//                  //  place.setText(addresses.get(0).getLocality()+" "+addresses.get(0).getAdminArea()+" "+addresses.get(0).getCountryName()+" "+addresses.get(0).getPostalCode()+" "+addresses.get(0).getFeatureName());
//                sendloc=addresses.get(0).getLocality()+" "+addresses.get(0).getAdminArea()+" "+addresses.get(0).getCountryName()+" "+addresses.get(0).getPostalCode()+" "+addresses.get(0).getFeatureName();
//              //  }
////                loca.setText("location found");
//            }
//        };
//    }
//
//    private void buildLocationRequest() {
//        locationRequest = new LocationRequest();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000);
//        locationRequest.setFastestInterval(3000);
//        locationRequest.setSmallestDisplacement(10);
//
//
//    }
//
//    private void displayLocationSettingsRequest(Context context) {
//        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
//                .addApi(LocationServices.API).build();
//        googleApiClient.connect();
//
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(10000 / 2);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the result
//                            // in onActivityResult().
//                            status.startResolutionForResult(LoginActivity.this, 0x1);
//                        } catch (IntentSender.SendIntentException e) {
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//
//                        break;
//                }
//            }
//        });
//    }


    public void loginUser()
    {
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public String status;

            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    JSONObject json = new JSONObject(response);
                     status = json.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                if(response.contains("user found"))
                {
                    SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("vehicleno", String.valueOf(vehicle));
                    editor.putString("password", String.valueOf(pass));
                    editor.putString("response",response);
                    editor.commit();
//                    result(response);
                    Intent in = new Intent(getApplicationContext(), profileActivity.class);
                    startActivity(in);

                }
                else{

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
                params.put("vehicleno",vehicle.getText().toString());
                params.put("password",pass.getText().toString());
                return  params;

            }
        };
        Volley.newRequestQueue(this).add(request);

//      LoginActivity.this.finish();
    }
}; //class ends

