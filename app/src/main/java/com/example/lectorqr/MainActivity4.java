package com.example.lectorqr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        new IntentIntegrator(this).initiateScan();


    }

    public void volleyPost(String device_id, String recording_write){
        String postUrl = "http://759c1ed65377.ngrok.io/qr/qr-recordings/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("device_id", device_id);
            postData.put("recording_write", recording_write);
            /*postData.put("user_write", "1");*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }


    public void volleyGet(String code ,String device_id){

        String url = "http://759c1ed65377.ngrok.io/qr/qr-codes/code/" + code + "/";
        List<String> jsonResponses = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    String id_qrcode = jsonObject.getString("id");
                    jsonResponses.add(id_qrcode);
                    volleyPost(device_id, id_qrcode);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);


    }





/*
    //funciones de botones
    public void irAweb(String url, String postData){
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.postUrl(url, postData.getBytes());
    }

    public void getUrlData(String url, String code){
        WebView webview = new WebView(this);
        setContentView(webview);
        String url_data = url + code;
        webview.getUrl(url_data);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        String recording = result.getContents();
        String device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //String url;
        //String postData;

        volleyGet(recording, device_id);
        //volleyPost(device_id, id_recording);

        /*url = "https://8e5847e08103.ngrok.io/qr/qr-recordings/" ;
        postData = "unico_code="+ datos + "&device_id="+id+"&recording_write=1";
        irAweb(url, postData);*/
    }


}