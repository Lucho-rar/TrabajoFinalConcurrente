import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Clase Politica
 * 
 * Esta clase se encarga de controlar la política de disparo de la red de petri
 */
public class Politica {

  private int tipoDePolitica;
  private ArrayList<Set<Integer>> SegIzq = new ArrayList<>();
  private ArrayList<Set<Integer>> SegDer = new ArrayList<>();
  private Integer[][] contadorIzq = new Integer[3][2];
  private Integer[][] contadorDer = new Integer[3][2];
  private Integer[] contadorUltimoSegmento = new Integer[3];
  private ArrayList<Set<Integer>> segUltimo = new ArrayList<Set<Integer>>();
  private boolean conTiempo;
  private  Scanner scanner;
  private boolean t1_antes_q_t2 = true;
  private boolean t5_antes_q_t6 = true;
  private boolean t9_antes_q_t10 = true;
  
  public Politica(){
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
    // Agrega los segmentos 1 - 3 - 5 - 7 - 9 - 11 a la lista de segmentos izquierdos
    SegIzq.add(new HashSet<>(Arrays.asList(1, 3)));
    SegIzq.add(new HashSet<>(Arrays.asList(5, 7)));
    SegIzq.add(new HashSet<>(Arrays.asList(9, 11)));
    // Agrega los segmentos 2 - 4 - 6 - 8 - 10 - 12 a la lista de segmentos derechos
    SegDer.add(new HashSet<>(Arrays.asList(2, 4)));
    SegDer.add(new HashSet<>(Arrays.asList(6, 8)));
    SegDer.add(new HashSet<>(Arrays.asList(10, 12)));
    // El ultimo segmento es el 13 - 14
    segUltimo.add(new HashSet<>(Arrays.asList(0, 13, 14)));
  }

  /**
   * Getter de la variable conTiempo
   * 
   * @return true si la política es con tiempo, false si no
   */
  public boolean getConTiempo() {
    return conTiempo;
  }
  
  /**
   * Setter de la variable conTiempo
   * 
   * @param t booleano que indica si la política es con tiempo o no
   */
  public void setConTiempo(boolean t) {
    conTiempo=t;
  }
  
  /**
   * Setter de la variable tipoDePolitica
   * 
   * @param t entero que indica el tipo de política
   */
  public void setTipoPolitica(int t) {
    this.tipoDePolitica=t;
  }
  
  /**
   * Actualiza el contador de transiciones
   * 
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
  
  /**
   * Método para elegir si la ejecución es con o sin tiempo
   * 
   * @return true si la ejecución es con tiempo, false si no.
   */
  public boolean elegirTipoTiempo(){
    int salida;
    scanner.reset();
    System.out.println("Elige ejecución con o sin tiempo: \n");
    System.out.println("1. Con tiempo \n");
    System.out.println("2. Sin tiempo \n");
    salida = scanner.nextInt();
    System.out.println("mostrar scannertipotiempo:" + salida);
    return salida == 1; //TODO: es siempre con tiempo?
  }

  /**
   * Método para elegir la política de disparo
   * 
   * @return 1 si la política es balanceada, 2 si es izquierda favorecida
   */
  public int elegirPolitica(){
    scanner.reset();
    int salida;
    System.out.println("Elige un numero de politica: \n");
    System.out.println("1. Balanceada \n");
    System.out.println("2. Izquierda favorecida \n");
    salida = scanner.nextInt();
    System.out.println("mostrar scannerpolitica:" + salida);
    //TODO: manejo de errores
    return salida;
  }

  /**
   * Método que chequea el tipo de política y llama a la función correspondiente
   * 
   * @param m
   * @return return de la función de política correspondiente (balanceada - izquierdaFavorecida)
   */
  public int cual(double[] m) {
    switch(tipoDePolitica) {
        case 1: return  balanceada(m);
        case 2: return izquierdaFavorecida(m);
      default: return -1;
    }
  }

