package com.peter.valutaomregner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.valutaomregner.Interfaces.ExchangeRatesCurrencyListener;
import com.peter.valutaomregner.Interfaces.ExchangeRateCurrencyListener;
import com.peter.valutaomregner.Models.Currency;
import com.peter.valutaomregner.Models.CurrencyEnum;
import com.peter.valutaomregner.Models.Rate;

public class MainActivity extends AppCompatActivity implements ExchangeRateCurrencyListener, ExchangeRatesCurrencyListener {
    CurrencyController caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getValuesBtn = findViewById(R.id.getvalues_btn);
        getValuesBtn.setOnClickListener(this::getValues);

        caller = new CurrencyController();
        caller.addRateListener(this);
        //caller.addRatesListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, CurrencyEnum.values());

        Spinner dropdownFrom = findViewById(R.id.drop_from);
        Spinner dropdownTo = findViewById(R.id.drop_to);

        dropdownFrom.setAdapter(adapter);
        dropdownTo.setAdapter(adapter);

        CurrencyEnum[] currencyEnums = CurrencyEnum.values();

        int dkkPos = 0;
        int usdPOS = 0;

        for(int i = 0; i < currencyEnums.length; i++){
            if(currencyEnums[i].toString() == "DKK")
                dkkPos = i;
            if(currencyEnums[i].toString() == "USD")
                usdPOS = i;
        }

        dropdownFrom.setSelection(dkkPos);
        dropdownTo.setSelection(usdPOS);
    }


    public  void getValues(View v){

        Spinner dropdownTo = findViewById(R.id.drop_to);
        String to = dropdownTo.getSelectedItem().toString();


        Spinner dropdownFrom = findViewById(R.id.drop_from);
        String from = dropdownFrom.getSelectedItem().toString();

        TextView number = findViewById(R.id.numbervalue_number);

        //Log.e("Currency", "text: " + number.getText().toString());

        if(number.getText().toString().isEmpty()){

            //Log.e("Currency", "true: " + number.getText().toString());
            Toast.makeText(this,"Number cannot be empty", Toast.LENGTH_SHORT).show();

            return;
        }

        caller.getExchangeRate(MainActivity.this, from, to);

        // Use this to get multiple rates
        //caller.getExchangeRates(MainActivity.this, from);
    }

    @Override
    public void update(Currency currency) {
        //Log.e("Currency", "From: " +currency.from);
        //Log.e("Currency","To: " + currency.to);
        //Log.e("Currency", "Ex: " + currency.exchangeRate);

        TextView number = findViewById(R.id.numbervalue_number);
        TextView exText = findViewById(R.id.exchanged_text);
        TextView exRateText = findViewById(R.id.exchanged_rate_text);


        Integer i = Integer.parseInt(number.getText().toString());
        Double val = i * currency.exchangeRate;

        exText.setText("Exchanged is: " +  String.format("%.3f", val));
        exRateText.setText("Exchanged rate is: " + currency.exchangeRate);

        Log.e("Currency: ", "Exchange rate: " + currency.exchangeRate);
    }

    @Override
    public void update(Rate rate) {
        Log.e("Rate: ", "Amount of rates: " + rate.rates.size());
    }

    @Override
    public void errorUpdate(String errorMessage) {
        Log.e("Currency", "Error: " + errorMessage);
    }
}