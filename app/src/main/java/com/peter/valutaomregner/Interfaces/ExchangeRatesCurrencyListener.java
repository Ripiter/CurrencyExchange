package com.peter.valutaomregner.Interfaces;

import com.peter.valutaomregner.Models.Rate;

public interface ExchangeRatesCurrencyListener {
    public void update(Rate rate);
    public void errorUpdate(String errorMessage);
}
