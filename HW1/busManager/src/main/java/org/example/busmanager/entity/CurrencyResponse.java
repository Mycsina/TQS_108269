package org.example.busmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrencyResponse (String disclaimer, String license, Long timestamp, String base, List<Rate> rates) {
    @Data
    public static class Rate {
        private String currency;
        private Double rate;
    }
}
