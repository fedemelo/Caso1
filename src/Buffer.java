import java.util.Queue;
import java.util.LinkedList;

public class Buffer {

    private int capacity;
    private String name;
    private boolean isFinal;

    // Queue of Products stored in buffer
    private Queue<Product> products = new LinkedList<Product>();

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

    public synchronized boolean isFull() {
        if (isFinal)
            return false;
        return this.products.size() == this.capacity;
    }

    public synchronized boolean isEmpty() {
        return this.products.size() == 0;
    }

    public synchronized Product send() {

        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Product sentProduct = products.remove();
        notifyAll();

        // System.out.println(String.format("Se envió %s de %s.", sentProduct.getName(),
        // this.name));

        return sentProduct;
    }

    public synchronized void receive(Product product) {

        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        products.add(product);

        // System.out.println(String.format("Se recibió %s en %s.", product.getName(),
        // this.name));

        notifyAll();
    }

    public String getName() {
        return name;
    }

}
