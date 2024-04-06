package org.example.busmanager;

import org.example.busmanager.entity.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Objects;

@EnableCaching
@SpringBootApplication
public class BusManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusManagerApplication.class, args);
    }

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driverClassName")));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @Cacheable("currency")
    public CurrencyResponse getLatestCurrencyRates(RestTemplate restTemplate) {
        String base = "https://openexchangerates.org/api/latest.json";
        String api_key = env.getProperty("open-exchange-rates.app-id");
        return restTemplate.getForObject(base + "?app_id=" + api_key, CurrencyResponse.class);
    }
}
