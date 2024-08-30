import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

//import target.*;
/**
 * Clase Red de Petri a representar
 * 
 * - Real Matrix - double[][] matrizIncidencia
 * - int[] marcadoInicial
 * - int[] marcadoActual
 * - int[] transicionesSensibilizadas
 */
public class RedDePetri {
  private double matrizIncidenciaEntrada[][] = {
      // T0 T1 T2 T3 T4 T5 T6 T7 T8 T9 T10 T11 T12 T13 T14
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
      { 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1 },
      { 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1 },
      { 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1 },
      { 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1 },
      { 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1 },
      { 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1 },
      { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1 },
      { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1 }
  };

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
  /*
   * private final double[][] marcadoInicial = {
   * {1, 1, 0, 3, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 0, 1} //P0-18
   * };
   */
  private double[][] marcadoActual = {
      { 1, 1, 0, 3, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 0, 1 } // P0-18
  };

  private double[][] transicionesSensibilizadas = {
      { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } // T0-14
  };
  
  private int[][] contadorInvariantes = {
    { 2, 2, 2, 2, 1, 1, 1, 1 }, //transición en la que comienza el invariante
    { 0, 0, 0, 0, 0, 0, 0, 0 } //contador de cada invariante completado
  };
  // private final RealMatrix marcadoInicialMatrix =
  // MatrixUtils.createRealMatrix(marcadoInicial);
  private final RealMatrix matrizIncidenciaEntradaMatrix = MatrixUtils.createRealMatrix(matrizIncidenciaEntrada);
  private final RealMatrix incidencia = MatrixUtils.createRealMatrix(matrizIncidencia);

  private RealMatrix marcadoActualMatrix = MatrixUtils.createRealMatrix(marcadoActual);
  private RealMatrix transicionesSensibilizadasMatrix = MatrixUtils.createRealMatrix(transicionesSensibilizadas);

