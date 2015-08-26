package com.ogomez.tecnoshop.app.RestClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ogomez.tecnoshop.app.HomeTabs;
import com.ogomez.tecnoshop.app.Objects.User;
import com.ogomez.tecnoshop.app.Utils.Utils;

import org.apache.http.Header;
import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

import java.util.Map;
/**
 * Created by Oswaldo Gomez on 26/07/2015.
 */
public class Users {
    private static final String BASE_URL = "http://www.ogomez.com.mx/t_api/Usuarios";
    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void register(final Activity act,final String email){


        RequestParams params = new RequestParams();
        params.put("email", email);

        client.post(getAbsoluteUrl("/insert"), params, new AsyncHttpResponseHandler() {
            ProgressDialog dialog;
            String json_resp;

            @Override
            public void onStart() {
                //Log.v("UsersRest", "Comienza el request");
                dialog = new ProgressDialog(act, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                dialog.setTitle("Registrando");
                dialog.setMessage("espere por favor.!");
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {
                    json_resp = Utils.byteToJson(response);

                    Gson gson = new Gson();
                    User user = gson.fromJson(json_resp, User.class);
                    SharedPreferences prefs = act.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("id_user", user.getId());
                    editor.commit();
                    dialog.dismiss();

                    Intent i = new Intent(act,HomeTabs.class);
                    act.startActivity(i);
                    act.finish();

                } catch (Exception e) {
                    //Log.e("REST",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    json_resp = Utils.byteToJson(errorResponse);

                    dialog.dismiss();
                } catch (Exception ex) {
                    //Log.e("REST",ex.getMessage());
                }
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
