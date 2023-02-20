import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Process extends Thread {

    private String color;
    private Integer nProducts;
    private Integer nProcesses;
    private Integer nStage;
    private Buffer sendingBuffer;
    private Buffer receivingBuffer;
    private String name;

    // Stage 1: No receiving buffer
    public Process(Integer nStage, String color, Integer nProducts,
            Buffer receivingBuffer) {
        if (nStage != 1)
            throw new IllegalArgumentException(
                    "El proceso debe crearse con buffer del que recibe o ser de la primera etapa");
        this.nStage = nStage;
        this.color = color;
        this.nProducts = nProducts;
        this.receivingBuffer = receivingBuffer;
        this.name = "Proceso " + color + " (etapa " + nStage + ")";
    }

    // Stage 2 and 3: Receiving and sending buffers
    public Process(Integer nStage, String color, Integer nProducts, Buffer sendingBuffer,
            Buffer receivingBuffer) {
        if (nStage == 1)
            throw new IllegalArgumentException("Los procesos de la etapa 1 no deben tener buffer del que reciben");
        this.nStage = nStage;
        this.color = color;
        this.nProducts = nProducts;
        this.sendingBuffer = sendingBuffer;
        this.receivingBuffer = receivingBuffer;
        this.name = "Proceso " + color + " (etapa " + nStage + ")";
    }

    // Red process
    public Process(String color, Integer nProcesses, Integer nProducts, Buffer sendingBuffer) {
        if (color != "rojo")
            throw new IllegalArgumentException("Todo proceso salvo el rojo debe pertenecer a una etapa.");
        this.color = "rojo";
        this.nProducts = nProducts;
        this.nProcesses = nProcesses;
        this.sendingBuffer = sendingBuffer;
        this.name = "Proceso " + color + " (final) ";
    }

    public void run() {
        if (color == "rojo")
            redProcess();
        else {
            switch (nStage) {
                case 1:
                    if (this.color == "azul")
                        stage1BlueProcess();
                    else
                        stage1OrangeProcess();
                    break;
                case 2:
                case 3:
                    if (this.color == "azul")
                        stage2or3BlueProcess();
                    else
                        stage2or3OrangeProcess();
                    break;
            }
        }
    }

    // Blue
    private void stage1BlueProcess() {
        for (int i = 0; i < nProducts; i++) {
            Product product = new Product(color);
            IdProvider.getInstance().giveId(product);

            aMimir();

            String msg = String.format("%s creó %s y lo envía a %s.", this.name, product.getName(),
                    receivingBuffer.getName());
            System.out.println(msg);

            receivingBuffer.receiveBlue(product);
        }
    }

    private void stage2or3BlueProcess() {
        for (int i = 0; i < nProducts; i++) {
            Product product = sendingBuffer.sendBlue();

            aMimir();

            String msg = String.format("%s recibió %s de %s y lo transformó%s.", this.name, product.getName(),
                    sendingBuffer.getName(), (nStage == 3 ? " nuevamente" : ""));
            System.out.println(msg);

            receivingBuffer.receiveBlue(product);
        }
    }

    // Orange
    private void stage1OrangeProcess() {
        for (int i = 0; i < nProducts; i++) {
            Product product = new Product(color);
            IdProvider.getInstance().giveId(product);

            aMimir();

            String msg = String.format("%s creó %s y lo envía a %s.", this.name, product.getName(),
                    receivingBuffer.getName());
            System.out.println(msg);

            while (receivingBuffer.orangeIsFull())
                Thread.yield();
            receivingBuffer.receiveOrange(product);

            synchronized (receivingBuffer) {
                receivingBuffer.notifyAll();
            }
        }
    }

    private void stage2or3OrangeProcess() {
        for (int i = 0; i < nProducts; i++) {
            while (sendingBuffer.orangeIsEmpty())
                Thread.yield();
            Product product = sendingBuffer.sendOrange();
            synchronized (sendingBuffer) {
                sendingBuffer.notifyAll();
            }

            aMimir();

            String msg = String.format("%s recibió %s de %s y lo transformó%s.", this.name, product.getName(),
                    sendingBuffer.getName(), (nStage == 3 ? " nuevamente" : ""));
            System.out.println(msg);

            while (receivingBuffer.orangeIsFull())
                Thread.yield();
            receivingBuffer.receiveOrange(product);
            synchronized (receivingBuffer) {
                receivingBuffer.notifyAll();
            }
        }
    }

    private void aMimir() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(50, 500 + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void redProcess() {

        Integer i = 0;

        while (i < nProcesses * nProducts) {
            Product product = sendingBuffer.giveProduct(i);
            while (product == null) {
                Thread.yield();
                product = sendingBuffer.giveProduct(i);
            }
            System.out.println(product.getName());
            i++;
        }
    }

}