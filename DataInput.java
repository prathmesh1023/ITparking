package com.prathmesh.itparking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataInput extends AppCompatActivity{
    public EditText tusername, tpassword, tmprimary, tmsecondary, teprimary, tesecondary, tvehicleno, tuser_address;
    private Button register;
 private ProgressDialog pd;
 //private com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
   public String url = "http://192.168.0.102/register.php";
    Intent intent = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);

        tusername = (EditText) findViewById(R.id.username);
        tpassword = (EditText) findViewById(R.id.upassword);
        tmprimary = (EditText) findViewById(R.id.mprimary);
        tmsecondary = (EditText) findViewById(R.id.msecondary);
        teprimary = (EditText) findViewById(R.id.eprimary);
        tesecondary = (EditText) findViewById(R.id.esecondary);
        tvehicleno = (EditText) findViewById(R.id.vehicleno);
         tuser_address= (EditText) findViewById(R.id.user_address);
        register = (Button) findViewById(R.id.btnRegister);
//      progress_dialog = new ProgressDialog(this);
//        final String username = tusername.getText().toString();
//        final String password = tpassword.getText().toString();
//        final String mprimary = tmprimary.getText().toString();
//        final String msecondary = tusername.getText().toString();
//        final String eprimary = teprimary.getText().toString();
//        final String esecondary = tesecondary.getText().toString();
//        final String vehicleno = tvehicleno.getText().toString();
//        final String userfname = tuser_address.getText().toString();
        tvehicleno.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        final SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tusername.getText()))
                    tusername.setError("First name is required!");
                    else if(TextUtils.isEmpty(tmprimary.getText()) || tmprimary.getText().length()!=10)
                        tmprimary.setError("Whatsapp no. is required!");
                    else if(TextUtils.isEmpty(tmsecondary.getText())|| tmsecondary.getText().length()!=10)
                        tmsecondary.setError("Secondary number is required!");
                    else if(TextUtils.isEmpty(teprimary.getText())|| teprimary.getText().length()!=10)
                        teprimary.setError("Emergency no is required!");
                   else if(TextUtils.isEmpty(tesecondary.getText())|| tesecondary.getText().length()!=10)
                        tesecondary.setError("Emergency no is required!");
                    else if(TextUtils.isEmpty(tvehicleno.getText()))
                        tvehicleno.setError("Vehicle no is required!");
                    else if(TextUtils.isEmpty(tuser_address.getText()))
                        tuser_address.setError("user address is required!");
                    else if(TextUtils.isEmpty(tpassword.getText()))
                        tpassword.setError("Password is required");
//                    isValidPassword(tpassword.getText().toString())
                    else {
                        if(isValidPassword(tpassword.getText().toString())){
                    pd = new ProgressDialog(DataInput.this);
                    pd.setTitle("Register");
                    pd.setMessage("Registering user...Please Wait");
                    pd.show();
                    registerUser();}
                    else
                        {
                            tpassword.setError("Password should contain Uppercase character followed by lowercase character, a special character out of @#$%&* and a number");

                        }
                }

                }


//            Handler handle=new Handler()
//            {
//                public void hanndleMessage(Message msg){
//                    super.handleMessage(msg);
//                    pd.incrementProgressBy(1);
//
//
//                }
//            };
        });
    }


    public boolean isValidPassword(String pass)
    {
        Pattern pattern = Pattern.compile("[A-Z]++[a-z]++[@#$%&*][0-9]++");
        Matcher matcher = pattern.matcher(pass);
      return matcher.matches();
    }

    public void registerUser()
    {
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public String status;

            @Override
            public void onResponse(String response1) {
                pd.dismiss();
                try {
                    JSONObject json = new JSONObject(response1);
                    status = json.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(response1.contains("user registered successfully"))
                {
                  loginUser();
                  Toast.makeText(getApplicationContext(),"User registered successfully",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error while registering",Toast.LENGTH_LONG).show();
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
                params.put("username",tusername.getText().toString());
                params.put("password",tpassword.getText().toString());
                params.put("mprimary",tmprimary.getText().toString());
                params.put("msecondary",tmsecondary.getText().toString());
                params.put("eprimary",teprimary.getText().toString());
                params.put("esecondary",tesecondary.getText().toString());
                params.put("vehicleno",tvehicleno.getText().toString());
                params.put("user_address",tuser_address.getText().toString());
                return  params;

            }
        };
        Volley.newRequestQueue(this).add(request);
//        DataInput.this.finish();
    }

    public void loginUser()
    {
        StringRequest request=new StringRequest(Request.Method.POST, "http://192.168.0.102/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("user found"))
                {
                    SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("vehicleno", String.valueOf(tvehicleno));
                    editor.putString("password", String.valueOf(tpassword));
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
                params.put("vehicleno",tvehicleno.getText().toString());
                params.put("password",tpassword.getText().toString());
                return  params;

            }
        };
        Volley.newRequestQueue(this).add(request);
//        LoginActivity.this.finish();
    }
};
