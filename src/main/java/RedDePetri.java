import org.apache.commons.math3.linear.*;

/**
*  Clase Red de Petri a representar
*  
*  - Real Matrix - double[][] matrizIncidencia 
*  - int[] marcadoInicial
*  - int[] marcadoActual
*  - int[] transicionesSensibilizadas
*/
public class RedDePetri {
   private double matrizIncidenciaEntrada[][]={
     // T1  T2  T3  T4  T5  T6  T7  T8  T9  T10 T11 T12 T13 T14
        { 1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P0
        { 1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P1
        { 0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P2
        { 1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0}, //P3
        { 0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P4
        { 0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P5
        { 0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0}, //P6
        { 0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P7
        { 0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0}, //P8
        { 0,  0,  0,  0,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0}, //P9
        { 0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0}, //P10
        { 0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0}, //P11
        { 0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0}, //P12
        { 0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  0,  0,  0,  0}, //P13
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  0}, //P14
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0}, //P15
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0}, //P16
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1}, //P17
        { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0}  //P18
  };
  
   private double matrizIncidencia[][] = {
    // T1  T2  T3  T4  T5  T6  T7  T8  T9  T10 T11 T12 T13 T14
      {-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1}, //P0
      {-1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P1
       {1,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P2
      {-1, -1,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0, -1,  1}, //P3
       {0,  1,  0, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P4
       {0, -1,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}, //P5
       {0,  0,  1,  1, -1, -1,  0,  0,  0,  0,  0,  0,  0,  0}, //P6
       {0,  0,  0,  0, -1,  0,  1,  0,  0,  0,  0,  0,  0,  0}, //P7
       {0,  0,  0,  0,  1,  0, -1,  0,  0,  0,  0,  0,  0,  0}, //P8
       {0,  0,  0,  0, -1, -1,  1,  1,  0,  0,  0,  0,  0,  0}, //P9
       {0,  0,  0,  0,  0,  1,  0, -1,  0,  0,  0,  0,  0,  0}, //P10
       {0,  0,  0,  0,  0, -1,  0,  1,  0,  0,  0,  0,  0,  0}, //P11
       {0,  0,  0,  0,  0,  0,  1,  1, -1, -1,  0,  0,  0,  0}, //P12
       {0,  0,  0,  0,  0,  0,  0,  0, -1, -1,  1,  1,  0,  0}, //P13
       {0,  0,  0,  0,  0,  0,  0,  0,  1,  0, -1,  0,  0,  0}, //P14
       {0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0, -1,  0,  0}, //P15
       {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1, -1,  0}, //P16
       {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1, -1}, //P17
       {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1,  1}  //P18
  };

  private int matrizIvariantesTransicion[][] = {
    {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1},
    {0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
    {0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1},
    {0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
    {1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1},
    {1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
    {1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1},
    {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1}
  };

  private int matrizInvariantesPlaza[][] = {
    {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0},
    {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
    {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
  };
  
  private final double[][] marcadoInicial = {
    {1, 1, 0, 3, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 0, 1} //P0-18
  }; 
  
  private double[][] marcadoActual = {
    {1, 1, 0, 3, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 0, 1} //P0-18
  };
  
  private double[][] transicionesSensibilizadas = {
    {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0} //T1-14
  };
  
  private final RealMatrix marcadoInicialMatrix = MatrixUtils.createRealMatrix(marcadoInicial);
  private final RealMatrix matrizIncidenciaEntradaMatrix = MatrixUtils.createRealMatrix(matrizIncidenciaEntrada);
  private final RealMatrix incidencia = MatrixUtils.createRealMatrix(matrizIncidencia);
  
  private RealMatrix marcadoActualMatrix = MatrixUtils.createRealMatrix(marcadoActual);
  private RealMatrix transicionesSensibilizadasMatrix = MatrixUtils.createRealMatrix(transicionesSensibilizadas);
  
  public RedDePetri(){
    //transicionesSensibilizadas = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  }


  public void dispararTransicion(int transicion){
    //ACTUALIZAR EL MARCADO ACTUAL Y ACTUALIZAR LAS TRANSICIONES SENSIBILIZADAS
    
    //transicionesSensibilizadas[transicion] = 0;  //actualiza el marcado de la transicion
    RealMatrix transicionADisparar = getTransicionMatrix(transicion);
    
    for(int i = 0; i < getCantidadPlazas(); i++){
      marcadoActualMatrix.getColumnMatrix(i).add(incidencia.multiply(transicionesADisparar));
      //marcadoActual[i] = marcadoActual[i] + incidencia.multiply(transicionesADisparar);
      /*if(transicionADisparar[i] == 1){
        marcadoActual[i]++;
      }
      if(transicionADisparar[i] == -1){
        marcadoActual[i]--;
      }*/
    }
   
    /* 
    mk= mi+W.S
     mk: proximo marcado
     mi: marcado actual
     w: matriz incidencia 
     s: mis transiciones disparadas
    */
  }
  
  private RealMatrix getTransicionesSensibilizadas(){
    int aux_p;
    int cant_t=transicionesSensibilizadasMatrix.getColumnDimension();
    int cant_p=marcadoActualMatrix.getColumnDimension();
    RealMatrix transicionesSensibilizadasMatrix;
    
    for(int i=0;i<cant_t;i++){
      aux_p=0;
      while(aux_p<cant_p && (matrizIncidenciaEntradaMatrix.getEntry(i,aux_p)==0)|| (marcadoActualMatrix.getEntry(0,aux_p)-matrizIncidenciaEntradaMatrix.getEntry(i,aux_p))>=0){
        aux_p++;
      }
      if(aux_p==cant_p){
        transicionesSensibilizadasMatrix.setEntry(0,i,1);
      }else{
        transicionesSensibilizadasMatrix.setEntry(0,i,0);
      }
    }
    return transicionesSensibilizadasMatrix;
  }

  public Boolean isSensibilizada(int transicion){
    transicionesSensibilizadasMatrix = getTransicionesSensibilizadas();
    
    return transicionesSensibilizadasMatrix.getEntry(0,transicion) == 1;
  }

  public int[] getMarcadoActual(){
    return marcadoActual;
  }

  public int getCantidadTransiciones(){
    return incidencia.getColumnDimension();
  }
  
  public int getCantidadPlazas(){
    return incidencia.getRowDimension();
  }

  public double[] getTransicion(int numero){
    return incidencia.getColumn(numero);
  }

  private RealMatrix getTransicionMatrix(int numero){
    return incidencia.getColumnMatrix(numero);
  }

  public double[] getPlaza(int numero){
    return incidencia.getRow(numero);
  }
}
