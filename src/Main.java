import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {

	
	//Cantidad de procesos Azules
	private static int cantProcesos;
	
	//Tamaño Buffer
	private static int tamBuffer;
	
	//Numero de productos creados por proceso en la etapa 1
	private static int cantProductos;
	
	//Cantidad de prodcutos
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Ingrese la cantidad de procesos en cada nivel: ");
		cantProcesos = Integer.parseInt(br.readLine());
		
		System.out.println("Ingrese la cantidad de productos que cada proceso va a crear: ");
		cantProductos = Integer.parseInt(br.readLine());
		
		System.out.println("Ingrese el tamaño de los buffers: ");
		tamBuffer = Integer.parseInt(br.readLine());
		
		ArrayList<BufferIntermedio> buffers = new ArrayList<BufferIntermedio>();
		for(int i = 1; i <= 2; i++) {
			buffers.add(new BufferIntermedio(tamBuffer, i));
		}
		
		BufferFinal buffFinal = new BufferFinal("F");
		
	}
}
