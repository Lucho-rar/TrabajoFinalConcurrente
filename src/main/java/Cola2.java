import java.util.ArrayList;
import java.util.concurrent.*;


/*
* Clase Cola
*
* lista: Array de imagenes
* mutex: Semaforo para sincronizar el acceso a la cola
*/
public class Cola2  {
  private double[] lista;
  private Semaphore mutex;
  private ArrayList<Semaphore> colaTransiciones;

  // constructor
  public Cola2(){
    mutex = new Semaphore(1);
    lista = new double[15];
    inicializarColaTransiciones();
  }

  //Método para agregar una imagen a la cola
  public void encolar(int transicion){
    try{
      //mutex.acquire(); //acquire del semaforo
      //lista[transicion]=1; //agrega a la lista
    	///System.out.println();
      //System.out.printf("ENCOLÉ TRANSICION " + transicion + " EL THREAD " + Thread.currentThread().getName());
      colaTransiciones.get(transicion).acquire();
      //System.out.printf("SE LIBERA TRANSICION " + transicion + " EL THREAD " + Thread.currentThread().getName());
      //System.out.println();
      
      
      
    }catch (InterruptedException e){
       System.out.println("error al encolar");
    }
    finally {
    	//mutex.release(); //release del semaforo
    }
  }
  
  //Método para sacar una imagen de la cola
  public void desencolar(int transicion){
    //try{
      //mutex.acquire();  //adquiere el semaforo
  	//System.out.println();
    //System.out.println("DESENCOLE HILO: " + transicion + ", POR EL HILO " + Thread.currentThread().getName());
    
      //lista[transicion] = 0;
    if(colaTransiciones.get(transicion).availablePermits() == 0) {
    	colaTransiciones.get(transicion).release();
        //System.out.println("LA TRANSICION " + transicion + " TIENE " + colaTransiciones.get(transicion).availablePermits() + " PERMISOS.");
        //System.out.println();
    }
      

    //}//catch (InterruptedException e){
      // System.out.println("error al desencolar");
    //}
 /*   if(lista.length > 0){  // si la lista no esta vacia toma la imagen y la saca de la misma
      img=lista[0];
      lista.remove(0);
    }*/
    //mutex.release(); // release del semaforo
    //return img;
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
    for(int i=0;i<15;i++){
      System.out.println("cant hilos esperando: "+colaTransiciones.get(i).getQueueLength());
    }
  }
}
