package org.example.busmanager;

import org.example.busmanager.entity.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyRequester {

    private final Environment env;
    private final RestTemplate restTemplate;

    @Autowired
    public CurrencyRequester(Environment env, RestTemplate restTemplate) {
        this.env = env;
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "currency")
    public CurrencyResponse getLatestCurrencyRates() {
        String base = "https://openexchangerates.org/api/latest.json";
        String api_key = env.getProperty("open-exchange-rates.app-id");
        return restTemplate.getForObject(base + "?app_id=" + api_key, CurrencyResponse.class);
    }
}
