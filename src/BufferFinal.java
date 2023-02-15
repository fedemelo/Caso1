import java.util.LinkedList;
import java.util.Queue;

public class BufferFinal {

	//Id del buzon
	private String id;
	
	//Productos guardados en el buffer
	private Queue<String> productos;
	

	
	public BufferFinal(String id) {
		this.id = id;
		productos = new LinkedList<String>();
	}
	
	public synchronized String enviar() {
		while(productos.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		String enviar = productos.remove();
		notifyAll();
		return enviar;
	}
	
	public synchronized void recibir(String producto) {
		productos.add(producto);
		notifyAll();
	}
	
	
	public synchronized boolean isEmpty() {
		return this.productos.size() == 0;
	}
}
