import java.util.concurrent.ThreadFactory;

/**
  * Clase Hilo que implementa Thread Factory
  * counter: Contador de hilos
  * name: Nombre del hilo
  */
public class FabricaDeHilos implements ThreadFactory{

  private int counter;

  //Constructor
  public FabricaDeHilos() {
    counter = 0;
  }

  // Metodo que crea un nuevo hilo
  @Override
  public Thread newThread(Runnable r) {
    String nombre= ((Procesador)r).getNombre() + counter;
    ((Procesador)r).setNombreHilo(nombre);
    Thread t = new Thread(r, nombre);
    counter++;    //aumenta el contador de hilos
    return t;     //retorna el hilo creado
  }
}
