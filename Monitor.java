
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

	private ColaTransiciones miColaTransiciones;

	private Semaphore mutex;

	private RealMatrix sensibilizadas;
	private double[] colaNoDisparados;
	private double[] m; // t0

	private Log log;
	private boolean corriendo;

	public Monitor(RedDePetri redp, Politica mp, Log log) {
		rdp = redp;
		miPolitica = mp;

		miColaTransiciones = new ColaTransiciones();
		m = new double[15];
		mutex = new Semaphore(1, false);
		this.log = log;
		if (miPolitica.getConTiempo()) {
			rdp.setConTiempo();
		} else {
			rdp.setSinTiempo();
		}
		corriendo = true;

	}

	public void setMatarEjecucion() {
		
		corriendo = false;
		double[] saliendo=this.miColaTransiciones.quienesEstan();
		for (int i=0;i<saliendo.length;i++) {
			if(saliendo[i]==1) {
				this.miColaTransiciones.desencolar(i);
			}
		}
		
	}

	public boolean getCorriendo() {
		return corriendo;
	}

	public void dispararTransicion(int transicion, Procesador procesador) {
        
        	try {
    			mutex.acquire();
    		} catch (InterruptedException e) {
    			throw new RuntimeException(e);
    		}

    		int transicionElegida;
    		Boolean k = true;

    		while (k) {
                if(getCorriendo()) {
                	k = rdp.dispararTransicionConTiempo(transicion, mutex);
                	
                }else {
                	mutex.release();
                	return;
    			}
    			if (k) {

    				procesador.operar(transicion);
    				miPolitica.actualizarContadorTransicion(transicion);

    				sensibilizadas = rdp.getTransicionesSensibilizadas();

    				colaNoDisparados = miColaTransiciones.quienesEstan();

    				for (int i = 0; i < colaNoDisparados.length; i++) {
    					// Aplicar la operación AND a cada elemento en la fila correspondiente
    					m[i] = (sensibilizadas.getEntry(0, i) == 1.0 && colaNoDisparados[i] == 1.0) ? 1.0 : 0.0;
    				}

    				/*
    				 * String salida="{"; for(int i=0;i<m.length;i++) { salida+=m[i]+","; }
    				 * salida+="}"; log.escribirArchivo("con tiempo m="+salida);
    				 */
    				if (recorrer(m)) {
    					/*
    					if (m[14] == 1) {
    						transicionElegida = 14;
    						log.escribirArchivo("Elegí la transición 14");
    					} else {*/
    					transicionElegida = miPolitica.cual(m); // esto nos debería entregar un entero
    					//}

    					miColaTransiciones.desencolar(transicionElegida);
    					// mutex.release();
    					return;
    				} else {
    					k = false;
    				}
    			} else {
    				mutex.release();
    				miColaTransiciones.encolar(transicion);
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
		if (miPolitica.getContador_decisiones() > 0) {
			this.log.escribirArchivo("1cantidad de veces que se decidió entre t9 y t10:"
					+ this.miPolitica.getContador_decisiones() + " decision9:"
					+ ((float) miPolitica.getDecisionT9() / (float) miPolitica.getContador_decisiones()) * 100 + "%"
					+ " decision10:"
					+ ((float) miPolitica.getDecisionT10() / (float) miPolitica.getContador_decisiones()) * 100 + "%");
		} else {
			this.log.escribirArchivo("2cantidad de veces que se decidió entre t9 y t10:"
					+ this.miPolitica.getContador_decisiones() + " decision9:" + miPolitica.getDecisionT9()
					+ " decision10:" + miPolitica.getDecisionT10());
		}
		if (rdp.getContadorTotalInvariantes() >= 200) {
			this.setMatarEjecucion();
			String salida="";
			for(int i=0;i<19;i++) {
				salida+="P("+i+"):"+rdp.getCantidadTokensPlaza(i)+" ";
			}
			log.escribirArchivo("Red de Petri "+ salida + "\n");
			log.escribirArchivo("SA(T1-T3): " + miPolitica.getContadorTransicion(3) + " SB(T2-T4): "
					+ miPolitica.getContadorTransicion(4) + " SC(T5-T7): " + miPolitica.getContadorTransicion(7)
					+ " SD(T6-T8): " + miPolitica.getContadorTransicion(8) + " SE(T9-T11): "
					+ miPolitica.getContadorTransicion(11) + " SF(T10-T12):" + miPolitica.getContadorTransicion(12));
		}

	}

}
