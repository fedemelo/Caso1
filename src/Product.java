public class Product {
    private String color;
    private Integer id;

    public Product(String color) {
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return String.format("Producto %s (%s)", id, color);
    }
}
