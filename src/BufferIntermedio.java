import java.util.LinkedList;
import java.util.Queue;

public class BufferIntermedio {
	
	//Id del buzon
	private int id;
	
	//Capacidad del buzon
	private int capacidad;
	
	//Productos guardados en el buffer
	private Queue<String> productos;
	

	
	public BufferIntermedio(int capacidad, int id) {
		this.id = id;
		this.capacidad = capacidad;
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
		while(productos.size() == capacidad) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		productos.add(producto);
		notifyAll();
	}
	
	public synchronized boolean isFull() {
		return this.productos.size() == this.capacidad;
	}
	
	public synchronized boolean isEmpty() {
		return this.productos.size() == 0;
	}
}
