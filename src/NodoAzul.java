// import java.util.concurrent.ThreadLocalRandom;

// public class NodoAzul extends Thread{

// 	//Id del nodo
// 	private int id;
	
// 	//Etapa del nodo
// 	private int etapa;
	
// 	//Buffer de salida
// 	private IntermidiateBuffer bufferIntermedio;
// 	private Buffer bufferFinal;
	
// 	//Buffer de entrada
// 	private IntermidiateBuffer bufferEntrada;
	
// 	//Mensajes que se deben crear
// 	private int cantidad;
	
	
// 	public NodoAzul(int etapa, IntermidiateBuffer bufferEntrada, IntermidiateBuffer bufferIntermedio, Buffer bufferFinal, int id, int cantidad) {
// 		this.etapa = etapa;
// 		this.id = id;
// 		this.bufferEntrada = bufferEntrada;
// 		this.bufferIntermedio = bufferIntermedio;
// 		this.bufferFinal = bufferFinal;
// 		this.cantidad = cantidad;
// 	}
	
	
// 	@Override
// 	public void run() {
// 		if(etapa == 1) {
// 			for(int i = 1; i <= cantidad; i++) {
// 				bufferIntermedio.receive("P" + i);
// 			}
// 		} else if(etapa == 2) {
// 			for(int i = 1; i <= cantidad; i++) {
// 				String producto = bufferEntrada.give();
// 				//Transformar producto
// 				try {
// 					Thread.sleep(ThreadLocalRandom.current().nextInt(50, 500));
// 				} catch (InterruptedException e) {
// 					e.printStackTrace();
// 				}
// 				bufferIntermedio.receive(producto);
// 			}
// 		} else if(etapa == 3) {
// 			for(int i = 1; i <= cantidad; i++) {
// 				String producto = bufferEntrada.give();
// 				//Transformar producto
// 				try {
// 					Thread.sleep(ThreadLocalRandom.current().nextInt(50, 500));
// 				} catch (InterruptedException e) {
// 					e.printStackTrace();
// 				}
// 				bufferFinal.receive(producto);
// 			}
// 		}
// 	}
// }
