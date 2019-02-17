package com.prathmesh.itparking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

public class LoginSender extends AsyncTask<Void,Void,String> {
    Context c;
    String urladdress;
    String vehicle,password,location,lat,lang;
    ProgressDialog pd;

    public LoginSender(Context context, double longitude,double latitude,String url,String loc,String vehicleno,String pass)
    {
        c=context;
        urladdress=url;
        vehicle=vehicleno;
        password=pass;
        location=loc;
        lat= String.valueOf(latitude);
        lang= String.valueOf(longitude);


    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pd= new ProgressDialog(c);
        pd.setTitle("send");
        pd.setMessage("Sending...Please Wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params)
    {
        return  this.send();
    }
    @Override
    protected void onPostExecute(String response)
    {
        super.onPostExecute(response);
        pd.dismiss();
        if(response != null)
        {

           try{ JSONObject sqlresult = new JSONObject(response);
            String status = sqlresult.getString("message");
               String username = sqlresult.getString("username");
               String mprimary = sqlresult.getString("mprimary");
               String msecondary = sqlresult.getString("msecondary");
               String eprimary = sqlresult.getString("eprimary");
               String eseondary = sqlresult.getString("esecondary");
               String vehcileno = sqlresult.getString("vehicleno");

 Intent intent = new Intent(c, profileActivity.class);

               Toast.makeText(c,status,Toast.LENGTH_LONG).show();

               Bundle pass = new Bundle();
               pass.putString("username",username);
               pass.putString("mprimary",mprimary);
               pass.putString("msecondary",msecondary);
               pass.putString("eprimary",eprimary);
               pass.putString("esecondary",eseondary);
               pass.putString("vehicleno",vehcileno);
               pass.putString("location",location);
               pass.putString("lat",lat);
               pass.putString("lang",lang);

               intent.putExtras(pass);
               c.startActivity(intent);
               }
            catch (JSONException e){
               e.printStackTrace();
            }
//            tvehicle.setText("");
//            tpassword.setText("");

        }
        else
        {
             Toast.makeText(c,"unsuccessfull",Toast.LENGTH_LONG).show();
        }

    }


    private String send()
    {
        HttpURLConnection con = Connecter.connect(urladdress);
        if(con==null)
        {
            return null;
        }
        else
        {
            try
            {
                OutputStream os = con.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                bw.write(new LoginDatapackeger(vehicle,password).packData());
                bw.flush();
                bw.close();
                os.close();

                int responseCode=con.getResponseCode();
                if(responseCode==con.HTTP_OK)
                {
                    BufferedReader br =new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuffer response=new StringBuffer();
                    String line;

                    while((line=br.readLine())!=null)
                    {
                        response.append(line);
                    }
                    br.close();

                    return response.toString();
                }
                else {

                }
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }



}
