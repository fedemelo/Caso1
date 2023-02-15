import java.util.concurrent.ThreadLocalRandom;

public class NodoNaranja extends Thread{

	//Id del nodo
	private int id;
	
	//Etapa del nodo
	private int etapa;
	
	//Buffer de salida
	private BufferIntermedio bufferIntermedio;
	private BufferFinal bufferFinal;
	
	//Buffer de entrada
	private BufferIntermedio bufferEntrada;
	
	//Mensajes que se deben crear
	private int cantidad;
	
	
	public NodoNaranja(int etapa, BufferIntermedio bufferEntrada, BufferIntermedio bufferIntermedio, BufferFinal bufferFinal, int id, int cantidad) {
		this.etapa = etapa;
		this.id = id;
		this.bufferEntrada = bufferEntrada;
		this.bufferIntermedio = bufferIntermedio;
		this.bufferFinal = bufferFinal;
		this.cantidad = cantidad;
	}
	
	
	@Override
	public void run() {
		if(etapa == 1) {
			for(int i = 1; i <= cantidad; i++) {
				while(bufferIntermedio.isFull()) 
					Thread.yield();
				bufferIntermedio.recibir("P" + i);
			}
		} else if(etapa == 2) {
			for(int i = 1; i <= cantidad; i++) {
				while(bufferEntrada.isEmpty())
					Thread.yield();
				//Transformar producto
				String producto = bufferEntrada.enviar();
				try {
					Thread.sleep(ThreadLocalRandom.current().nextInt(50, 500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while(bufferIntermedio.isFull())
					Thread.yield();
				bufferIntermedio.recibir(producto);
			}
		} else if(etapa == 3) {
			for(int i = 1; i <= cantidad; i++) {
				while(bufferEntrada.isEmpty())
					Thread.yield();
				//Transformar producto
				String producto = bufferEntrada.enviar();
				try {
					Thread.sleep(ThreadLocalRandom.current().nextInt(50, 500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				bufferIntermedio.recibir(producto);
			}
		}
	}
}
