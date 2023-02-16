public class Stage {

    // private Integer nStage;
    // private Buffer bufferReceives;
    // private Buffer bufferSends;
    // private Integer nProcesses;
    // private Integer nProducts;
    // Stage 1: No receiving buffer
    public Stage(Integer nStage, Integer nProcesses, Integer nProducts, Buffer bufferSends) {
        if (nStage != 1)
            throw new IllegalArgumentException("La etapa debe crearse con buffer del que recibe o ser la primera etapa");
        // this.nStage = nStage;
        // this.nProcesses = nProcesses;
        // this.nProducts = nProducts; 
        // this.bufferSends = bufferSends;
        
        new Process(nStage, "naranja", nProducts, bufferSends).start();
        for (int i = 0; i < nProcesses-1; i++) {
            new Process(nStage, "azul", nProducts, bufferSends).start();
        }
    }

    // Stage 2 and 3: Receiving and sending buffers
    public Stage(Integer nStage, Integer nProcesses, Integer nProducts,
            Buffer bufferReceives, Buffer bufferSends) {
        if (nStage == 1)
            throw new IllegalArgumentException("La primera etapa no debe tener buffer del que recibe");
        // this.nStage = nStage;
        // this.nProcesses = nProcesses;
        // this.nProducts = nProducts;
        // this.bufferReceives = bufferReceives;
        // this.bufferSends = bufferSends;
        
        new Process(nStage, "naranja", nProducts, bufferReceives, bufferSends).start();
        for (int i = 0;  i < nProcesses-1; i++) {
            new Process(nStage, "azul", nProducts, bufferReceives, bufferSends).start();
        }
    }

}
