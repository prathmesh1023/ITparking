package com.prathmesh.itparking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity {
    ImageView image;
    Button select,uploadpic;
    private String name,img_name,vehicle;
    public String uploadurl = "http://192.168.0.102/imgupload.php";
    private ProgressDialog pd;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Intent intent=getIntent();
        Bundle data = intent.getExtras();
        name = data.getString("name");
        vehicle = data.getString("vehicleno");
        select = (Button) findViewById(R.id.select);
        uploadpic = (Button) findViewById(R.id.uploadpic);
        image = (ImageView) findViewById(R.id.profilepic);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,1);
            }
        });

        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(Upload.this);
                pd.setTitle("Uploading");
                pd.setMessage("Uploading photo...Please Wait");
                pd.show();
                uploadImage();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!= null)
        {
            Uri path = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(path);
                bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    JSONObject json = new JSONObject(response);
                    String Respone = json.getString("message"); 
                    pd.dismiss();
                    Toast.makeText(Upload.this,Respone,Toast.LENGTH_LONG).show();

//                    image.setImageResource(0);
                    image.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                String imagedata = image_to_string(bitmap);
                params.put("image",imagedata);
                params.put("vehicleno",vehicle);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Upload.this);
        requestQueue.add(stringRequest);
    }

    private String image_to_string(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        String encode = Base64.encodeToString(imgBytes,Base64.DEFAULT);
        return  encode;
    }
}
