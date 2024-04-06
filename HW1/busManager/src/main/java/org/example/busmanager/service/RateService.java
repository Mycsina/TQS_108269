package org.example.busmanager.service;

import org.example.busmanager.entity.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RateService {

    private final Environment env;
    private final RestTemplate restTemplate;

    @Autowired
    public RateService(Environment env, RestTemplate restTemplate) {
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
}
