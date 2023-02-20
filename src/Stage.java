public class Stage {

    // Stage 1: Only sending buffer
    public Stage(Integer nStage, Integer nProcesses, Integer nProducts, Buffer bufferSends) {
        if (nStage != 1)
            throw new IllegalArgumentException("La etapa debe crearse con buffer del que recibe o ser la primera etapa");
        
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
        
        new Process(nStage, "naranja", nProducts, bufferReceives, bufferSends).start();
        for (int i = 0;  i < nProcesses-1; i++) {
            new Process(nStage, "azul", nProducts, bufferReceives, bufferSends).start();
        }
    }

}
