package com.prathmesh.itparking;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class selectAction extends AppCompatActivity {

    String tu,tm,tv,tms,te,tes,location,link,longitude,latitude;
    TextView u,v,loc;
    ImageButton logout;
    Button wrong,emergency,fatal;

    String mwrong="Your Vehicle is wrongly parked at following location :";
    String mfatal=" has met with a Fatal Accident at location : ";
    String memergency=" needs assistance for an emergency situation at location : ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        tu = data.getString("username");
        tm =data.getString("mprimary");
        tv = data.getString("vehicleno");
        te = data.getString("eprimary");
        tes = data.getString("esecondary");
        tms = data.getString("msecondary");
        location=data.getString("location");
        longitude=data.getString("lang");
        latitude=data.getString("lat");
        u = (TextView)findViewById(R.id.username);
        v = (TextView)findViewById(R.id.vehicle);
        loc =(TextView)findViewById(R.id.wrong);
        wrong = (Button)findViewById(R.id.wrong);
        emergency = (Button)findViewById(R.id.emergency);
        fatal = (Button)findViewById(R.id.fatal);
//        fcall = (Button)findViewById(R.id.fcall);
//        ewhatsapp = (Button)findViewById(R.id.ewhatsapp);
//        ecall = (Button)findViewById(R.id.ecall);

        logout = (ImageButton) findViewById(R.id.logout);
        u.setText(tu);
        v.setText(tv);
        link="https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude;

        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = "Dear "+tu+" ,"+mwrong+location+" "+link;
                AlertDialog.Builder builder = new AlertDialog.Builder(selectAction.this);
                builder.setTitle("Choose your Action:");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(selectAction.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Send Message");
                arrayAdapter.add("Make a call");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        switch(which)
                        {
                            case 0:
                                Intent intent = new Intent(selectAction.this,SendMessage.class);
                                Bundle pass = new Bundle();
                                pass.putString("username",tu);
                                pass.putString("mprimary",tm);
                                pass.putString("msecondary",tms);
                                pass.putString("message",text);
                                pass.putString("action","Wrong Parking");
                                intent.putExtras(pass);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(selectAction.this,MakeCall.class);
                                Bundle pass1 = new Bundle();
                                pass1.putString("username",tu);
                                pass1.putString("mprimary",tm);
                                pass1.putString("msecondary",tms);
                                pass1.putString("action","Wrong Parking");
                                intent1.putExtras(pass1);
                                startActivity(intent1);
                                break;
                        }

                    }
                });
                builder.show();
            }

        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = tu+memergency+location+" "+link;
                AlertDialog.Builder builder = new AlertDialog.Builder(selectAction.this);
                builder.setTitle("Choose your Action:");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(selectAction.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Send Message");
                arrayAdapter.add("Make a call");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        switch(which)
                        {
                            case 0:
                                Intent intent = new Intent(selectAction.this,SendMessage.class);
                                Bundle pass = new Bundle();
                                pass.putString("username",tu);
                                pass.putString("mprimary",te);
                                pass.putString("msecondary",tes);
                                pass.putString("message",text);
                                pass.putString("action","Emergency Situation");
                                intent.putExtras(pass);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(selectAction.this,MakeCall.class);
                                Bundle pass1 = new Bundle();
                                pass1.putString("username",tu);
                                pass1.putString("mprimary",te);
                                pass1.putString("msecondary",tes);
                                pass1.putString("action","Emergency Situation");
                                startActivity(intent1);
                                break;
                        }

                    }
                });
                builder.show();
            }

        });
        fatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = tu+mfatal+location+" "+link;
                AlertDialog.Builder builder = new AlertDialog.Builder(selectAction.this);
                builder.setTitle("Choose your Action:");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(selectAction.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Send Message");
                arrayAdapter.add("Make a call");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        switch(which)
                        {
                            case 0:
                                Intent intent = new Intent(selectAction.this,SendMessage.class);
                                Bundle pass = new Bundle();
                                pass.putString("username",tu);
                                pass.putString("mprimary",te);
                                pass.putString("msecondary",tes);
                                pass.putString("message",text);
                                pass.putString("action","Fatal Accident");
                                intent.putExtras(pass);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(selectAction.this,MakeCall.class);
                                Bundle pass1 = new Bundle();
                                pass1.putString("username",tu);
                                pass1.putString("mprimary",te);
                                pass1.putString("msecondary",tes);
                                pass1.putString("action","Fatal Accident");
                                startActivity(intent1);
                                break;
                        }

                    }
                });
                builder.show();
            }

        });

