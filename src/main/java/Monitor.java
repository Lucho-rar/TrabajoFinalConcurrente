import java.util.concurrent.Semaphore;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *  Clase Monitor
 * 
 * Esta clase se encarga de controlar el flujo de la red de petri
 * 
 */
public class Monitor {

	private RedDePetri rdp; // Red de Petri
	private Politica miPolitica; // Política de disparo
	private ColaTransiciones miColaTransiciones; // Cola de transiciones
	private Semaphore mutex; // Mutex
	private RealMatrix sensibilizadas; // Transiciones sensibilizadas
	private double[] colaNoDisparados; // No disparados
	private double[] m; // AND de sensibilizadas y no disparados
	private boolean matarEjecucion=true;
		
	/**
	 * Constructor de la clase Monitor
	 * @param redp Red de Petri
	 * @param mp Política de disparo
	 
	 */

	public Monitor(RedDePetri redp, Politica mp) {
		// Inicialización de variables y objetos
		rdp = redp;
		miPolitica = mp;
		miColaTransiciones = new ColaTransiciones();
		m = new double[15];
		mutex = new Semaphore(1, false);

		// Chequeo si la politica es con o sin tiempo
		if (miPolitica.getConTiempo()) {
			rdp.setConTiempo();
		} else {
			rdp.setSinTiempo();
		}
	}

	/**
	 * Método de finalización de ejecución del sistema
	 *
	 * Se fija si el sistema sigue corriendo y si no, desencola las transiciones que quedaron en la cola.
	 */
	private void setMatarEjecucion() {
		if(matarEjecucion) {
		double[] saliendo = this.miColaTransiciones.quienesEstan();
		for (int i = 0; i < saliendo.length; i++) {
			if(saliendo[i] == 1) {
				this.miColaTransiciones.desencolar(i);
			}
		}
		matarEjecucion = false;
		}
	}

	/**
	 * Método para disparar una transición en la red de petri
	 * @param transicion Transición a disparar
	
	 */
	public void dispararTransicion(int transicion) {
			// Intenta adquirir el mutex
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		int transicionElegida; // Transición elegida
		
    	Boolean k = true; // Boolean para saber si se pudo disparar la transición
		while (k) {
			// Si el sistema no sigue corriendo, se libera el mutex y se retorna 
			if(rdp.getInvCompletados() < rdp.getInvariantesMax()) {
				k = rdp.dispararTransicionConTiempo(transicion, mutex);
				
			} else {
				setMatarEjecucion();
				mutex.release();
				return;
			}
			// Si se pudo disparar la transición, busco prox transicion a disparar. Si no, encolo la transición y seteo k en false
			if (k) {
				rdp.actualizarContadorTransicion(transicion);  // Actualizo el contador de transiciones
				sensibilizadas = rdp.getTransicionesSensibilizadas(); // Obtengo las transiciones sensibilizadas
				colaNoDisparados = miColaTransiciones.quienesEstan(); // Obtengo las transiciones no disparadas - encoladas 
				// Realiza la AND de sensibilizadas y no disparados
				for (int i = 0; i < colaNoDisparados.length; i++) {
					m[i] = (sensibilizadas.getEntry(0, i) == 1.0 && colaNoDisparados[i] == 1.0) ? 1.0 : 0.0;
				}
				// Me fijo si hay alguna transición en M. Si hay, llamo a la política para que elija una y la desencolo. Si no, seteo k en false
				if (recorrer(m)) {
					transicionElegida = miPolitica.cual(m); 
					miColaTransiciones.desencolar(transicionElegida);
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

	/**
	 * Método para recorrer m y ver si tiene alguna transición para poder disparar
	 * @param m Vector de transiciones sensibilizadas y no disparadas
	 * @return True si hay alguna transición para disparar, False si no
	*/
	private boolean recorrer(double[] m) {
		for (int i = 0; i < m.length; i++) {
			if (m[i] == 1.0) {
				return true;
			}
		}
		return false;
	}
}
