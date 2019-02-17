package com.prathmesh.itparking;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class LoginDatapackeger {
    String vehicle,password;

    public LoginDatapackeger(String v,String p)
    {
        vehicle=v;
        password=p;
    }

    public String packData(){
        JSONObject json = new JSONObject();
        StringBuffer packdata = new StringBuffer();
        try {
            json.put("vehicleno",vehicle);
            json.put("password",password);

            Boolean firstvalue = true;
            Iterator it=json.keys();

            do {
                String key= it.next().toString();
                String value= json.get((String)key).toString();
                if(firstvalue){
                    firstvalue=false;
                }
                else
                {
                    packdata.append("&");
                }
                try {
                    packdata.append(URLEncoder.encode(key, "UTF-8"));
                    packdata.append("=");
                    packdata.append(URLEncoder.encode(value,"UTF-8"));
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }

            }while (it.hasNext());
            return packdata.toString();

        }catch(JSONException e){
            e.printStackTrace();
        }
        return  null;
    }
}
