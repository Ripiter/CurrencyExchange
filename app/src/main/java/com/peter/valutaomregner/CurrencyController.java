package com.peter.valutaomregner;

import android.content.Context;

import com.peter.valutaomregner.Interfaces.RatesCurrencyConvertable;
import com.peter.valutaomregner.Interfaces.ExchangeRatesCurrencyListener;
import com.peter.valutaomregner.Interfaces.RateCurrencyConvertable;
import com.peter.valutaomregner.Interfaces.ExchangeRateCurrencyListener;
import com.peter.valutaomregner.Models.Currency;
import com.peter.valutaomregner.Models.Rate;

import java.util.ArrayList;

public class CurrencyController implements RateCurrencyConvertable, RatesCurrencyConvertable {

    ArrayList<ExchangeRateCurrencyListener> apiSingleListeners = new ArrayList<>();
    ArrayList<ExchangeRatesCurrencyListener> apiRatesListeners = new ArrayList<>();


    public void addRateListener(ExchangeRateCurrencyListener watcher){
        apiSingleListeners.add(watcher);
    }

    public void removeRateListener(ExchangeRateCurrencyListener watcher){
        apiSingleListeners.remove(watcher);
    }

    public void addRatesListener(ExchangeRatesCurrencyListener watcher){
        apiRatesListeners.add(watcher);
    }

    public void removeRatesListener(ExchangeRatesCurrencyListener watcher){
        apiRatesListeners.remove(watcher);
    }

    /**
    *  Gets exchange rate
    * {@link #rateCallResponse(Currency)} Callback method
    *
    * @param  from The currency that we get rate from
    * @param  to The currency that we exchange to
    *
    * */
    public void getExchangeRate(Context context, String from, String to){

        RateCurrencyCaller call = new RateCurrencyCaller(this::rateCallResponse);
        call.createCall(context, from, to);
    }

    /**
     *  Gets exchange rates to other currencies
     * {@link #rateCallResponse(Currency)} Callback method
     *
     * @param  from The currency that we get rate from
     *
     * */
    public void getExchangeRates(Context context, String from){

        RatesCurrencyCaller ratesCurrencyCaller = new RatesCurrencyCaller(this::ratesCallResponse);
        ratesCurrencyCaller.createCall(context, from);
    }

    @Override
    public void rateCallResponse(Currency currency) {
        for (ExchangeRateCurrencyListener watcher : apiSingleListeners){
            watcher.update(currency);
        }
    }

    @Override
    public void ratesCallResponse(Rate rate) {
        for (ExchangeRatesCurrencyListener watcher : apiRatesListeners){
            watcher.update(rate);
        }
    }
}
