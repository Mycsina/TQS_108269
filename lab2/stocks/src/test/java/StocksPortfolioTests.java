import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTests {

    @Mock
    IStockmarketService stockMarket;

    @Test
    public void testGetStock() {
        when(stockMarket.lookUpPrice(any(String.class))).thenReturn(100.0);
        assertEquals(100.0, stockMarket.lookUpPrice("AAPL"));
        verify(stockMarket, times(1)).lookUpPrice("AAPL");
    }

    @Test
    public void testHamcrestGetStock() {
        when(stockMarket.lookUpPrice(any(String.class))).thenReturn(100.0);
        assertThat(stockMarket.lookUpPrice("AAPL"), equalTo(100.0));
        verify(stockMarket, times(1)).lookUpPrice("AAPL");
    }

    @Test
    public void testGetTotalValue() {
        IStockmarketService stockMarket = mock(IStockmarketService.class);
        StocksPortfolio portfolio = new StocksPortfolio(stockMarket);
        Stock stock = new Stock("AAPL", 2);
        Stock stock2 = new Stock("GOOGL", 1);
        portfolio.addStock(stock);
        portfolio.addStock(stock2);
        when(stockMarket.lookUpPrice("AAPL")).thenReturn(100.0);
        when(stockMarket.lookUpPrice("GOOGL")).thenReturn(50.0);
        double result = portfolio.getTotalValue();
        assertEquals(250.0, result);
        verify(stockMarket, times(1)).lookUpPrice("AAPL");
        verify(stockMarket, times(1)).lookUpPrice("GOOGL");
    }

    @Test
    public void testHamcrestGetTotalValue() {
        IStockmarketService stockMarket = mock(IStockmarketService.class);
        StocksPortfolio portfolio = new StocksPortfolio(stockMarket);
        Stock stock = new Stock("AAPL", 2);
        Stock stock2 = new Stock("GOOGL", 1);
        portfolio.addStock(stock);
        portfolio.addStock(stock2);
        when(stockMarket.lookUpPrice("AAPL")).thenReturn(100.0);
        when(stockMarket.lookUpPrice("GOOGL")).thenReturn(50.0);
        assertThat(portfolio.getTotalValue(), equalTo(250.0));
        verify(stockMarket, times(1)).lookUpPrice("AAPL");
        verify(stockMarket, times(1)).lookUpPrice("GOOGL");
    }
}
