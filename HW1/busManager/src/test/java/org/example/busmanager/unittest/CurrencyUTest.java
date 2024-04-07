package org.example.busmanager.unittest;

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
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyUTest {
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
}
