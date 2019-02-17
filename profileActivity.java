package com.prathmesh.itparking;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//import com.google.zxing.integration.android.IntentIntegrator;

public class profileActivity extends AppCompatActivity {


    String tu,tm,tv,tms,te,tes,location,lat,lang,active;
    TextView u,m,v;
    ImageView qr;
    Button scan,qrcode;
    ImageButton logout,settings;
    public ProgressDialog pd;
    public double latitude,longitude;
//    Spinner sp;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    Bitmap bitmap;

//    String options[]={" ","Profile","Settings"};

//    ArrayAdapter <String> adapter;
    //Context c;
    //Activity activity;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final AlertDialog builder = new AlertDialog.Builder(profileActivity.this).create();

        builder.setCancelable(false);
        builder.setTitle("Enable Location to proceed");
        builder.setMessage("Please enable your mobile phone's GPS to proceed");

        builder.setButton(AlertDialog.BUTTON_NEGATIVE,"Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setButton(AlertDialog.BUTTON_POSITIVE,"Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displayLocationSettingsRequest(profileActivity.this);

                if (ActivityCompat.checkSelfPermission(profileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(profileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(profileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);

                    return;
                }

                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            }
        });
        builder.show();
        if (ActivityCompat.shouldShowRequestPermissionRationale(profileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(profileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            buildLocationRequest();
            buildLocationCallback();

        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(profileActivity.this);



//        final SharedPreferences sharedpreferences = getSharedPreferences(profileActivity.MyPREFERENCES, Context.MODE_PRIVATE);
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        tu = bundle.getString("username");
//        tm =bundle.getString("mprimary");
//        tv = bundle.getString("vehicleno");
//        te = bundle.getString("eprimary");
//        tes = bundle.getString("esecondary");
//        tms = bundle.getString("msecondary");
//        location = bundle.getString("location");
//        lat=bundle.getString("lat");
//        lang=bundle.getString("lang");
        final SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
        String response = pref.getString("response", null);
        try {
            JSONObject sqlresult = new JSONObject(response);
          //  String status = sqlresult.getString("message");
            tu = sqlresult.getString("username");
            tm  = sqlresult.getString("mprimary");
           tms = sqlresult.getString("msecondary");
            te= sqlresult.getString("eprimary");
             tes= sqlresult.getString("esecondary");
           tv = sqlresult.getString("vehicleno");
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
         u = (TextView)findViewById(R.id.username);
        m = (TextView)findViewById(R.id.mobile);
        v = (TextView)findViewById(R.id.vehicle);
        qrcode = (Button) findViewById(R.id.qrcode);
        scan = (Button) findViewById(R.id.scan);
        u.setText(tu);
        m.setText(tm);
        v.setText(tv);
        logout = (ImageButton) findViewById(R.id.logout);
        settings = (ImageButton) findViewById(R.id.settings);
//        sp= (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(profileActivity.this,R.array.options,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp.setAdapter(adapter);
//     sp.setOnItemSelectedListener(profileActivity.this);


                JSONObject qrjson = new JSONObject();
                try {
                    qrjson.put("username", tu);
                    qrjson.put("mprimary",tm);
                    qrjson.put("vehicleno",tv);
                    qrjson.put("msecondary",tms);
                    qrjson.put("eprimary",te);
                    qrjson.put("esecondary",tes);
                }catch (JSONException e){
                    e.printStackTrace();
                }

                MultiFormatWriter mw = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = mw.encode(qrjson.toString(), BarcodeFormat.QR_CODE,337,332);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                   bitmap =barcodeEncoder.createBitmap(bitMatrix);
                  //  qr.setImageBitmap(bitmap); show the qr code
                }catch(WriterException e){
                    e.printStackTrace();
                }

                settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle pass = new Bundle();
                        pass.putString("vehicleno",tv);
                        pass.putString("name",tu);
                        Intent intent2 = new Intent(profileActivity.this,settings.class);
                        intent2.putExtras(pass);
                        startActivity(intent2);
                    }
                });


        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertadd = new AlertDialog.Builder(
                        profileActivity.this);
                alertadd.setTitle(tu.toUpperCase());
                LayoutInflater factory = LayoutInflater.from(profileActivity.this);
                 view = factory.inflate(R.layout.qrcode, null);

