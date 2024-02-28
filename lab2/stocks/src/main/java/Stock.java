public class Stock {
    private final String label;
    private Integer quantity;

    public Stock(String label, Integer quantity) {
        this.label = label;
        this.quantity = quantity;
    }

    public String getLabel() {
        return label;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}
