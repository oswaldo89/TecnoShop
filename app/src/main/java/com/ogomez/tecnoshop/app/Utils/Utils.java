package com.ogomez.tecnoshop.app.Utils;

import android.util.Log;

/**
 * Created by Oswaldo Gomez on 26/07/2015.
 */
public class Utils {

    public static String json_resp;
    public static String URL_IMG = "http://www.ogomez.com.mx/";
    public static int refresh = 0;

    public static String byteToJson(byte[] response){
        try {
            json_resp = new String(response, "UTF-8");
        }catch (Exception e){
            //Log.e("Utils", e.getMessage());
        }
        return json_resp;
    }
}
