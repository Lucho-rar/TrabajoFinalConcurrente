import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Clase Red de Petri a representar
 * 
 * - Real Matrix - double[][] matrizIncidencia
 * - int[] marcadoInicial
 * - int[] marcadoActual
 * - int[] transicionesSensibilizadas
 */
public class RedDePetri {
	
  private double matrizIncidenciaEntrada[][] = {//W-
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
  
  private double matrizIncidenciaSalida[][] = {//W+
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
  /*
    // T0 T1 T2 T3 T4 T5 T6 T7 T8 T9 T10 T11 T12 T13 T14
    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // P0
    { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // P1
    { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // P2
    { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },  // P3
    { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // P4
    { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // P5
    { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // P6
    { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },  // P7
    { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // P8
    { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },  // P9
    { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },  // P10
    { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },  // P11
    { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },  // P12
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },  // P13
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },  // P14
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },  // P15
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },  // P16
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },  // P17
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }   // P18
*/
  //TODO: no se usa
  private double matrizIncidencia[][] = {
      // T0 T1 T2 T3 T4 T5 T6 T7 T8 T9 T10 T11 T12 T13 T14
      { 1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P0
      { 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P1
      { 0, 1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P2
      { 0, -1, -1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1 }, // P3
      { 0, 0, 1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P4
      { 0, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // P5
      { 0, 0, 0, 1, 1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0 }, // P6
      { 0, 0, 0, 0, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, // P7
      { 0, 0, 0, 0, 0, 1, 0, -1, 0, 0, 0, 0, 0, 0, 0 }, // P8
      { 0, 0, 0, 0, 0, -1, -1, 1, 1, 0, 0, 0, 0, 0, 0 }, // P9
      { 0, 0, 0, 0, 0, 0, 1, 0, -1, 0, 0, 0, 0, 0, 0 }, // P10
      { 0, 0, 0, 0, 0, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0 }, // P11
      { 0, 0, 0, 0, 0, 0, 0, 1, 1, -1, -1, 0, 0, 0, 0 }, // P12
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 1, 1, 0, 0 }, // P13
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 0, 0, 0 }, // P14
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 0, 0 }, // P15
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, -1, 0 }, // P16
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1 }, // P17
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1 } // P18
  };

  private int matrizIvariantesTransicion[][] = {
       //T0->T1->T3->T5->T7->T9->T11->T13->T14
      { 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1 }, //1

      //T0->T1->T3->T5->T7->T10->T12->T13->T14
      { 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1 }, //2

      //T0->T1->T3->T6->T8->T9->T11->T13->T14
      { 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1 }, //3

      //T0->T1->T3->T6->T8->T10->T12->T13->T14
      { 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1 }, //4

      //T0->T2->T4->T5->T7->T9->T11->T13->T14
      { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1 }, //5

      //T0->T2->T4->T5->T7->T10->T12->T13->T14
      { 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1 }, //6

      //T0->T2->T4->T6->T8->T9->T11->T13->T14
      { 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1 }, //7

      //T0->T2->T4->T6->T8->T10->T12->T13->T14
      { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1 }  //8
  };

  //TODO: no se usa
  private int matrizInvariantesPlaza[][] = {
      { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0 },
      { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
      { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
  };

  private double[][] marcadoActual = {
      { 0, 1, 0, 3, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 0, 1 } // P0-18
  };

  private double[][] transicionesSensibilizadas = {
      { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } // T0-14
  };
  
  private int[][] contadorInvariantes = {
    { 0, 0, 0, 0, 0, 0, 0, 0 } //contador de cada invariante completado
  };

  private final RealMatrix matrizIncidenciaEntradaMatrix = MatrixUtils.createRealMatrix(matrizIncidenciaEntrada);
  private final RealMatrix matrizIncidenciaSalidaMatrix = MatrixUtils.createRealMatrix(matrizIncidenciaSalida);
  private final RealMatrix incidencia = matrizIncidenciaSalidaMatrix.copy().subtract(matrizIncidenciaEntradaMatrix);
  
  private RealMatrix marcadoActualMatrix = MatrixUtils.createRealMatrix(marcadoActual);
  private RealMatrix transicionesSensibilizadasMatrix = MatrixUtils.createRealMatrix(transicionesSensibilizadas);
  
  private Log log;
  
  //transiciones temporizadas T0, T3, T4, T7, T8, T11, T12, T14
  private long  timeStamps[]={0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  //private long alfa[] = {10, 0, 0, 10, 10, 0, 0, 20, 20, 0, 0, 20, 20, 0, 10};
  //private long beta[] = {5000, 0, 0, 5000, 5000, 0, 0, 5000, 5000, 0, 0, 5000, 5000, 0, 5000};
  private long alfa[] = {5, 0, 0, 5, 5, 0, 0, 10, 10, 0, 0, 10, 10, 0, 5};
  private long beta[] = {5000, 0, 0, 5000, 5000, 0, 0, 5000, 5000, 0, 0, 5000, 5000, 0, 5000};
  private boolean conTiempo = false;
  private String secuenciaDisparos = "";
  private int contadorDisparosTotales = 0;
  private boolean esperando[] = new boolean[15];
  public RedDePetri(Log log) {
	  //establecer timestamp inicial
	  //llamar al método setNuevoTimeStamp()
    Arrays.fill(esperando, false);
    this.actualizarTimeStamp(null, null);
    this.log = log;
    // transicionesSensibilizadas = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  }
  
  public void setConTiempo() {
    conTiempo = true;
  }

  public void setSinTiempo() {
    conTiempo = false;
  }
  
 //ya no se usa éste método disparar
 //TODO: borrar luego de calcular lo necesario
  public Boolean dispararTransicionOriginal(int transicion) {    
    // ACTUALIZAR EL MARCADO ACTUAL Y ACTUALIZAR LAS TRANSICIONES SENSIBILIZADAS
    RealMatrix sensibilizadasCopy;
    if (isSensibilizada(transicion)		) {
      RealMatrix misTransicionesDisparadas = MatrixUtils.createRealMatrix(incidencia.getColumnDimension(),1);
      misTransicionesDisparadas.setEntry(transicion,0,1);
      sensibilizadasCopy = getTransicionesSensibilizadas();
      marcadoActualMatrix = marcadoActualMatrix.add(((incidencia.copy()).multiply(misTransicionesDisparadas)).transpose());
      actualizarTimeStamp( sensibilizadasCopy, this.getTransicionesSensibilizadas());
      return true;
    }
    return false;
    /*
     * mk= mi+W.S
     * mk: proximo marcado
     * mi: marcado actual
     * w: matriz incidencia
     * s: mis transiciones disparadas
     */
  }
  
  public Boolean dispararTransicionConTiempo(int transicion, Semaphore mutexMonitor) {
		RealMatrix sensibilizadasCopy;
		if (getContadorTotalInvariantes() < 200 && isSensibilizada(transicion)	) {
    // transicionesSensibilizadas[transicion] = 0; //actualiza el marcado de la
    // transicion
    long tiempoActual = System.currentTimeMillis();
    if(testVentanaTiempo(tiempoActual, transicion)) {
      //está en ventana de tiempo.
      if(!esperando[transicion]) {
	    			//setear el nuevo timestamp
        this.setNuevoTimeStamp(transicion);
        RealMatrix misTransicionesDisparadas = MatrixUtils.createRealMatrix(incidencia.getColumnDimension(),1);
        misTransicionesDisparadas.setEntry(transicion,0,1);
        sensibilizadasCopy = getTransicionesSensibilizadas();
        marcadoActualMatrix = marcadoActualMatrix.add(((incidencia.copy()).multiply(misTransicionesDisparadas)).transpose());
        String marcadoAcString = Arrays.toString(marcadoActualMatrix.getRow(0));
        if (!chequearInvariantesPlaza()){
          log.escribirArchivo("Error invariantes plaza " + marcadoAcString);
        }
        //hay que actualizar las marcas de tiempo de las transiciones que cambiaron de sensibilización 
        actualizarTimeStamp( sensibilizadasCopy, this.getTransicionesSensibilizadas());
        secuenciaDisparos += "T" + transicion;
        contadorDisparosTotales++;
        return true;
      } else {
        //está esperando
        if (transicion == 0){
        //  log.escribirArchivo("devolví false porque está esperando");
        }
        return false;
      }
    } else {
	    		//si es menor que alfa, seteo esperando en true, lo duermo lo que le falta (timestamp+alfa-ahora). 
	    		//si es mayor que beta, ya
      if(antesDeLaVentana(tiempoActual,transicion)) {
        setEsperando(transicion);
        long tiempoDormir = this.timeStamps[transicion] + alfa[transicion] - tiempoActual;
        try {
          TimeUnit.MILLISECONDS.sleep(tiempoDormir);
          resetEsperando(transicion);
          RealMatrix misTransicionesDisparadas = MatrixUtils.createRealMatrix(incidencia.getColumnDimension(),1);
          misTransicionesDisparadas.setEntry(transicion,0,1);
          sensibilizadasCopy = getTransicionesSensibilizadas();
          marcadoActualMatrix = marcadoActualMatrix.add(((incidencia.copy()).multiply(misTransicionesDisparadas)).transpose());
          String marcadoAcString = Arrays.toString(marcadoActualMatrix.getRow(0));
          if (!chequearInvariantesPlaza()){
            log.escribirArchivo("Error invariantes plaza " + marcadoAcString);
          }
          //hay que actualizar las marcas de tiempo de las transiciones que cambiaron de sensibilización 
          actualizarTimeStamp( sensibilizadasCopy, this.getTransicionesSensibilizadas());
          secuenciaDisparos+="T"+transicion;
          contadorDisparosTotales++;
          return true;
				} catch(InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          if(transicion==0){
            // log.escribirArchivo("devolvi false porque hubo una excepción");
          }
          return false;
				}
      } else {
          if(transicion == 0){
            //log.escribirArchivo("devolvi false porque no esta antes de la ventana, tiempos: timestamp T0: "+(timeStamps[transicion]+alfa[transicion])+" fin de ventana: "+(timeStamps[transicion]+alfa[transicion])+
            //"tiempo actual: "+tiempoActual);
          }
          return false;
        }
      }
    }
    if(transicion == 0){
      //log.escribirArchivo("devolvi false porque habia terminado o no estaba sensibilizada");
    }
    return false;
	     // mk= mi+W.S
	     // mk: proximo marcado
	     // mi: marcado actual
	     // w: matriz incidencia
	     //s: mis transiciones disparadas
  }
  
  public String getSecuenciaDisparos() {
    return secuenciaDisparos;
  }

  public int getDisparosTotales() {
    return contadorDisparosTotales;
  }

  public boolean testVentanaTiempo(long actual, int transicion) {
	//alfa<ahora-timestamp<beta
	  //obtiene el instantnow
	  //tiene que verificar si el tiempo actual está entre el alpha y beta asociado a la transición
    if(this.conTiempo) {
      if(transicion == 1 || transicion == 2 || transicion == 5 || transicion == 6 || transicion == 9 || transicion == 10 || transicion == 13)
        {return true;}
      return ((actual >= (this.timeStamps[transicion] + alfa[transicion])) && (actual <= (timeStamps[transicion] + beta[transicion])));
    }else {
      return true;
    }
  }

  public void setNuevoTimeStamp(int transicion) {
    this.timeStamps[transicion] = System.currentTimeMillis();
  }

	public void actualizarTimeStamp(RealMatrix previoDisparo, RealMatrix posteriorDisparo) {
		if (previoDisparo != null && posteriorDisparo != null) {
			for(int i = 0; i < previoDisparo.getColumnDimension(); i++) {
				if (posteriorDisparo.getEntry(0, i) - previoDisparo.getEntry(0, i) == 1) {
					setNuevoTimeStamp(i);
				}
			}
		} else {
			//lleno con la marca actual de tiempo
			for(int i = 0 ; i < this.timeStamps.length; i++) {
				if(this.isSensibilizada(i)) {
					setNuevoTimeStamp(i);
				}
			}
		}
  }

	public boolean antesDeLaVentana(long actual, int transicion) {
		return actual < (this.timeStamps[transicion] + alfa[transicion]);
	}
	
	public void setEsperando(int transicion) {
		this.esperando[transicion] = true;
	}

	public void resetEsperando(int transicion) {
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
  
  private boolean comprobarInvariante(int posInvariante,Imagen imagen ) {
    ArrayList<Integer> invariante = imagen.getInvariante();
    ///0->2->4->5->7->9->11->13->14->
    for (int i = 0; i < invariante.size(); i++) {
      if (matrizIvariantesTransicion[posInvariante][invariante.get(i)] != 1) {
        return false;
      }
    }
    return true;
  }

  public void logearInvariantes() {
    for (int j = 0; j < contadorInvariantes[0].length; j++) {
      int valor = contadorInvariantes[0][j];
      String texto = "El invariante: " + (j + 1) + " se completó " + valor + " veces.";
      
      log.escribirArchivo(texto);
    }
    return;
  }

  public void actualizarContadorInvariante(Imagen imagen){
    try {
      for (int i = 0; i < matrizIvariantesTransicion.length; i++) {
        if (comprobarInvariante(i, imagen)) {
          contadorInvariantes[0][i]++;
          break;
        }
      }
    }
    catch(Exception ex){
      System.err.println("Ocurrió un error inesperado: " + ex.getMessage());
      //TODO: throw exception
    }
  }

  private Boolean isSensibilizada(int transicion) {
    transicionesSensibilizadasMatrix = getTransicionesSensibilizadas();
    return transicionesSensibilizadasMatrix.getEntry(0, transicion) == 1;
  }

  public int getCantidadTransiciones() {
    return incidencia.getColumnDimension();
  }

  public int getCantidadPlazas() {
    return incidencia.getRowDimension();
  }

  public double[] getTransicion(int numero) {
    return incidencia.getColumn(numero);
  }

  public double[] getPlaza(int numero) {
    return incidencia.getRow(numero);
  }
  
  public double getCantidadTokensPlaza(int plaza) {
    return marcadoActualMatrix.getEntry(0, plaza);
  }

  public int getContadorTotalInvariantes() {
    int total = 0;
    for (int i = 0; i < contadorInvariantes[0].length; i++) {
        total += contadorInvariantes[0][i];
    }
    return total;
  }

  public String getMarcadoComoString() {
    String marcado = "P0: " + getCantidadTokensPlaza(0) + " P2: " + getCantidadTokensPlaza(2) + " P4: " + getCantidadTokensPlaza(4) + " P6: "
    + getCantidadTokensPlaza(6) + " P8: " + getCantidadTokensPlaza(8) + " P10: " + getCantidadTokensPlaza(10) + " P12: "
    + getCantidadTokensPlaza(12) + " P14: " + getCantidadTokensPlaza(14) + " P15: " + getCantidadTokensPlaza(15) + " P16: " + getCantidadTokensPlaza(16);
    return marcado;
  }

  public boolean[] getEsperando() {
    return esperando;
  }

  public boolean chequearInvariantesPlaza() {
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
}
