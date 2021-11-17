package com.peter.valutaomregner;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.peter.valutaomregner.Interfaces.RatesCurrencyConvertable;
import com.peter.valutaomregner.Models.Rate;

import java.util.HashMap;
import java.util.Map;

public class RatesCurrencyCaller implements  Response.Listener, Response.ErrorListener{

    RatesCurrencyConvertable responseMethod;

    public RatesCurrencyCaller(RatesCurrencyConvertable _rm){
        responseMethod = _rm;
    }

    public void createCall(Context context, String from){
        String url = "https://api.m3o.com/v1/currency/Rates?code="+from;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url, null, this, this){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer OTYxNzViZDAtYmMxZi00NzRkLTg2ZDAtNmE1NzBmZjc1MDUx");

                return headers;
            }
        };

        queue.add(jsonObjectRequest2);
    }

    @Override
    public void onResponse(Object response) {
        Gson gson = new Gson();
        Rate rate = gson.fromJson(response.toString(), Rate.class);

        responseMethod.ratesCallResponse(rate);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }


}
