package org.example.busmanager.service;

import org.example.busmanager.CurrencyRequester;
import org.example.busmanager.entity.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    private final CurrencyRequester requester;

    @Autowired
    public CurrencyService(CurrencyRequester requester) {
        this.requester = requester;
    }

    public double convertCurrency(double amount, String from, String to) {
        CurrencyResponse currencyResponse = requester.getLatestCurrencyRates();
        double fromRate = currencyResponse.rates().stream()
                .filter(rate -> rate.currency().equals(from))
                .mapToDouble(CurrencyResponse.Rate::rate)
                .findFirst()
                .orElseThrow();
        double toRate = currencyResponse.rates().stream()
                .filter(rate -> rate.currency().equals(to))
                .mapToDouble(CurrencyResponse.Rate::rate)
                .findFirst()
                .orElseThrow();
        return amount * toRate / fromRate;
    }
}
