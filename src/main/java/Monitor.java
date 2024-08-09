
//import java.util.concurrent.*;
import org.apache.commons.math3.linear.RealMatrix;
//import org.apache.commons.math3.linear.MatrixUtils;
import java.util.*;

/**
 * Clase Monitor
 */
public class Monitor {

  private RedDePetri rdp;
  private Politica miPolitica;
  private Cola2 miCola;

  /*
   * private Cola cola_cargaImagenes;
   * private Cola cola_cargaAjustador;
   * private Cola cola_recortador;
   * private Cola cola_exportador;
   */

  // ODO: cambiar los nombres según su función
  // recursos compartidos y restricciones
  /*
   * private Semaphore p1;//restriccion. un token max
   * private Semaphore p3;//recurso compartido. tres tokens max
   * private Semaphore p5;//restriccion. un token max
   * //
   * private Semaphore p7;//restriccion. un token max
   * private Semaphore p9;//recurso compartido. dos tokens max
   * private Semaphore p11;//restriccion. un token max
   * //
   * private Semaphore p13;//recurso compartido. un token max
   * private Semaphore p18;//restriccion. un token max
   */

  // private Semaphore mutex;
  private MySemaphore mutex;

  // private HashMap<String,Thread> hilos;

  private RealMatrix sensibilizadas;
  private double[] colaNoDisparados;
  private double[] m;

  public Monitor(RedDePetri redp, Politica mp) {
    rdp = redp;
    miPolitica = mp;
    miCola = new Cola2();
    // inicializacion de colas
    /*
     * cola_cargaImagenes=new Cola();
     * cola_cargaAjustador=new Cola();
     * cola_recortador=new Cola();
     * cola_exportador=new Cola();
     */

    // inicializacion de recursos compartidos / semaforos
    /*
     * p1=new Semaphore(1);
     * p3=new Semaphore(3);
     * p5=new Semaphore(1);
     * p7=new Semaphore(1);
     * p9=new Semaphore(2);
     * p11=new Semaphore(1);
     * p13 = new Semaphore (1);
     * p18 = new Semaphore (1);
     */

    mutex = new MySemaphore(1);
    // hilos = new HashMap<String,Thread>();
  }

  // Para disparar una transición, primero debe adquirirse el semáforo del monitor
  public void dispararTransicion(int transicion) {
    // return mutex;
    Collection<Thread> temphilos;
    try {
      mutex.acquire();

      // currentThread().getName();
      int transicionElegida;
      Boolean k = true;

      while (k) {
        // sensibilizadas=rdp.getTransicionesSensibilizadas();
        // Math.random();

        // HAY QUE REVISAR EL USO DEL HASHMAP DE LOS HILOS
        /*
         * Thread hilo=hilos.get(((Procesador)currentThread()).getNombre());
         * int[] transiciones=hilo.getTransiciones();
         */

        k = rdp.dispararTransicion(transicion);
        if (k) {
          miPolitica.actualizarContadorTransicion(transicion);
          sensibilizadas = rdp.getTransicionesSensibilizadas();
          colaNoDisparados = miCola.quienesEstan();
          m = sensibilizadas.operate(colaNoDisparados);

          if (recorrer(m)) {
            transicionElegida = miPolitica.cual(m); // esto nos debería entregar un entero
            miCola.desencolar(transicionElegida);
            // debemos utilizar del semaphore el método getQueuedThreads() para obtener los
            // hilos
            // que intentaron hacer acquire. Luego, utilizar notify para despertar a ese
            // hilo.
            if (mutex.hasQueuedThreads()) {
              temphilos = mutex.getMyQueuedThreads();
              for (Runnable thread : temphilos) {
                if (((Procesador) thread).perteneceTransicion(transicionElegida)) {
                  thread.notify();
                  break;
                }

              }

            }

          } else {
            k = false;
          }
        } else {
          mutex.release();
          miCola.encolar(transicion);
        }
        // ODO: ver cuáles transiciones están en la cola y compararlas (&&) con estas

        /*
         * int[] transiciones=currentThread().getTransiciones();
         * for(int i=0;i<transiciones.length;i++){
         * if(transiciones[i]==1){
         * //System.out.println("entra");
         * 
         * }
         * }
         */

      }
      mutex.release();
    } catch (InterruptedException e) {
      e.printStackTrace();
      mutex.release();
    }

  }

  private boolean recorrer(double[] m) {
    for (int i = 0; i < m.length; i++) {
      if (m[i] != 0) {
        return true;
      }
    }
    return false;
  }
  /*
   * public void setHilos(HashMap<String,Thread> hilos){
   * this.hilos=hilos;
   * }
   */

}
