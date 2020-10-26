public class CartItem {
    private final String cartId;
    private final String name;
    private final String picture;
    private final String description;
    private final int price;
    private final int number;
    private final double discount;

    public CartItem(String cartId,
                    String name,
                    String picture,
                    String description,
                    int price,
                    int number,
                    double discount) {
        this.cartId = cartId;
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.price = price;
        this.number = number;
        this.discount = discount;
    }

    public String getCartId() {
        return cartId;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public double getDiscount() {
        return discount;
    }
}
