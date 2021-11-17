package com.peter.valutaomregner.Interfaces;

import com.peter.valutaomregner.Models.Currency;

public interface ExchangeRateCurrencyListener {
    public void update(Currency currency);
    public void errorUpdate(String errorMessage);
}
