
//import java.util.concurrent.*;
//import org.apache.commons.math3.linear.MatrixUtils;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;

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
  private Semaphore mutex;

  // private HashMap<String,Thread> hilos;

  private RealMatrix sensibilizadas;
  private double[] colaNoDisparados;
  private double[] m;
  private Log log;

  public Monitor(RedDePetri redp, Politica mp, Log log) {
    rdp = redp;
    miPolitica = mp;
    miCola = new Cola2();
    m = new double[15];
    mutex = new Semaphore(1, true);
    this.log = log;
    
  }

  // Para disparar una transición, primero debe adquirirse el semáforo del monitor
  public void dispararTransicion(int transicion, Procesador procesador) {
    
    try {
      mutex.acquire();
    }
    catch (InterruptedException e) {
    	throw new RuntimeException(e);
    }

      // currentThread().getName();
      int transicionElegida;
      Boolean k = true;
      
      /*System.out.println();
      if(!mutex.hasQueuedThreads()) {
    	  System.out.println("NO HAY HILOS");
        miCola.mostrarListasEspera();
      }
      System.out.println();*/

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
          procesador.operar(transicion);
          miPolitica.actualizarContadorTransicion(transicion);
          String salida="";
         // for(int i=0;i<15;i++) {
        //	  salida+="t"+i+": "+miPolitica.getContadorTransicion(i)+" ";
         // }
          //log.escribirArchivo(salida);//////////////////////
          /*if(transicion == 13) {
        	  System.out.println();
        	  System.out.println("CONTADOR TRANSICION 11: " + miPolitica.getContadorTransicion(11));
        	  System.out.println("CONTADOR TRANSICION 12: " + miPolitica.getContadorTransicion(12));
        	  System.out.println("CONTADOR TRANSICION 13: " + miPolitica.getContadorTransicion(13));
        	  System.out.println("CANTIDAD TOKENS PLAZA 16: " + rdp.getCantidadTokensPlaza(16));
        	  System.out.println();
          }*/
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
          
          String salida1="sensi=";
          String salida2="NDisp=";
          String salida3="m    =";
          int contador=0;
         /* for(int i=0;i<15;i++) {
        	  salida1+=(int)sensibilizadas.getEntry(0, i)+", ";
        	  salida2+=(int)colaNoDisparados[i]+", ";
        	  salida3+=(int)m[i]+", ";
        	  contador+=m[i];
          }
          log.escribirArchivo(salida1);
          log.escribirArchivo(salida2);
          log.escribirArchivo(salida3);
          if(contador>1) {
        	  log.escribirArchivo("M tiene más de 1: "+contador);
          }*/
          
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
            /*if (mutex.hasQueuedThreads() > 0) {
              temphilos = mutex.getMyQueuedThreads();
              String nombreHilo;
             
              for (Thread thread : temphilos) {
                nombreHilo=thread.getName();
                for (Procesador proc : arrayProcesadores ){
                   if (nombreHilo == proc.getNombreHilo() && proc.perteneceTransicion(transicionElegida) ) {
                	 mutex.release();
                     thread.interrupt();
                     //break;
                   }
                }
              }
            }*/
            
            return;
          } else {
            k = false;
          }
        } else {
          mutex.release();
          miCola.encolar(transicion);
          k = true;
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
    /*finally {
    	mutex.release();
    }*/

  }

  public boolean recorrer(double[] m) {
    for (int i = 0; i < m.length; i++) {
      if (m[i] == 1.0) {
        return true;
      }
    }
    return false;
  }
  
  public double cantidadTokensPlaza(int plaza) {
	 return rdp.getCantidadTokensPlaza(plaza);
  }
  
  public void contadorInvariantes(Imagen imagen) {
	  rdp.actualizarContadorInvariante(imagen);
      this.log.escribirArchivo("cantidad de veces que se decidió entre t9 y t10:"+this.miPolitica.getContador_decisiones());
  }
  /*
   * public void setHilos(HashMap<String,Thread> hilos){
   * this.hilos=hilos;
   * }
   */
  

}