  public RedDePetri() {
    // transicionesSensibilizadas = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  }
  
  public Boolean dispararTransicion(int transicion) {
    // ODO: REVISAR PARA EL CASO DEL EXPORTADOR YA QUE ESE HILO TIENE ASOCIADO 2
    // TRANSICIONES
    // ODO: LLAMAR METODO PARA OBTENER TRANSICIONES SENSIBILIZADAS Y VER SI LA QUE
    // QUIERO DISPARAR ESTa SENSIBILIZADA
    // ODO: EL METODO DEBE DEVOLVER UN VALOR BOOLEANO QUE INDICA SI LA TRANSICION SE
    // PUDO DISPARAR O NO
    
    // ACTUALIZAR EL MARCADO ACTUAL Y ACTUALIZAR LAS TRANSICIONES SENSIBILIZADAS

    // System.out.printf("transicion a disparar: %d", transicion);
    // System.out.println();
    // System.out.println();

    if (isSensibilizada(transicion)) {
      // transicionesSensibilizadas[transicion] = 0; //actualiza el marcado de la
      // transicion
     // private final RealMatrix matrizIncidenciaEntradaMatrix = MatrixUtils.createRealMatrix(matrizIncidenciaEntrada);
      RealMatrix misTransicionesDisparadas = MatrixUtils.createRealMatrix(incidencia.getColumnDimension(),1);
      misTransicionesDisparadas.setEntry(transicion,0,1);
      
      //for (int i = 0; i < getCantidadPlazas(); i++) {
        //System.out.printf("transicion:%d   \n\r",  transicion);

        //System.out.printf("misTransicionesDisparadas (%d x %d),  \n\r", misTransicionesDisparadas.getRowDimension(),misTransicionesDisparadas.getColumnDimension());
        //System.out.printf(" marcadoActualMatrix (%d x %d),  \n\r",  marcadoActualMatrix.getRowDimension(), marcadoActualMatrix.getColumnDimension());
        //System.out.printf(" incidencia (%d x %d),  \n\r",  incidencia.getRowDimension(), incidencia.getColumnDimension());
        //[1x19]                                + [19x15] * [15x1]

        marcadoActualMatrix = marcadoActualMatrix.add(((incidencia.copy()).multiply(misTransicionesDisparadas)).transpose());
        // marcadoActual[i] = marcadoActual[i] +
        // incidencia.multiply(transicionesADisparar);
        /*
         * if(transicionADisparar[i] == 1){
         * marcadoActual[i]++;
         * }
         * if(transicionADisparar[i] == -1){
         * marcadoActual[i]--;
         * }
         */
      //}

      actualizarContadorInvariante(transicion);

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

  public RealMatrix getTransicionesSensibilizadas() {
    RealMatrix transicionesSensibilizadasMatrix = MatrixUtils.createRealMatrix(new double[1][15]);
    int aux_p;
    int cant_t = transicionesSensibilizadasMatrix.getColumnDimension();
    int cant_p = marcadoActualMatrix.getColumnDimension();
    
    transicionesSensibilizadasMatrix.setEntry(0, 0, 1);//La t0 siempre está sensibilizada porque no tiene plazas de entrada
    /*Aclaracion: i comienza en 1 porque excluimos el caso de la transicion t0 que siempre está sensibilizada
    */
    for (int i = 1; i < cant_t; i++) {
      aux_p = 0;  // aux_p no puede llegar a 19 (plazas) por la condicion del while
    
      while (aux_p < cant_p ) {
        //System.out.println(aux_p);
            //1-la plaza tiene que ser una plaza de entrada de la transicion
            //2- el marcado actual tiene que ser mayor que el peso del arco de entrada
            if(matrizIncidenciaEntradaMatrix.getEntry(aux_p, i) != 0){
              if((marcadoActualMatrix.getEntry(0, aux_p) - matrizIncidenciaEntradaMatrix.getEntry(aux_p, i)) >= 0){
                  //La transición está sensibilizada
                transicionesSensibilizadasMatrix.setEntry(0, i, 1);
              }else{
                transicionesSensibilizadasMatrix.setEntry(0, i, 0);
                break;
                }
            }

            aux_p++;

            
        //System.out.printf("aux_p %d , cant_t %d, cant_p %d, i %d\n\r",aux_p,cant_t,cant_p, i);
        // System.out.printf("matrizIncidenciaEntradaMatrix.getEntry(i, aux_t) %f\n\r",matrizIncidenciaEntradaMatrix.getEntry(i, aux_t));
        // System.out.printf("marcadoActualMatrix.getEntry(0, aux_t) %f\n\r",marcadoActualMatrix.getEntry(0, aux_t));
        
        
      }
      // System.out.printf("valor aux_p: %d", aux_p);
      // System.out.println();
     
    }

/*    for (double[] row : transicionesSensibilizadasMatrix.getData()) {  // Obtener cada fila como un array
        for (double value : row) {  // Recorrer cada valor en la fila
                System.out.print(value + " ");
            }
            // Imprimir una nueva línea después de cada fila
            System.out.println();
        }
        System.out.println();*/

    return transicionesSensibilizadasMatrix;
  }
  
  private int proximaTransicion( int invarianteTransicion, int posicionActual) throws Exception{
   int pos=posicionActual;

    while(pos<matrizIvariantesTransicion[invarianteTransicion].length && matrizIvariantesTransicion[invarianteTransicion][pos]!=1){
      pos++;
    }
    
    if(pos!=matrizIvariantesTransicion[invarianteTransicion].length){
      return pos;
    }else{
      //no lo encontró. hay error . no debería entrar aquí. 
      throw new Exception("error transicion inicial");
    }
  }

  private void actualizarContadorInvariante(int transicionDisparada){
    try{
		//Todo actualiza mal el contador de proxima transicion
      for (int i=0; i<contadorInvariantes[0].length;i++){
          if(transicionDisparada==14 && contadorInvariantes[0][i]==14){
            //System.out.println("estoy aca");
            contadorInvariantes[0][i] = proximaTransicion(i, 0);//matrizIvariantesTransicion[i][posicion donde tengo el primer 1]
            contadorInvariantes[1][i]++; //actualizo el contador de invariante
          }else if(contadorInvariantes[0][i]==transicionDisparada){
              //corresponde actualizar el recorrido de la invariante
              //System.out.println("estoy en el else if");
              contadorInvariantes[0][i] = proximaTransicion(i, contadorInvariantes[0][i]++);
          }else{
            //System.out.println("estoy en el else");
          }

        // Recorremos la matriz utilizando foreach
        // for (int[] fila : contadorInvariantes) {
        //     for (int valor : fila) {
        //         System.out.print(valor + " ");
        //     }
        //     // Imprimir una nueva línea después de cada fila
        //     System.out.println();
        // }

        // System.out.println();
      }
      /*for (double[] row : marcadoActualMatrix.getData()) {  // Obtener cada fila como un array
          for (double value : row) {  // Recorrer cada valor en la fila
                System.out.print(value + " ");
            }
            // Imprimir una nueva línea después de cada fila
            System.out.println();
        }
        System.out.println();*/
    }
    catch(Exception ex){
      System.err.println("Ocurrió un error inesperado: " + ex.getMessage()); //oDO tratar el try/catch desde la llamada en disparar transicion
      //throw ex;
      //throw ex;
    }
  }

  private Boolean isSensibilizada(int transicion) {
    transicionesSensibilizadasMatrix = getTransicionesSensibilizadas();
    return transicionesSensibilizadasMatrix.getEntry(0, transicion) == 1;
  }

  /*
   * public double[][] getMarcadoActual(){
   * return marcadoActual;
   * }
   */
  public int getCantidadTransiciones() {
    return incidencia.getColumnDimension();
  }

  public int getCantidadPlazas() {
    return incidencia.getRowDimension();
  }

  public double[] getTransicion(int numero) {
    return incidencia.getColumn(numero);
  }

  private RealMatrix getTransicionMatrix(int numero) {
    return incidencia.getColumnMatrix(numero);
  }

  public double[] getPlaza(int numero) {
    return incidencia.getRow(numero);
  }
}
