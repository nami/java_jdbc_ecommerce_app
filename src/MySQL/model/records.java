package MySQL.model;

public class records {

    private int id;
    private int product_id;
    private int platform_id;
    private int prices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(int platform_id) {
        this.platform_id = platform_id;
    }

    public int getPrices() {
        return prices;
    }

    public void setPrices(int prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "prices_products_platforms_access{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", platform_id=" + platform_id +
                ", prices=" + prices +
                '}';
    }
}
