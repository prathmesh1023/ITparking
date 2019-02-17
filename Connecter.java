package com.prathmesh.itparking;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connecter {
    public static HttpURLConnection connect(String url_address){
        try
        {
            URL url = new URL(url_address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(20000);
            con.setDoOutput(true);
            con.setDoInput(true);
            return con;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
};
