
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
  private Cola2 miColaSinTiempo;
  private Cola2 miColaConTiempo;

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
  private double[] m;         //t0
  //private long tiempos[]= {500,0,0,500,200,0,0,200,1000,0,0,1000,1000,0,0};	
  private long tiempos[]= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};	
  private Log log;
  
  public Monitor(RedDePetri redp, Politica mp, Log log) {
    rdp = redp;
    miPolitica = mp;
    miColaSinTiempo = new Cola2();
    miColaConTiempo = new Cola2();
    m = new double[15];
    mutex = new Semaphore(1, false);
    this.log = log;
    
  }
  public void dormirTiempo(int transicion) {
	  if(this.tiempos[transicion]>0) {
		try {
			Thread.currentThread().sleep(tiempos[transicion]);
			log.escribirArchivo("hilo dormido: "+Thread.currentThread().getName());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	  }
	  
  }
  public void dispararTransicion(int transicion, Procesador procesador) {
	  if(this.miPolitica.getTiempo()) {
		 if(!(transicion==1||transicion==2||transicion==5||transicion==6||transicion==9||transicion==10||transicion==13) ) {
			  
			  dispararTransicionConTiempo(transicion,procesador);
		  }else {
			  log.escribirArchivo("ingresé a sin tiempo. transicion:"+transicion);
			  dispararTransicionSinTiempo(transicion,procesador);
		  }
		 //dispararTransicionConTiempo(transicion,procesador);
		 	  
	  }else {
		  dispararTransicionSinTiempo(transicion,procesador);
	  }
	  
  }
  // Para disparar una transición, primero debe adquirirse el semáforo del monitor
  public void dispararTransicionSinTiempo(int transicion, Procesador procesador) {
    
    try {
      mutex.acquire();
    }
    catch (InterruptedException e) {
    	throw new RuntimeException(e);
    }

      int transicionElegida;
      Boolean k = true;
  

      while (k) {
        
        k = rdp.dispararTransicion(transicion);
        if (k) { 
          procesador.operar(transicion);
          miPolitica.actualizarContadorTransicion(transicion);
          
         
          sensibilizadas = rdp.getTransicionesSensibilizadas();


          colaNoDisparados = miColaSinTiempo.quienesEstan();

		
          for (int i = 0; i < colaNoDisparados.length; i++) {
            // Aplicar la operación AND a cada elemento en la fila correspondiente
            m[i] = (sensibilizadas.getEntry(0, i) == 1.0 && colaNoDisparados[i] == 1.0) ? 1.0 : 0.0;
          }
          String salida="{";
          for(int i=0;i<m.length;i++) {
        	  salida+=m[i]+",";
          }
          salida+="}";
          log.escribirArchivo("sin tiempo m="+salida);
          
         
          if (recorrer(m)) {
            transicionElegida = miPolitica.cual(m); // esto nos debería entregar un entero
           
            miColaSinTiempo.desencolar(transicionElegida);
            
            return;
          } else {
            k = false;
          }
        } else {
          mutex.release();
          miColaSinTiempo.encolar(transicion);
          k = true;
        }
       
      }
      mutex.release();
  }
  
  
  public void dispararTransicionConTiempo(int transicion, Procesador procesador) {
	    
	    try {
	      mutex.acquire();
	    }
	    catch (InterruptedException e) {
	    	throw new RuntimeException(e);
	    }

	      int transicionElegida;
	      Boolean k = true;
	  

	      while (k) {
	        
	        k = rdp.dispararTransicionConTiempo(transicion,mutex);
	        if (k) { 
	        	
	          procesador.operar(transicion);
	          miPolitica.actualizarContadorTransicion(transicion);
	         
	         
	          sensibilizadas = rdp.getTransicionesSensibilizadas();


	          colaNoDisparados = miColaConTiempo.quienesEstan();

			
	          for (int i = 0; i < colaNoDisparados.length; i++) {
	            // Aplicar la operación AND a cada elemento en la fila correspondiente
	            m[i] = (sensibilizadas.getEntry(0, i) == 1.0 && colaNoDisparados[i] == 1.0) ? 1.0 : 0.0;
	          }
	          String salida="{";
	          for(int i=0;i<m.length;i++) {
	        	  salida+=m[i]+",";
	          }
	          salida+="}";
	          log.escribirArchivo("con tiempo m="+salida);
	          
	          if (recorrer(m)) {
	            transicionElegida = miPolitica.cual(m); // esto nos debería entregar un entero
	           
	            miColaConTiempo.desencolar(transicionElegida);
	            
	            return;
	          } else {
	            k = false;
	          }
	        } else {
	          mutex.release();
	          miColaConTiempo.encolar(transicion);
	          k = true;
	        }
	       
	      }
	      mutex.release();
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
