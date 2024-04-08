package org.example.busmanager.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = CurrencyResponseDeserializer.class)
public record CurrencyResponse(String disclaimer, String license, Long timestamp, String base, List<Rate> rates) {
    @Accessors(chain = true)
    @Data
    @AllArgsConstructor
    public static class Rate {
        private String currency;
        private Double ratio;
    }
}

class CurrencyResponseDeserializer extends StdDeserializer<CurrencyResponse> {
    public CurrencyResponseDeserializer() {
        this(null);
    }

    public CurrencyResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CurrencyResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String disclaimer = node.get("disclaimer").asText();
        String license = node.get("license").asText();
        Long timestamp = node.get("timestamp").asLong();
        String base = node.get("base").asText();
        List<CurrencyResponse.Rate> rates = new ArrayList<>();
        JsonNode ratesNode = node.get("rates");
        // rates is a map string -> double, parse it
        ratesNode.fields().forEachRemaining(entry -> {
            String currency = entry.getKey();
            Double rate = entry.getValue().asDouble();
            rates.add(new CurrencyResponse.Rate(currency, rate));
        });
        return new CurrencyResponse(disclaimer, license, timestamp, base, rates);

    }
}