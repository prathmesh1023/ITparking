package com.prathmesh.itparking;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

public class settingsSender extends AsyncTask<Void,Void,String> {
    Context c;
    String urladdress,tvehicleno,value,tchoice;
    EditText tvalue;
    ProgressDialog pd;

    public settingsSender(Context context,String url,int choice,EditText val,String vehicleno)
    {
        c=context;
        urladdress=url;
        tvehicleno=vehicleno;
        tvalue=val;
        tchoice= String.valueOf(choice);
       value=tvalue.getText().toString();

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
            Toast.makeText(c,response,Toast.LENGTH_LONG).show();
            //tusername.setText("");

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
                bw.write(new settingsDatapackeger(value,tchoice,tvehicleno).packData());
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
