package org.example.busmanager.service;

import org.example.busmanager.CurrencyRequester;
import org.example.busmanager.entity.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
                .filter(rate -> rate.getCurrency().equals(from))
                .mapToDouble(CurrencyResponse.Rate::getRate)
                .findFirst()
                .orElseThrow();
        double toRate = currencyResponse.rates().stream()
                .filter(rate -> rate.getCurrency().equals(to))
                .mapToDouble(CurrencyResponse.Rate::getRate)
                .findFirst()
                .orElseThrow();
        return amount * toRate / fromRate;
    }

    public Collection<CurrencyResponse.Rate> getAvailableCurrencies() {
        CurrencyResponse currencyResponse = requester.getLatestCurrencyRates();
        return currencyResponse.rates();
    }
}
