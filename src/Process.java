public class Process extends Thread {

    private String color;
    private Integer nProducts;
    private Integer nStage;
    private Buffer bufferReceives;
    private Buffer bufferSends;
    private String name;

    // Stage 1: No receiving buffer
    public Process(Integer nStage, String color, Integer nProducts,
            Buffer bufferSends) {
        if (nStage != 1)
            throw new IllegalArgumentException(
                    "El proceso debe crearse con buffer del que recibe o ser de la primera etapa");
        this.nStage = nStage;
        this.color = color;
        this.nProducts = nProducts;
        this.bufferSends = bufferSends;
        this.name = "Proceso " + color + " de la etapa " + nStage;
    }

    // Stage 2 and 3: Receiving and sending buffers
    public Process(Integer nStage, String color, Integer nProducts, Buffer bufferReceives,
            Buffer bufferSends) {
        if (nStage == 1)
            throw new IllegalArgumentException("Los procesos de la etapa 1 no deben tener buffer del que reciben");
        this.nStage = nStage;
        this.color = color;
        this.nProducts = nProducts;
        this.bufferReceives = bufferReceives;
        this.bufferSends = bufferSends;
        this.name = "Proceso " + color + " de la etapa " + nStage;
    }

    // Red process
    public Process(String color, Integer nProducts, Integer nProcesses, Buffer bufferReceives) {
        if (color != "rojo")
            throw new IllegalArgumentException("Todo proceso salvo el rojo debe pertenecer a una etapa.");
        this.color = "rojo";
        this.nProducts = nProducts;
        this.bufferReceives = bufferReceives;
        this.name = "Proceso " + color + " (final) ";
    }

    public void run() {
        if (color == "rojo")
            redProcess();
        else {
            switch (nStage) {
                case 1:
                    stage1Process();
                    break;
                case 2:
                case 3:
                    stage2or3Process();
                    break;
            }
        }
    }

    /*
     * En la primera etapa se crea el producto y se le asigna un número consecutivo
     * para su
     * identificación (con la ayuda de un objeto que asigna este consecutivo).
     * 
     * Luego se deja el producto en un buffer intermedio.
     */
    private void stage1Process() {
        System.out.println("Inicia proceso " + color + " de la primera etapa de producción");
        for (int i = 0; i < nProducts; i++) {
            Product product = new Product(color);
            IdProvider.getInstance().giveId(product);
            System.out.println(this.name + " creó producto " + product.getId() + ". Se quiere dejar en el "
                    + bufferSends.getName() + ".");
            bufferSends.receive(product);
        }
    }

    /*
     * En la segunda y tercera etapa se transforman
     * para lograr el producto final y se termina la producción.
     */
    private void stage2or3Process() {
        switch (nStage) {
            case 2:
                System.out.println("Inicia proceso " + color + " de la segunda etapa de producción");
                break;
            case 3:
                System.out.println("Inicia proceso " + color + " de la tercera etapa de producción");
                break;
        }
        for (int i = 0; i < nProducts; i++) {
            Product product = bufferReceives.give();
            System.out.println(bufferReceives.getName() + " envió producto " + product.getId() + " y lo recibió " + this.name + ". Lo quiere dejar en el "
                    + bufferSends.getName() + ".");
            bufferSends.receive(product);
        }

        // if (nStage == 3)
        //     barrera.await();

    }


    private void redProcess() {
        // TODO: barrera!
        System.out.println("Inicia proceso " + color + " (último)");

        for (int i = 0; i < nProducts; i++) {
            Product product = bufferReceives.give();
            System.out.println(this.name + " recibió producto " + product.getId() + ". Se quiere dejar en el "
                    + bufferSends.getName() + ".");
            bufferSends.receive(product);
        }
    }

}