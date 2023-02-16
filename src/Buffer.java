import java.util.Queue;
import java.util.LinkedList;

public class Buffer {

    // Buffer's number
    private int number;

    // Buffer's capacity
    private int capacity;

    private String name;

    // Queue of Products stored in buffer
    private Queue<Product> products = new LinkedList<Product>();

    // Intermidiate buffer: number 1 or 2
    public Buffer(Integer number, Integer capacity) {
        if (number == 3) {
            throw new IllegalArgumentException(
                    "El tercer buffer tiene capacidad inifinita, no ponga capacidad en el constructor");
        }
        this.number = number;
        this.capacity = capacity;
        this.name = "Buffer " + number;
    }

    // Final buffer: number 3
    public Buffer(Integer number) {
        if (number != 3) {
            throw new IllegalArgumentException("El buffer debe crearse con capacidad o ser el tercer buffer");
        }
        this.number = number;
        this.name = "Buffer " + number + "(final)";
    }

    public synchronized boolean isFull() {
        if (number == 3) {
            return false;
        }
        return this.products.size() == this.capacity;
    }

    public synchronized boolean isEmpty() {
        return this.products.size() == 0;
    }

    public synchronized Product give() {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Product send = products.remove();
        notifyAll(); 
        System.out.println("Se envió el producto " + send.getId() + " del " + this.name + ".");
        return send;
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
        System.out.println("Se dejó el producto " + product.getId() + " en el " + this.name + ".");
        notifyAll();
    }

    public String getName() {
        return name;
    }

}
