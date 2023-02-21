public class Product {
    private String color;
    private Integer id;
    private String message;

    public Product(String color) {
        this.color = color;
        this.message = "";
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

    public String addToMessage(String message) {
        return this.message += message;
    }

    public String getMessage() {
        return message;
    }

}
