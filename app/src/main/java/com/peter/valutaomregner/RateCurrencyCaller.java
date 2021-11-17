package com.peter.valutaomregner;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.peter.valutaomregner.Interfaces.RateCurrencyConvertable;
import com.peter.valutaomregner.Models.Currency;

public class RateCurrencyCaller implements  Response.Listener, Response.ErrorListener {

    RateCurrencyConvertable responseMethod;

    public RateCurrencyCaller(RateCurrencyConvertable _rm){
        responseMethod = _rm;
    }

    public void createCall(Context context, String from, String to){
        String url = "https://free.currconv.com/api/v7/convert?q="+from+"_"+to+"&compact=ultra&apiKey=8565d080c4603e65f6d6";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(Object response) {
        try {
            Currency currency = new Currency();
            String resp = response.toString();

            String[] values = resp.replace("{", "").replace("}", "").replace("\"", "").split(":");

            currency.from = values[0].split("_")[0];
            currency.to = values[0].split("_")[1];
            currency.exchangeRate = Double.parseDouble(values[1]);

            responseMethod.rateCallResponse(currency);
        } catch (Exception e) {
            Log.e("Repsonse", "E: " + e.toString());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Repsonse", "obj: " + error.toString());
        //for (ValutaApiWatchable watcher : apiListeners){
        //    watcher.errorUpdate(error.getMessage());
        //}
    }
}
