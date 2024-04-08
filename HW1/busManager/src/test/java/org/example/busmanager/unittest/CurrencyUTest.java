package org.example.busmanager.unittest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.busmanager.CurrencyRequester;
import org.example.busmanager.entity.CurrencyResponse;
import org.example.busmanager.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyUTest {
    @Mock(strictness = Mock.Strictness.LENIENT)
    private CurrencyRequester currencyRequester;
    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    public void setUp() {
        CurrencyResponse.Rate usd = new CurrencyResponse.Rate("USD", 1.0);
        CurrencyResponse.Rate eur = new CurrencyResponse.Rate("EUR", 0.85);
        CurrencyResponse.Rate gbp = new CurrencyResponse.Rate("GBP", 0.75);
        List<CurrencyResponse.Rate> rates = List.of(usd, eur, gbp);
        CurrencyResponse currencyResponse = new CurrencyResponse("", "", 0L, "USD", rates);

        when(currencyRequester.getLatestCurrencyRates()).thenReturn(currencyResponse);
    }

    @Test
    void whenConvertCurrency_thenReturnConvertedCurrency() {
        double converted = currencyService.convertCurrency(100.0, "USD", "EUR");
        assertThat(converted, is(equalTo(85.0)));
    }

    @Test
    void whenConvertSameCurrency_thenReturnSameAmount() {
        double converted = currencyService.convertCurrency(100.0, "USD", "USD");
        assertThat(converted, is(equalTo(100.0)));
    }

    @Test
    void whenConvertInvalidCurrency_thenThrowException() {
        try {
            currencyService.convertCurrency(100.0, "USD", "INVALID");
        } catch (NoSuchElementException e) {
            assertThat(e.getMessage(), is(equalTo("No value present")));
        }
    }

    @Test
    void whenGivenResponse_thenDeserialize() throws JsonProcessingException {
        String response = """
                {
                    "disclaimer": "Usage subject to terms: https://openexchangerates.org/terms",
                        "license": "https://openexchangerates.org/license",
                        "timestamp": 1712595600,
                        "base": "USD",
                        "rates": {
                            "BTC": 0.000013933904,
                            "EUR": 0.921648,
                            "GBP": 0.790618,
                            "USD": 1
                    }
                    }""";
        ObjectMapper objectMapper = new ObjectMapper();
        CurrencyResponse parse = objectMapper.readValue(response, CurrencyResponse.class);
        assertThat(parse.base(), is(equalTo("USD")));
        assertThat(parse.rates().size(), is(equalTo(4)));
        Map<String, Double> rates = parse.rates().stream().collect(Collectors.toMap(CurrencyResponse.Rate::getCurrency, CurrencyResponse.Rate::getRatio));
        assertThat(rates.get("EUR"), is(equalTo(0.921648)));
        assertThat(rates.get("GBP"), is(equalTo(0.790618)));
        assertThat(rates.get("USD"), is(equalTo(1.0)));
        assertThat(rates.get("BTC"), is(equalTo(0.000013933904)));
    }

    @Test
    void whenGivenInvalidResponse_thenThrowException() {
        String response = """
                {
                        "license": "https://openexchangerates.org/license",
                        "timestamp": 1712595600,
                        "base": "USD",
                        "rates": {
                            "BTC": 0.000013933904,
                            "EUR": 0.921648,
                            "GBP": 0.790618,
                            "USD": 1
                    }
                    }""";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue(response, CurrencyResponse.class);
            assertThat(true, is(equalTo(false)));
        } catch (NullPointerException | JsonProcessingException e) {
            assertThat(true, is(equalTo(true)));
        }
    }
}