//        whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //whatsappcall(tm);
//                AlertDialog.Builder builderSingle = new AlertDialog.Builder(selectAction.this);
//               // builderSingle.setIcon(R.drawable.ic_launcher);
//                builderSingle.setTitle("Select One Name:-");
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(selectAction.this, android.R.layout.select_dialog_singlechoice);
//                arrayAdapter.add("whatsapp on personal no.");
//                arrayAdapter.add("text on personal no");
//                arrayAdapter.add("whatsapp on secondary no.");
//                arrayAdapter.add("text on secondary no.");
//
//                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog,int which) {
//                        switch(which)
//                        {
//                            case 0:
//                                whatsappcall(tm,wrong);
//                                break;
//                            case 1:
//                                sendSMS(tm,wrong);
//                                break;
//                            case 2:
//                                whatsappcall(tms,wrong);
//                                break;
//                            case 3:
//                                sendSMS(tms,wrong);
//                                break;
//                        }
//
//                    }
//                });
//                builderSingle.show();
//            }
//        });
//
//       call.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               AlertDialog.Builder builderSingle = new AlertDialog.Builder(selectAction.this);
//               // builderSingle.setIcon(R.drawable.ic_launcher);
//               builderSingle.setTitle("Select One Name:-");
//
//               final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(selectAction.this, android.R.layout.select_dialog_singlechoice);
//               arrayAdapter.add("Call on personal no.");
//               arrayAdapter.add("Call on Secondary no");
//
//               builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog, int which) {
//                       dialog.dismiss();
//                   }
//               });
//
//               builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog,int which) {
//                       switch(which)
//                       {
//                           case 0:
//                               makephonecall(tm);
//                               break;
//                           case 1:
//                               makephonecall(tms);
//                               break;
//                       }
//
//                   }
//               });
//               builderSingle.show();
//
//           }
//       });
//
//       fwhatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //whatsappcall(tm);
//                AlertDialog.Builder builderSingle = new AlertDialog.Builder(selectAction.this);
//                // builderSingle.setIcon(R.drawable.ic_launcher);
//                builderSingle.setTitle("Select One Name:-");
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(selectAction.this, android.R.layout.select_dialog_singlechoice);
//                arrayAdapter.add("whatsapp on Emergency no.");
//                arrayAdapter.add("text on Emergency no");
//                arrayAdapter.add("whatsapp on secondary Emergency no.");
//                arrayAdapter.add("text on secondary Emergency no.");
//
//                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog,int which) {
//                        switch(which)
//                        {
//                            case 0:
//                                whatsappcall(te,fatal);
//                                break;
//                            case 1:
//                                sendSMS(te,fatal);
//                                break;
//                            case 2:
//                                whatsappcall(tes,fatal);
//                                break;
//                            case 3:
//                                sendSMS(tes,fatal);
//                                break;
//                        }
//
//                    }
//                });
//                builderSingle.show();
//            }
//        });
//
//        fcall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builderSingle = new AlertDialog.Builder(selectAction.this);
//                // builderSingle.setIcon(R.drawable.ic_launcher);
//                builderSingle.setTitle("Select One Name:-");
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(selectAction.this, android.R.layout.select_dialog_singlechoice);
//                arrayAdapter.add("Call on Emergency no.");
//                arrayAdapter.add("Call on Secondary Emergency no");
//
//                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog,int which) {
//                        switch(which)
//                        {
//                            case 0:
//                                makephonecall(te);
//                                break;
//                            case 1:
//                                makephonecall(tes);
//                                break;
//                        }
//
//                    }
//                });
//                builderSingle.show();
//
//            }
//        });
//
//        ewhatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //whatsappcall(tm);
//                AlertDialog.Builder builderSingle = new AlertDialog.Builder(selectAction.this);
//                // builderSingle.setIcon(R.drawable.ic_launcher);
//                builderSingle.setTitle("Select One Name:-");
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(selectAction.this, android.R.layout.select_dialog_singlechoice);
//                arrayAdapter.add("whatsapp on Emergency no.");
//                arrayAdapter.add("text on Emergency no");
//                arrayAdapter.add("whatsapp on secondary Emergency no.");
//                arrayAdapter.add("text on secondary Emergency no.");
//
//                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog,int which) {
//                        switch(which)
//                        {
//                            case 0:
//                                whatsappcall(te,emergency);
//                                break;
//                            case 1:
//                                sendSMS(te,emergency);
//                                break;
//                            case 2:
//                                whatsappcall(tes,emergency);
//                                break;
//                            case 3:
//                                sendSMS(tes,emergency);
//                                break;
//                        }
//
//                    }
//                });
//                builderSingle.show();
//            }
//        });
//
//        ecall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builderSingle = new AlertDialog.Builder(selectAction.this);
//                // builderSingle.setIcon(R.drawable.ic_launcher);
//                builderSingle.setTitle("Select One Name:-");
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(selectAction.this, android.R.layout.select_dialog_singlechoice);
//                arrayAdapter.add("Call on Emergency no.");
//                arrayAdapter.add("Call on Secondary Emergency no");
//
//                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog,int which) {
//                        switch(which)
//                        {
//                            case 0:
//                                makephonecall(te);
//                                break;
//                            case 1:
//                                makephonecall(tes);
//                                break;
//                        }
//
//                    }
//                });
//                builderSingle.show();
//
//            }
//        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(selectAction.this, LoginActivity.class);
                startActivity(intent);
                selectAction.this.finish();
            }
        });

    }

//    private void makephonecall(String no) {
//        if(ContextCompat.checkSelfPermission(selectAction.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(selectAction.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
//        }
//        else{
//            String dial = "tel:"+no;
//            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
//
//        }
//    }
//
//    private void whatsappcall(String tm,String msg) {
//        try {
//            String text = msg+location+" "+link;//Replace with your message.
//
//            String toNumber = "91"+tm; // Replace with mobile phone number without +Sign or leading zeros.
//
//
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
//            startActivity(intent);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }


//
//    private void sendSMS(String tm,String msg) {
//        Log.i("Send SMS", "");
//        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//
//        smsIntent.setData(Uri.parse("smsto:"));
//        smsIntent.setType("vnd.android-dir/mms-sms");
//        smsIntent.putExtra("address"  ,tm);
//        smsIntent.putExtra("sms_body" , msg+location+" "+link);
//
//        try {
//            startActivity(smsIntent);
//            finish();
//            Log.i("Finished sending SMS...", "");
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(selectAction.this,
//                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getMenuInflater().inflate(R.menu);
//        return true;
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == REQUEST_CALL){
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                makephonecall(tm);
//            }else{
//                Toast.makeText(selectAction.this,"Permission Denied",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
