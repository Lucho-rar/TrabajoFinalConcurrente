import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class RedDePetri {
  private Integer[][] contadorIzq = new Integer[3][2];
  private Integer[][] contadorDer = new Integer[3][2];
  private Integer[] contadorUltimoSegmento = new Integer[3];
 
  private double[][] matrizIncidenciaEntrada = {//W-
     //T0 T1 T2 T3 T4 T5 T6 T7 T8 T9 T10 T11 T12 T13 T14
      { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P0
      { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P1
      { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P2
      { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, // P3
      { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P4
      { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P5
      { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, // P6
      { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P7
      { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, // P8
      { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, // P9
      { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, // P10
      { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, // P11
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 }, // P12
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 }, // P13
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 }, // P14
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, // P15
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, // P16
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // P17
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 } // P18
  };
  
  private double[][] matrizIncidenciaSalida = {//W+
     //T0 T1 T2 T3 T4 T5 T6 T7 T8 T9 T10 T11 T12 T13 T14
      { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P0
      { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P1
      { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P2
      { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // P3
      { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P4
      { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P5
      { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P6
      { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, // P7
      { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P8
      { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, // P9
      { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, // P10
      { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, // P11
      { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, // P12
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 }, // P13
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, // P14
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 }, // P15
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 }, // P16
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, // P17
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 } // P18
  };
  

  private double[][] marcadoActual = {
      { 0, 1, 0, 3, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 0, 1 } // P0-18
  };

  private double[][] transicionesSensibilizadas = {
      { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } // T0-14
  };
  
  private final RealMatrix matrizIncidenciaEntradaMatrix = MatrixUtils.createRealMatrix(matrizIncidenciaEntrada);
  private final RealMatrix matrizIncidenciaSalidaMatrix = MatrixUtils.createRealMatrix(matrizIncidenciaSalida);
  private final RealMatrix incidencia = matrizIncidenciaSalidaMatrix.copy().subtract(matrizIncidenciaEntradaMatrix);
  
  private RealMatrix marcadoActualMatrix = MatrixUtils.createRealMatrix(marcadoActual);
  private RealMatrix transicionesSensibilizadasMatrix = MatrixUtils.createRealMatrix(transicionesSensibilizadas);
  
  private Log log;
  private Log log_regex;
  //transiciones temporizadas T0, T3, T4, T7, T8, T11, T12, T14
  private long[]  timeStamps = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  private long[] alfa = {10, 0, 0, 10, 10, 0, 0, 20, 20, 0, 0, 20, 20, 0, 10};
  private long[] beta = {500, 0, 0, 500, 500, 0, 0, 500, 500, 0, 0, 500, 500, 0, 500};
  private boolean conTiempo = false;
  private String secuenciaDisparos = "";
  private boolean[] esperando = new boolean[15];
  private final int invariantesMax = 200;
  private int invCompletados = 0;
  
  public RedDePetri(Log log, Log log_regex) {
    for (Integer[] row : contadorIzq) {
      for (int i = 0; i < row.length; i++) {
        row[i] = 0;
      }
    }
    for (Integer[] row : contadorDer) {
      for (int i = 0; i < row.length; i++) {
          row[i] = 0;
      }
    }
    for (int i = 0; i < contadorUltimoSegmento.length; i++) {
        contadorUltimoSegmento[i] = 0;
    }
   
    Arrays.fill(esperando, false);
    this.inicializarTimeStamps();
    this.log = log;
    this.log_regex = log_regex;
  }
  public void setAlfa(int pos, int valor) {
	   alfa[pos] = valor;
  }
  
  public int getInvariantesMax() {
    return invariantesMax;
  }
  public void setConTiempo() {
    conTiempo = true;
  }

  public void setSinTiempo() {
    conTiempo = false;
  }
  
  private void disparar(int transicion) {
    RealMatrix sensibilizadasCopy;
    RealMatrix misTransicionesDisparadas = MatrixUtils.createRealMatrix(incidencia.getColumnDimension(),1);
    misTransicionesDisparadas.setEntry(transicion, 0, 1);
    sensibilizadasCopy = getTransicionesSensibilizadas();
    marcadoActualMatrix = marcadoActualMatrix.add(((incidencia.copy()).multiply(misTransicionesDisparadas)).transpose());
    String marcadoAcString = Arrays.toString(marcadoActualMatrix.getRow(0));
    if (!chequearInvariantesPlaza()){
      log.escribirArchivo("Error invariantes plaza " + marcadoAcString);
    }
    //hay que actualizar las marcas de tiempo de las transiciones que cambiaron de sensibilización 
    actualizarTimeStamp( sensibilizadasCopy, this.getTransicionesSensibilizadas());
    secuenciaDisparos += "T" + transicion;
  }

  public Boolean dispararTransicionConTiempo(int transicion, Semaphore mutex) {
	  if (this.invCompletados < invariantesMax && isSensibilizada(transicion)	) {
      long tiempoActual = System.currentTimeMillis();
      if(testVentanaTiempo(tiempoActual, transicion)) {
        //está en ventana de tiempo.
        if(!hayEsperando()) {
          disparar(transicion);
          //setear el nuevo timestamp
          this.setNuevoTimeStamp(transicion);
          return true;
        } else {
          //está esperando
          return false;
        }
      } else {
        //si es menor que alfa, seteo esperando en true, lo duermo lo que le falta (timestamp+alfa-ahora). 
        //si es mayor que beta, ya se pasó de la ventana de tiempo y se tiene que ir
        if(antesDeLaVentana(tiempoActual,transicion)) {
          setEsperando(transicion);
          long tiempoDormir = this.timeStamps[transicion] + alfa[transicion] - tiempoActual;
          try {
            mutex.release();
            TimeUnit.MILLISECONDS.sleep(tiempoDormir);
            mutex.acquire();
            resetEsperando(transicion);
            disparar(transicion);
            return true;
          } catch(InterruptedException e) {
            e.printStackTrace();
            return false;
          }
        } else {
          return false;
        }
      }
		}	
    return false;
     // mk= mi+W.S
     // mk: proximo marcado
     // mi: marcado actual
     // w: matriz incidencia
     //s: mis transiciones disparadas
  }
  
  
  private boolean testVentanaTiempo(long actual, int transicion) {
	  //alfa<ahora-timestamp<beta
	  //obtiene el instantnow
	  //tiene que verificar si el tiempo actual está entre el alpha y beta asociado a la transición
    if (this.conTiempo) {
      if(transicion == 1 || transicion == 2 || transicion == 5 || transicion == 6 || transicion == 9 || transicion == 10 || transicion == 13)
        { return true; }
      return ((actual >= (this.timeStamps[transicion] + alfa[transicion])) && (actual <= (timeStamps[transicion] + beta[transicion])));
    } else {
      return true;
    }
  }

  private void setNuevoTimeStamp(int transicion) {
    this.timeStamps[transicion] = System.currentTimeMillis();
  }

  private void actualizarTimeStamp(RealMatrix previoDisparo, RealMatrix posteriorDisparo) {
    for(int i = 0; i < previoDisparo.getColumnDimension(); i++) {
      if (posteriorDisparo.getEntry(0, i) - previoDisparo.getEntry(0, i) == 1) {
        setNuevoTimeStamp(i);
      }
    }
  }

  private void inicializarTimeStamps() {
    //lleno con la marca actual de tiempo
    for(int i = 0 ; i < this.timeStamps.length; i++) {
      if(this.isSensibilizada(i)) {
        setNuevoTimeStamp(i);
      }
    }
  }

  private boolean antesDeLaVentana(long actual, int transicion) {
		return actual < (this.timeStamps[transicion] + alfa[transicion]);
	}
	
  private boolean hayEsperando(){
    for (int i = 0 ; i < 15 ; i++){
      if (esperando[i] == true ){
        return true;
      }
    }
    return false;
  }
  private void setEsperando(int transicion) {
		this.esperando[transicion] = true;
	}

  private void resetEsperando(int transicion) {
		this.esperando[transicion] = false;
	}

  public RealMatrix getTransicionesSensibilizadas() {
    RealMatrix transicionesSensibilizadasMatrix = MatrixUtils.createRealMatrix(new double[1][15]);
    int aux_p;
    int cant_t = transicionesSensibilizadasMatrix.getColumnDimension();
    int cant_p = marcadoActualMatrix.getColumnDimension();
    
    transicionesSensibilizadasMatrix.setEntry(0, 0, 1);//La t0 siempre está sensibilizada porque no tiene plazas de entrada
    /*Aclaracion: i comienza en 1 porque excluimos el caso de la transicion t0 que siempre está sensibilizada
    */
    for (int aux_t = 1; aux_t < cant_t; aux_t++) {
      aux_p = 0;  // aux_p no puede llegar a 19 (plazas) por la condicion del while
      while (aux_p < cant_p ) {
        //1-la plaza tiene que ser una plaza de entrada de la transicion
        //2- el marcado actual tiene que ser mayor que el peso del arco de entrada
        if (matrizIncidenciaEntradaMatrix.getEntry(aux_p, aux_t) != 0){
          if ((marcadoActualMatrix.getEntry(0, aux_p) - matrizIncidenciaEntradaMatrix.getEntry(aux_p, aux_t)) >= 0){
              //La transición está sensibilizada
            transicionesSensibilizadasMatrix.setEntry(0, aux_t, 1);
          } else {
            transicionesSensibilizadasMatrix.setEntry(0, aux_t, 0);
            break;
          }
        }
        aux_p++;
      }
    }
    return transicionesSensibilizadasMatrix;
  }
  


  private Boolean isSensibilizada(int transicion) {
    transicionesSensibilizadasMatrix = getTransicionesSensibilizadas();
    return transicionesSensibilizadasMatrix.getEntry(0, transicion) == 1;
  }
  
  private double getCantidadTokensPlaza(int plaza) {
    return marcadoActualMatrix.getEntry(0, plaza);
  }

  private boolean chequearInvariantesPlaza() {
    boolean pInv1 = (getCantidadTokensPlaza(1) + getCantidadTokensPlaza(2)) == 1 ;
    boolean pInv2 = (getCantidadTokensPlaza(4) + getCantidadTokensPlaza(5)) == 1 ;
    boolean pInv3 = (getCantidadTokensPlaza(13) + getCantidadTokensPlaza(14) + getCantidadTokensPlaza(15)) == 1 ;
    boolean pInv4 = (getCantidadTokensPlaza(7) + getCantidadTokensPlaza(8)) == 1 ;
    boolean pInv5 = (getCantidadTokensPlaza(10) + getCantidadTokensPlaza(11)) == 1 ;
    boolean pInv6 = (getCantidadTokensPlaza(8) + getCantidadTokensPlaza(9) + getCantidadTokensPlaza(10 ))== 2 ;
    boolean pInv7 = (getCantidadTokensPlaza(17) + getCantidadTokensPlaza(18)) == 1 ;
    boolean pInv8 = (getCantidadTokensPlaza(2) + getCantidadTokensPlaza(3) + getCantidadTokensPlaza(4) + getCantidadTokensPlaza(17)) == 3;

    if (pInv1 && pInv2 && pInv3 && pInv4 && pInv5 && pInv6 && pInv7 && pInv8) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Actualiza el contador de transiciones
   * @param transicion transición a actualizar
   */
  public void actualizarContadorTransicion(int transicion) {
    switch(transicion) {
      case 0: contadorUltimoSegmento[0]++; break;
      case 1: contadorIzq[0][0]++; break;
      case 2: contadorDer[0][0]++; break;
      case 3: contadorIzq[0][1]++; break;
      case 4: contadorDer[0][1]++; break;
      case 5: contadorIzq[1][0]++; break;
      case 6: contadorDer[1][0]++; break;
      case 7: contadorIzq[1][1]++; break;
      case 8: contadorDer[1][1]++; break;
      case 9: contadorIzq[2][0]++; break;
      case 10: contadorDer[2][0]++; break;
      case 11: contadorIzq[2][1]++; break;
      case 12: contadorDer[2][1]++; break;
      case 13: contadorUltimoSegmento[1]++; break;
      case 14: contadorUltimoSegmento[2]++; break;
      default: break;
    }
  }

  /**
   * Getter del contador de una transicion 
   *
   * @param transicion transición de interés
   * @return cantidad de veces que se disparó la transición
   */
  public int getContadorTransicion(int transicion) {
    switch(transicion) {
      case 0: return contadorUltimoSegmento[0];
      case 1: return contadorIzq[0][0];
      case 2: return contadorDer[0][0];
      case 3: return contadorIzq[0][1];
      case 4: return contadorDer[0][1];
      case 5: return contadorIzq[1][0];
      case 6: return contadorDer[1][0];
      case 7: return contadorIzq[1][1];
      case 8: return contadorDer[1][1];
      case 9: return contadorIzq[2][0];
      case 10: return contadorDer[2][0];
      case 11: return contadorIzq[2][1];
      case 12: return contadorDer[2][1];
      case 13: return contadorUltimoSegmento[1];
      case 14: return contadorUltimoSegmento[2];
      default: return 0;
    }
  }

  public void escribirLog() {
		log_regex.escribirArchivo(secuenciaDisparos);
		String salida = "";
		// Log de Plazas
		for(int i = 0; i < incidencia.getRowDimension(); i++) {
			salida += "P(" + i + "):" + this.getCantidadTokensPlaza(i) + " ";
		}
		log.escribirArchivo("Red de Petri " + salida + "\n");
		// Log de Transiciones por segmento
		log.escribirArchivo("SA(T1-T3): " + this.getContadorTransicion(3) + " SB(T2-T4): "
		+ this.getContadorTransicion(4) + " SC(T5-T7): " + this.getContadorTransicion(7)
		+ " SD(T6-T8): " + this.getContadorTransicion(8) + " SE(T9-T11): "
		+ this.getContadorTransicion(11) + " SF(T10-T12):" + this.getContadorTransicion(12));
	}
	
	public void invCompletado() {
	  invCompletados++;
  }
  	
  public int getInvCompletados(){
    return invCompletados;
  }
}
