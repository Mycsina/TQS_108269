package org.example.busmanager;


import org.example.busmanager.entity.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CurrencyRequester {

    private final Environment env;

    @Autowired
    public CurrencyRequester(Environment env) {
        this.env = env;
    }

    @Cacheable(value = "currencyCache")
    public CurrencyResponse getLatestCurrencyRates() {
        WebClient client = WebClient.create();
        String base = "https://openexchangerates.org/api/latest.json";
        String api_key = env.getProperty("open-exchange-rates.app-id");
        return client
                .get()
                .uri(base + "?app_id=" + api_key)
                .retrieve()
                .bodyToMono(CurrencyResponse.class)
                .block();
    }

}
