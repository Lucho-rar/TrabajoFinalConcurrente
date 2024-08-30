
//import java.util.concurrent.*;
//import org.apache.commons.math3.linear.MatrixUtils;
import java.util.Collection;

import org.apache.commons.math3.linear.RealMatrix;

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
    m = new double[15];
    //colaNoDisparados=new double[15];
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

          /*for (double[] row : sensibilizadas.getData()) {  // Obtener cada fila como un array
            for (double value : row) {  // Recorrer cada valor en la fila
                  System.out.print(value + " ");
              }
              // Imprimir una nueva línea después de cada fila
              System.out.println();
            }
          System.out.println();*/

          colaNoDisparados = miCola.quienesEstan();

          /*System.out.println();
          System.out.println(Arrays.toString(colaNoDisparados));
          System.out.println();*/
          
          //m = sensibilizadas.operate(colaNoDisparados);

          for (int i = 0; i < colaNoDisparados.length; i++) {
            // Aplicar la operación AND a cada elemento en la fila correspondiente
            m[i] = (sensibilizadas.getEntry(0, i) == 1.0 && colaNoDisparados[i] == 1.0) ? 1.0 : 0.0;
          }
 /*         for(int i=0; i<sensibilizadas.getColumnDimension();i++){
            if(sensibilizadas.getEntry(0,i)==1&&colaNoDisparados[i]==1){
              m[i]=1;
              }
              else{
               System.out.printf("valor de i %s",i); 
              m[i]=0;
              }
            }*/
          

          
          
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
