package com.prathmesh.itparking;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class Datapackeger {
    String username,password,mprimary,msecondary,eprimary,esecondary,vehicleno,user_address;

    public Datapackeger(String u,String p,String mp,String ms,String ep,String es,String v,String ua)
    {
        username=u;
        password=p;
        mprimary=mp;
        msecondary=ms;
        eprimary=ep;
        esecondary=es;
        vehicleno=v;
        user_address=ua;
    }

    public String packData(){
        JSONObject json = new JSONObject();
        StringBuffer packdata = new StringBuffer();
        try {
            json.put("username",username);
            json.put("password",password);
            json.put("mprimary",mprimary);
            json.put("msecondary",msecondary);
            json.put("eprimary",eprimary);
            json.put("esecondary",esecondary);
            json.put("vehicleno",vehicleno);
            json.put("user_address",user_address);

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
