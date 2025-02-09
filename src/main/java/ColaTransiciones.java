import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/*
* Clase ColaTransiciones
*
* lista: Array de Transiciones
* colaTransiciones: array de semaforos para dormir los hilos
*/
public class ColaTransiciones {
  private double[] lista;
  private ArrayList<Semaphore> colaTransiciones;

  //Constructor
  public ColaTransiciones() {
    lista = new double[15];
    inicializarColaTransiciones();
  }

  //Método para agregar una transicion a la cola
  public void encolar(int transicion) {
    try {
      colaTransiciones.get(transicion).acquire();
    } catch(InterruptedException e) {
      System.out.println("error al encolar");
    }
  }
  
  //Método para sacar una transicion de la cola
  public void desencolar(int transicion) {
    colaTransiciones.get(transicion).release();
  }
  
  //Metodo para obtener las transiciones encoladas
  public double[] quienesEstan() {
    for (int i = 0; i < 15; i++) {
      if (colaTransiciones.get(i).hasQueuedThreads()) {
        lista[i] = 1;
      }
      else {
        lista[i] = 0;
      }
    }
    return lista;
  }
  
  private void inicializarColaTransiciones() {
    colaTransiciones = new ArrayList<Semaphore>();
    for (int i = 0; i < 15; i++)
      colaTransiciones.add(new Semaphore(0, true));
  }
}
