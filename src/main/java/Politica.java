
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Politica {
  private int tipoDePolitica;
  //private Set<Integer>[] SegIzq={{1,3},{5,7},{9,11}}; //SA(1) SC(3) Y SE(5)
  private ArrayList<Set<Integer>> SegIzq = new ArrayList<>();
  //private Set<Integer>[] SegIzq = new Set<Integer>[3];
  // private Set<Integer>[3] SegDer={{2,4},{6,8},{10,12}}; //SB(2) SD(4) Y SF(6)
  private ArrayList<Set<Integer>> SegDer = new ArrayList<>();
  
  private Integer[][] contadorIzq = new Integer[3][2];
  private Integer[][] contadorDer = new Integer[3][2]; 
  private Integer[] contadorUltimoSegmento = new Integer[3];
  private ArrayList<Set<Integer>> segUltimo=new ArrayList<Set<Integer>>();
  
  
  private int contador_decisiones=0;
  private int decisionT9=0;
  private int decisionT10=0;
  private boolean conTiempo; 
  private  Scanner scanner;
  private boolean t1_antes_q_t2=true;
  private boolean t5_antes_q_t6=true;
  private boolean t9_antes_q_t10=true;
  
  public Politica(){

    for (Integer[] row : contadorIzq) {
      for (int i = 0; i < row.length; i++) {
        row[i] = 0;
      }
    }
    // foreach(int i in contadorDer)i=0;
    for (Integer[] row : contadorDer) {
      for (int i = 0; i < row.length; i++) {
          row[i] = 0;
      }
    }

    for (int i = 0; i < contadorUltimoSegmento.length; i++) {
        contadorUltimoSegmento[i] = 0;
    }
  
    
    SegIzq.add(new HashSet<>(Arrays.asList(1, 3)));
    SegIzq.add(new HashSet<>(Arrays.asList(5, 7)));
    SegIzq.add(new HashSet<>(Arrays.asList(9, 11)));

    SegDer.add(new HashSet<>(Arrays.asList(2, 4)));
    SegDer.add(new HashSet<>(Arrays.asList(6, 8)));
    SegDer.add(new HashSet<>(Arrays.asList(10, 12)));

    segUltimo.add(new HashSet<>(Arrays.asList(0, 13, 14)));
    
   
    
  }
  public boolean getConTiempo() {
	  return conTiempo;
  }
  
  public void setConTiempo(boolean t) {
	  conTiempo=t;
  }
  
  public void setTipoPolitica(int t) {
	  this.tipoDePolitica=t;
  }
  
  public int getContador_decisiones() {
	  return contador_decisiones;
  }
  public void setContador_decisiones(int c) {
	  contador_decisiones=c;
  }
  
  public void actualizarContadorTransicion(int transicion){
    switch(transicion){
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
  /*public boolean getTiempo() {
	  return this.tiempo;
  }*/
  public int getContadorTransicion(int transicion){
    switch(transicion){
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
  
  public boolean elegirTipoTiempo(){
	    int salida;
	    scanner.reset();
	   
	    System.out.println("Elige ejecución con o sin tiempo: \n");
	    System.out.println("1. Con tiempo \n");
	    System.out.println("2. Sin tiempo \n");
	    salida = scanner.nextInt();
	    	    
	    System.out.println("mostrar scannertipotiempo:"+salida);
	    //falta manejo de errores
	    return salida==1;
	  }

  
  public int elegirPolitica(){
	  scanner.reset();
    int salida;
    System.out.println("Elige un numero de politica: \n");
    System.out.println("1. Balanceada \n");
    System.out.println("2. Izquierda favorecida \n");
    salida = scanner.nextInt();
    System.out.println("mostrar scannerpolitica:"+salida);
    //scanner.close();
    //falta manejo de errores
    return salida;
  }


  public int cual(double[] m){
	  
	    switch(tipoDePolitica){
	        case 1: return  balanceada(m);
	        case 2: return izquierdaFavorecida(m);
	      default: return -1;
	    }
	  }
  
  
 

  private int balanceada(double[] m){
    //Hay que determinar cuál es la transición que va a devolver cual(), 
    //m[] contiene las transiciones habilitadas por la rdp, la posición es el nro de transicion
    //
    /*Otra posibilidad puede ser:
      1- armar una lista de posiciones en m[] (las que no son cero)
      2- elegir una posición al azar de la lista
      3- fijarse si pertenece a un segmento desbalanceado
      4- si pertenece a un segmento desbalanceado, lo retorno y termino el metodo
      5- sino, lo saco de la lista y vuelvo al paso 2
     *Otra posibilidad puede ser:
      0-generar un aleatorio entre los distintos segmentos, para saber cuál dispararía
      1- generamos un aleatorio para saber si disparará el segmento izquierdo o el derecho
      2-

      *Otra posibilidad puede ser:
      1- armar una lista depurada con los elementos sensibilizados de m
      2-tener un ranking de segmentos desbalanceados (el más desbalanceado va primero)
      3-si puedo disparar el segmento más desbalanceado, devuelvo la transición. 
      4 si no, elijo  el siguiente segmento en el ranking y vuelvo a 3

      *OTRA:
      1- crear una lista depurada de segmentos elimnando las transiciones que no están sensiblizados. Para ver qué tipo de segmento existen (derecho, izquierdo o ultimo/primero)
      2- elegir entre ir por segmento izquierdo, derecho, o ultimo con random según 1.      
      3-de la lista con las transiciones elegidas, elijo una aleatoriamente y la devuelvo. 
        
       
       
    */
    
    //List<ArrayList<Integer>> lista = new ArrayList<>();
    //posicion 0 van izq, posicion 1 van derecho, posicion 2 van ultimo
    int salida=0;
   
    Random random1 = new Random();
    if(m[9]==1&&m[10]==0) {
    	m[9]=0;
    	 
    }
    if(m[9]==0&&m[10]==1) {
    	m[10]=0;
    }
    ArrayList<Integer> mFiltrada = filtrarTransiciones(m);
    
    salida=random1.nextInt(mFiltrada.size());
    
    if((mFiltrada.get(salida)==1 || mFiltrada.get(salida)==2)&&(m[1]==1&&m[2]==1)){
      if(t1_antes_q_t2) {
        t1_antes_q_t2=false;
        return 1;
      }else {
        t1_antes_q_t2=true;
        return 2;
      }
    }

    if((mFiltrada.get(salida)==5 || mFiltrada.get(salida)==6)&&(m[5]==1&&m[6]==1)){
      if(t5_antes_q_t6) {
        t5_antes_q_t6=false;
        return 5;
      }else {
        t5_antes_q_t6=true;
        return 6;
      }
    }
    
    if((mFiltrada.get(salida)==9 || mFiltrada.get(salida)==10)&&(m[9]==1&&m[10]==1)){
      if(t9_antes_q_t10) {
        t9_antes_q_t10=false;
        return 9;
      }else {
        t9_antes_q_t10=true;
        return 10;
      }
    }
   
   return mFiltrada.get(salida);
  } 
  public int getDecisionT9() {
	  return decisionT9;
  }
  public int getDecisionT10() {
	  return decisionT10;
  }
 private int izquierdaFavorecida(double[] m) {
	 if(m[9]==1&&m[10]==1) {
		 if(this.deboFavorecerIzquierda(9)) {
			 return 9;
		 }else {
			 return 10;
		 }
	 }else {
		 return  balanceada(m);
	 }
	 
}

  
  
  private boolean deboFavorecerT1SobreT2(int salida){
    float contador1 = (float) getContadorTransicion(1);
    float contador2 = (float) getContadorTransicion(2);
    
    
    // check division por cero
    if (contador1 + contador2 ==0 ){
      if (salida ==1 ){
        return true;
      }else{
	      return false;        
      }
    }
    
    return (contador1 / (contador1 + contador2)) <= 0.5;
    
  }
  
  
  private boolean deboFavorecerT5SobreT6(int salida){
    float contador5 = (float) getContadorTransicion(5);
    float contador6 = (float) getContadorTransicion(6);
    
    
    //check division por cero
    if (contador5 + contador6 ==0 ){
      if (salida == 5){
        return true;
      }else{
        return false;
      }
      
    }
    
    return (contador5 / (contador5 + contador6)) <= 0.5;
    
    
  }
  
  private boolean deboFavorecerT9SobreT10(int salida){
    float contador9 = (float) getContadorTransicion(9);
    float contador10 = (float) getContadorTransicion(10);
    
    
    //check division por cero
    if (contador9 + contador10 ==0 ){
      if(salida == 9){
        return true;
      }else{
	      return false;        
      }
    }
    
    return (contador9 / (contador9 + contador10)) <= 0.5;
    
    
  }
  
  
  
  
  private boolean deboFavorecerIzquierda(int salida) {
    float contador9 = (float) getContadorTransicion(9);
    float contador10 = (float) getContadorTransicion(10);

    
    // Evitar división por cero
    if (contador9 + contador10 == 0) {
      if(salida==9){
        return true;
      }
        return false; // O alguna lógica predeterminada
    }

    return (contador9 / (contador9 + contador10)) <= 0.8;
}



  private boolean multiplesEsperando(double[] m){
	  //es para que no genere stackoverflow 
    int esperandosensibilizadas = 0;
     for (int i = 0; i < m.length; i++) {
      if (m[i] == 1.0) {
        esperandosensibilizadas++;
      }
    }
    if(esperandosensibilizadas>1){
      return true;
    }
    return false;
    
    
    
  }
    public ArrayList<Integer> filtrarTransiciones(double[] m){
    ArrayList<Integer> lista = new ArrayList<>();
    for (int i = 0; i < m.length; i++) {
      if (m[i] == 1.0) {
        lista.add(i);
      }
    }
    return lista;
  }
}