  /**
   * Método que ejerce la política de disparo balanceada
   * 
   * La política consiste en ejercer el %50 entre los segmentos de la parte izquierda y derecha. Para ello:
   * 
   * - En las transiciones que definen cada segmento opuesto y que están sensibilizadas al mismo tiempo 
   *   (por ejemplo T1 y T2 para segmento A y B), se marca cual se eligió en la decisión anterior para luego elegir la otra.
   * - Si no hay "conflictos" (transiciones de segmentos opuestos sensibilizadas al mismo tiempo), se elige una transición al azar.
   * - También se vuelve a asegurar que para el caso de T9 y T10, si solo una está sensibilizada, la quita de m.
   *  
   * @param m AND de sensibilizadas y no disparadas
  */
  private int balanceada(double[] m) {
    int salida = 0;
    Random random1 = new Random();  // Random para elegir transición
    // Si T9 o T10 estan en m, me aseguro que estén si o si ambas, sino "filtro" m.
    if (m[9] == 1 && m[10] == 0) {
      m[9] = 0;
    }
    if (m[9] == 0 && m[10] == 1) {
      m[10] = 0;
    }
    ArrayList<Integer> mFiltrada = filtrarTransiciones(m);
    // Genero un numero random para elegir una transicion
    salida=random1.nextInt(mFiltrada.size());
    // %50 SEGMENTO A & SEGMENTO B
    if ((mFiltrada.get(salida) == 1 || mFiltrada.get(salida) == 2) && (m[1] == 1 && m[2] == 1)) {
      if (t1_antes_q_t2) {
        t1_antes_q_t2 = false;
        return 1;
      } else {
        t1_antes_q_t2 = true;
        return 2;
      }
    }
    // %50 SEGMENTO C & SEGMENTO D
    if ((mFiltrada.get(salida) == 5 || mFiltrada.get(salida) == 6) && (m[5] == 1 && m[6] == 1)) {
      if (t5_antes_q_t6) {
        t5_antes_q_t6 = false;
        return 5;
      } else {
        t5_antes_q_t6 = true;
        return 6;
      }
    }
    
    // %50 SEGMENTO E & SEGMENTO F
    if ((mFiltrada.get(salida) == 9 || mFiltrada.get(salida) == 10) && (m[9] == 1 && m[10] == 1)) {
      if(t9_antes_q_t10) {
        t9_antes_q_t10 = false;
        return 9;
      } else {
        t9_antes_q_t10 = true;
        return 10;
      }
    }
   // Si no hay conflictos, elijo una transición al azar
    return mFiltrada.get(salida);
  }

  /**
   * Método que ejerce la política de disparo izquierda favorecida
   * 
   * La política consiste en favorecer el segmento izquierdo. Para ello:
   * 
   * - Se centra en la decisión de los SEGMENTOS E & F (es decir T9 y 10).
   * - En caso de que AMBAS estén en m, se apoya en @deboFavorecerIzquierda para decidir cuál disparar, aplicando
   *   el %80 de disparo a la izquierda.
   * - En caso de no existir el "conflicto" de 9 y 10, se llama a la política balanceada, como para todos los demás segmentos.
  */
  private int izquierdaFavorecida(double[] m) {
   // Chequeo que T9 y T10 estén sensibilizadas al mismo tiempo
    if(m[9] == 1 && m[10] == 1) {
      if (this.deboFavorecerIzquierda(9)) {
        return 9;
      } else {
        return 10;
      }
    }else { // Sino, llamo a la política balanceada
      return  balanceada(m);
    }
  }
  
  /**
   * Método para decidir si favorecer la izquierda o no, segun el %80.
   * 
   * @param transicion transicion generada por el random en la politica.
   * @return true si se favorece la izquierda, false si no.
   */
  private boolean deboFavorecerIzquierda(int transicion) {
    float contador9 = (float) getContadorTransicion(9);
    float contador10 = (float) getContadorTransicion(10);
    // Evitar división por cero
    if (contador9 + contador10 == 0) {
      if(transicion == 9){
        return true;
      }
        return false; // TODO: ?
    }
    return (contador9 / (contador9 + contador10)) <= 0.8;
  }

  /**
   * Método que genera una lista con m "filtrada" para el caso de T9 y T10 sensibilizadas al mismo tiempo
   * 
   * @param m AND de sensibilizadas y no disparadas SIN T9 o T10 
   * @return ArrayList de transiciones sensibilizadas y no disparadas "filtradas"
   */
  private ArrayList<Integer> filtrarTransiciones(double[] m) {
    ArrayList<Integer> lista = new ArrayList<>();
    for (int i = 0; i < m.length; i++) {
      if (m[i] == 1.0) {
        lista.add(i);
      }
    }
    return lista;
  }
}
