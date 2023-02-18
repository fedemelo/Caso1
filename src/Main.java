import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static int bufferSize;
    private static int nProducts;

    private static String input(String message) {
        try {
            System.out.print(message);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error leyendo de la consola.");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {

        Integer nProcesses = Integer.parseInt(
                input("\nIngrese el número total de procesos.\nSerá el mismo para las 3 etapas de producción.\n> "));
        System.out.println("Habrá 1 proceso naranja y " + (nProcesses-1) + " procesos azules.\n");

        nProducts = Integer.parseInt(input("Ingrese la cantidad de productos que cada proceso va a crear.\n> "));

        // Los buzones y el objeto identificador serán creados en el programa principal.
        bufferSize = Integer
                .parseInt(input("\nIngrese el tamaño de los primeros 2 buffers.\n> "));
        System.out.println("Los buffers 1 y 2 tendrán tamaño de " + bufferSize + "; el 3 será ilimitado.\n");
        Buffer buffer1 = new Buffer(1, bufferSize);
        Buffer buffer2 = new Buffer(2, bufferSize);
        Buffer finalBuffer = new Buffer(3);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * Stage 1: En la primera etapa se crea el producto y se le asigna un número
         * consecutivo para su identificación (con la ayuda de un objeto que asigna este
         * consecutivo)
         */
        new Stage(1, nProcesses, nProducts, buffer1);
        /*
         * En la segunda y tercera etapa se transforman
         * para lograr el producto final y se termina la producción.
         */
        new Stage(2, nProcesses, nProducts, buffer1, buffer2);
        new Stage(3, nProcesses, nProducts, buffer2, finalBuffer);

        /*
         * El proceso rojo es el encargado de terminar la producción. Se encarga de
         * recoger los productos terminados y de imprimirlos en orden.
         */
        // Seleep thread for two seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Process finalProcess = new Process("rojo", nProcesses, nProducts, finalBuffer);
        finalProcess.start();
    }

}
