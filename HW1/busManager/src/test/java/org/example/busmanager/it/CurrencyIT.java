package org.example.busmanager.it;

import org.example.busmanager.CurrencyRequester;
import org.example.busmanager.entity.CurrencyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class CurrencyIT {
    @Autowired
    private CurrencyRequester currencyRequester;

    @Test
    void whenGetLatestCurrencyRates_thenReturnRates() {
        CurrencyResponse currencyResponse = currencyRequester.getLatestCurrencyRates();
        assertThat(currencyResponse.base(), is(equalTo("USD")));
    }

    @Test
    void whenTwiceGetLatestCurrencyRates_thenVerifyCacheIsUsed() {
        CurrencyResponse currencyResponse = currencyRequester.getLatestCurrencyRates();
        CurrencyResponse currencyResponse2 = currencyRequester.getLatestCurrencyRates();
        assertThat(currencyResponse, is(equalTo(currencyResponse2)));
    }
}