                ImageView image= (ImageView) view.findViewById(R.id.qrcodeop);
                image.setImageBitmap(bitmap);
                alertadd.setView(view);
                alertadd.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) { }
                });
                alertadd.show();




            }
        });
       // final IntentIntegrator i = new IntentIntegrator(profileActivity.this);
//        scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(profileActivity.this, scanActivity.class);
//                startActivity(intent);
//            }
//        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator i = new IntentIntegrator(profileActivity.this);
                i.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                i.setPrompt("scan");
                i.setCameraId(0);
                i.setBeepEnabled(false);
                i.setBarcodeImageEnabled(false);
                i.setOrientationLocked(true);
               i.setCaptureActivity(CaptureActivityPortait.class);
                i.initiateScan();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
               editor.clear();
                editor.commit();
                Intent intent = new Intent(profileActivity.this, LoginActivity.class);
                startActivity(intent);
                profileActivity.this.finish();
            }
        });
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback(){
            public int count=0;

            @Override
            public void onLocationResult(LocationResult locationResult) {

                for (Location location : locationResult.getLocations()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                lat = String.valueOf(latitude);
                lang = String.valueOf(longitude);
                Geocoder gcd = new Geocoder(profileActivity.this, Locale.getDefault());
                List<Address> addresses = null;

                try {
                    addresses = gcd.getFromLocation(latitude, longitude, 4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pd = new ProgressDialog(profileActivity.this);
                pd.setTitle("Detecting Location");
                pd.setMessage("Finding your Location...Please Wait");
                pd.show();
//                if (addresses.size() > 0) {
                    //  place.setText(addresses.get(0).getLocality()+" "+addresses.get(0).getAdminArea()+" "+addresses.get(0).getCountryName()+" "+addresses.get(0).getPostalCode()+" "+addresses.get(0).getFeatureName());
                    final Handler handler = new Handler();
                    final List<Address> finalAddresses = addresses;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    location = finalAddresses.get(0).getLocality() + " " + finalAddresses.get(0).getAdminArea() + " " + finalAddresses.get(0).getCountryName() + " " + finalAddresses.get(0).getPostalCode() + " " + finalAddresses.get(0).getFeatureName();
                                    pd.dismiss();
                                    break; // Exit the loop. Could return from the method, depending
                                    // on what it does...
                                } catch (NullPointerException e) {
                                }
                            }
                        }}, 30000);
//                location = addresses.get(0).getLocality() + " " + addresses.get(0).getAdminArea() + " " + addresses.get(0).getCountryName() + " " + addresses.get(0).getPostalCode() + " " + addresses.get(0).getFeatureName();
//                lat= String.valueOf(latitude);
//                lang= String.valueOf(longitude);
                    //  }
//                Toast.makeText(getApplicationContext(),location,Toast.LENGTH_LONG).show();
//                loca.setText("location found");
                }

        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10);


    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(profileActivity.this, 0x1);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(this,"you cancelled the scanning",Toast.LENGTH_LONG).show();
            }
            else
            {
              //  Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                try{ JSONObject qrresult = new JSONObject(result.getContents());
                    String userfname = qrresult.getString("username");
                    String mprimary = qrresult.getString("mprimary");
                    String msecondary = qrresult.getString("msecondary");
                    String eprimary = qrresult.getString("eprimary");
                    String eseondary = qrresult.getString("esecondary");
                    String vehicleno = qrresult.getString("vehicleno");
                    Intent intent = new Intent(profileActivity.this,selectAction.class);


                    Bundle pass = new Bundle();
                    pass.putString("username",userfname);
                    pass.putString("mprimary",mprimary);
                    pass.putString("msecondary",msecondary);
                    pass.putString("eprimary",eprimary);
                    pass.putString("esecondary",eseondary);
                    pass.putString("location",location);
                    pass.putString("vehicleno",vehicleno);
                    pass.putString("lat",lat);
                    pass.putString("lang",lang);
                    intent.putExtras(pass);
                    startActivity(intent);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
