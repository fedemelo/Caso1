import java.util.Queue;
import java.util.LinkedList;

public class Buffer {

    private int capacity;
    private String name;
    private boolean isFinal;

    // Queue of Products stored in buffer
    private Queue<Product> blueProducts = new LinkedList<Product>();
    private Queue<Product> orangeProducts = new LinkedList<Product>();
    private Queue<Product> finalProducts = new LinkedList<Product>();

    // Intermidiate buffer: number 1 or 2
    public Buffer(Integer number, Integer capacity) {
        if (number == 3) {
            throw new IllegalArgumentException(
                    "El tercer buffer tiene capacidad inifinita, no ponga capacidad en el constructor");
        }
        this.capacity = capacity;
        this.name = "Buffer " + number;
        this.isFinal = false;
    }

    // Final buffer: number 3
    public Buffer(Integer number) {
        if (number != 3) {
            throw new IllegalArgumentException("El buffer debe crearse con capacidad o ser el tercer buffer");
        }
        this.name = "Buffer " + number + "(final)";
        this.isFinal = true;
    }

    private synchronized boolean blueIsFull() {
        if (isFinal)
            return false;
        return this.blueProducts.size() == this.capacity;
    }

    private synchronized boolean blueIsEmpty() {
        return this.blueProducts.size() == 0;
    }

    public synchronized boolean orangeIsFull() {
        if (isFinal)
            return false;
        return this.orangeProducts.size() == this.capacity;
    }

    public synchronized boolean orangeIsEmpty() {
        return this.orangeProducts.size() == 0;
    }


    // Send blue product
    public synchronized Product sendBlue() {

        while (blueIsEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Product sentProduct = blueProducts.remove();

        notifyAll();

        // System.out.println(String.format("Se envió %s de %s.", sentProduct.getName(),
        // this.name));

        return sentProduct;
    }

    public synchronized void receiveBlue(Product product) {

        while (blueIsFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!isFinal)
            blueProducts.add(product);
        else
            finalProducts.add(product);

        // System.out.println(String.format("Se recibió %s en %s.", product.getName(),
        // this.name));

        notifyAll();
    }

    // Send orange product
    public synchronized Product sendOrange() {

        Product sentProduct = orangeProducts.remove();
        return sentProduct;
    }

    public synchronized void receiveOrange(Product product) {
        if (!isFinal)
            orangeProducts.add(product);
        else
            finalProducts.add(product);
    }

    public String getName() {
        return name;
    }

    public synchronized Product giveProduct(Integer id) {
        for (Product product : finalProducts) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

}
