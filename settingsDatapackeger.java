package com.prathmesh.itparking;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class settingsDatapackeger {
    String choice,value,vehicleno;

    public settingsDatapackeger(String tvalue,String tchoice,String tvehicleno)
    {
        choice=tchoice;
        value=tvalue;
        vehicleno=tvehicleno;

    }

    public String packData(){
        JSONObject json = new JSONObject();
        StringBuffer packdata = new StringBuffer();
        try {
            json.put("value",value);
            json.put("choice",choice);
            json.put("vehicleno",vehicleno);

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
