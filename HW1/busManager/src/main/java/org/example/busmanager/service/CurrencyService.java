package org.example.busmanager.service;

import org.example.busmanager.entity.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrencyService {

    private final Environment env;
    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyService(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Cacheable(value = "currency")
    public CurrencyResponse getLatestCurrencyRates() {
        String base = "https://openexchangerates.org/api/latest.json";
        String api_key = env.getProperty("open-exchange-rates.app-id");
        return restTemplate.getForObject(base + "?app_id=" + api_key, CurrencyResponse.class);
    }

    //https://www.baeldung.com/spring-invoke-cacheable-other-method-same-bean
    @Autowired
    private CurrencyService self;

    public double convertCurrency(double amount, String from, String to) {
        CurrencyResponse currencyResponse = self.getLatestCurrencyRates();
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
