package com.ogomez.tecnoshop.app.RestClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ogomez.tecnoshop.app.Adapters.AdapterItems;
import com.ogomez.tecnoshop.app.Objects.ItemP;
import com.ogomez.tecnoshop.app.Utils.Utils;
import com.quentindommerc.superlistview.SuperListview;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Oswaldo Gomez on 26/07/2015.
 */
public class Items {
    private static final String BASE_URL = "http://www.ogomez.com.mx/t_api/Items";
    public static AsyncHttpClient client = new AsyncHttpClient();
    public static AdapterItems adpt;
    public static void getAll(final Activity act, final SuperListview mRecyclerView) {

        client.get(getAbsoluteUrl("/getAll"), new AsyncHttpResponseHandler() {
            String json_resp;

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {


                    json_resp = Utils.byteToJson(response);
                    Gson gson = new Gson();

                    Type listType = new TypeToken<List<ItemP>>() {
                    }.getType();
                    List<ItemP> posts = (List<ItemP>) gson.fromJson(json_resp, listType);


                    // Limpiar elementos antiguos
                    adpt = new AdapterItems(act, posts);
                    mRecyclerView.setAdapter(adpt);
                    adpt.notifyDataSetChanged();

                } catch (Exception e) {
                    //Log.e("REST", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    json_resp = Utils.byteToJson(errorResponse);
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

    public static void getByCatego(final Activity act, final SuperListview mRecyclerView, String _catego) {

        RequestParams params = new RequestParams();
        params.put("categoria", _catego);

        client.get(getAbsoluteUrl("/getAllByCatego"),params, new AsyncHttpResponseHandler() {
            String json_resp;

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {


                    json_resp = Utils.byteToJson(response);
                    Gson gson = new Gson();

                    Type listType = new TypeToken<List<ItemP>>() {
                    }.getType();
                    List<ItemP> posts = (List<ItemP>) gson.fromJson(json_resp, listType);


                    // Limpiar elementos antiguos
                    adpt = new AdapterItems(act, posts);
                    mRecyclerView.setAdapter(adpt);
                    adpt.notifyDataSetChanged();

                } catch (Exception e) {
                    //Log.e("REST", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    json_resp = Utils.byteToJson(errorResponse);
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

    public static void getBySearch(final Activity act, final SuperListview mRecyclerView, String _catego) {

        RequestParams params = new RequestParams();
        params.put("categoria", _catego);

        client.get(getAbsoluteUrl("/getAllBySearch"),params, new AsyncHttpResponseHandler() {
            String json_resp;

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {


                    json_resp = Utils.byteToJson(response);
                    Gson gson = new Gson();

                    Type listType = new TypeToken<List<ItemP>>() {
                    }.getType();
                    List<ItemP> posts = (List<ItemP>) gson.fromJson(json_resp, listType);


                    // Limpiar elementos antiguos
                    adpt = new AdapterItems(act, posts);
                    mRecyclerView.setAdapter(adpt);
                    adpt.notifyDataSetChanged();

                } catch (Exception e) {
                    //Log.e("REST", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                try {
                    json_resp = Utils.byteToJson(errorResponse);
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
