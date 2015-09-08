package com.ogomez.tecnoshop.app.RestClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ogomez.tecnoshop.app.Objects.ItemP;
import com.ogomez.tecnoshop.app.Utils.Utils;
import org.apache.http.Header;
/**
 * Created by Oswaldo Gomez on 30/07/2015.
 */
public class Upload {

    private static final String BASE_URL = "http://www.ogomez.com.mx/t_api/Upload";
    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void insert(final Activity act, final ItemP ob){

        RequestParams params = new RequestParams();


        params.put("id_user", ob.getId_user() );
        params.put("nombre", ob.getNombre());
        params.put("categoria",ob.getCategoria() );
        params.put("descripcion", ob.getDescripcion());
        params.put("precio", ob.getPrecio());
        params.put("local", ob.getLocal());
        params.put("telefono", ob.getTelefono());
        try {
            params.put("img", ob.getImg());
        }catch(Exception e){

        }

        client.post(getAbsoluteUrl("/item"),params, new AsyncHttpResponseHandler() {
            ProgressDialog dialog;
            String json_resp;

            @Override
            public void onStart() {
                //Log.v("UsersRest", "Comienza el request");
                dialog = new ProgressDialog(act, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                dialog.setTitle("cargando");
                dialog.setMessage("espere por favor.!");
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {


                    json_resp = Utils.byteToJson(response);
                    //Log.e("REST", json_resp);


                    dialog.dismiss();
                    //se pone constante en 1 para actualizar en el onResume de la actividad principal
                    Utils.refresh = 1;
                    Toast.makeText(act,"Item agregado correctamente",Toast.LENGTH_LONG).show();

                    act.finish();


                } catch (Exception e) {
                    //Log.e("REST", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    json_resp = Utils.byteToJson(errorResponse);
                    //Log.e("REST", json_resp);

                    dialog.dismiss();
                } catch (Exception ex) {
                    //Log.e("REST", ex.getMessage());
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
