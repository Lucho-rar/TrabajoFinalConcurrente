import java.util.ArrayList;
import java.util.concurrent.*;


/*
* Clase Cola
*
* lista: Array de imagenes
* mutex: Semaforo para sincronizar el acceso a la cola
*/
public class ColaTransiciones  {
  private double[] lista;
  private Semaphore mutex;
  private ArrayList<Semaphore> colaTransiciones;

  // constructor
  public ColaTransiciones(){
    mutex = new Semaphore(1);
    lista = new double[15];
    inicializarColaTransiciones();
  }

  //Método para agregar una imagen a la cola
  public void encolar(int transicion){
    try{
      colaTransiciones.get(transicion).acquire();
    } catch (InterruptedException e){
       System.out.println("error al encolar");
    }
  }
  
  //Método para sacar una imagen de la cola
  public void desencolar(int transicion){
    colaTransiciones.get(transicion).release();
  }

  //Método para saber si la cola esta vacia
  public boolean estaVacia(){
    return lista.length == 0;
  }

  //Método para obtener el tamaño de la cola
  public int tamano(){
    return lista.length;
  }
  public Semaphore getMutex(){
    return mutex;
  }
  
  //Metodo para obtener las transiciones encoladas
  public double[] quienesEstan(){
	for(int i = 0; i < 15; i++) {
		if(colaTransiciones.get(i).hasQueuedThreads()) {
			lista[i] = 1;
		}
		else {
			lista[i] = 0;
		}
	}
    return lista;
  }
  
  public void inicializarColaTransiciones() {
	  colaTransiciones = new ArrayList<Semaphore>();
	  
	  for(int i = 0; i < 15; i++)
		  colaTransiciones.add(new Semaphore(0, true));
  }

  public void mostrarListasEspera(){
    System.out.println("mostrarlistaespera");
    for(int i = 0; i < 15; i++){
      System.out.println("cant hilos esperando: "+colaTransiciones.get(i).getQueueLength());
    }
  }
}
