import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private final IStockmarketService stockmarket;
    private final List<Stock> stocks = new ArrayList<Stock>();

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double getTotalValue() {
        double value = 0;
        for (Stock stock : stocks) {
            value += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return value;
    }
}
