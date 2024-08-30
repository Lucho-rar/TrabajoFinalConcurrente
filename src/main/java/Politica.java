
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
  /*


  Set<Integer> miarray=new Set[3];
  

  private Set<Integer>[] SegDer = new Set[3];
    {
        SegDer[0] = new HashSet<>(Arrays.asList(2, 4));
        SegDer[1] = new HashSet<>(Arrays.asList(6, 8));
        SegDer[2] = new HashSet<>(Arrays.asList(10, 12));
    }
  */
 /*
 ArrayList<Set<Integer>> list = new ArrayList<>();
list.add(Set.of(1, 3));
list.add(Set.of(5, 7));
list.add(Set.of(9, 11));

Set<Integer> set1 = new HashSet<>();
set1.add(1);
set1.add(3);
SegIzq.add(set1);

Set<Integer> set2 = new HashSet<>();
set2.add(5);
set2.add(7);
SegIzq.add(set2);

Set<Integer> set3 = new HashSet<>();
set3.add(9);
set3.add(11);
SegIzq.add(set3);


private Integer[][] contadorIzq = new Integer[3][2];

 */
  private Integer[][] contadorIzq = new Integer[3][2];
  private Integer[][] contadorDer = new Integer[3][2];
  private ArrayList<Set<Integer>> segUltimo=new ArrayList<Set<Integer>>();
  private Integer[] contadorUltimoSegmento = new Integer[3];
  
  public Politica(){
    
    this.tipoDePolitica = elegirPolitica();
    //foreach(int i in contadorIzq)i=0;
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
    /*
    for (Integer[] row : contadorIzq) {
    for (int i = 0; i < row.length; i++) {
        row[i] = 0;
    }
}
    */
    
    SegIzq.add(new HashSet<>(Arrays.asList(1, 3)));
    SegIzq.add(new HashSet<>(Arrays.asList(5, 7)));
    SegIzq.add(new HashSet<>(Arrays.asList(9, 11)));

    SegDer.add(new HashSet<>(Arrays.asList(2, 4)));
    SegDer.add(new HashSet<>(Arrays.asList(6, 8)));
    SegDer.add(new HashSet<>(Arrays.asList(10, 12)));

    segUltimo.add(new HashSet<>(Arrays.asList(0, 13, 14)));
  }
  /*
  public Politica (int tipoDePolitica) {
    this.tipoDePolitica = tipoDePolitica;
    foreach(int i in contadorIzq)i=0;
    foreach(int i in contadorDer)i=0;
  }
  */
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
  
  /*
  public int getContadorSegmento(int segmento){
    
    1-si el segmento es 0, retorna el contador de la transicion cero ya que no es parte del último segmento
    2-si pide segmento 7, devuelve la suma de los contadores de 13 y 14
    
  
    switch(segmento ){
        case 0: return contadorUltimoSegmento[0];
        case 1: return contadorIzq[0][0]+contadorIzq[0][1];
        case 2: return contadorDer[0][0]+contadorDer[0][1];
        case 3: return contadorIzq[1][0]+contadorIzq[1][1];
        case 4: return contadorDer[1][0]+contadorDer[1][1];
        case 5: return contadorIzq[2][0]+contadorIzq[2][1];
        case 6: return contadorDer[2][0]+contadorDer[2][1];
        case 7: return contadorUltimoSegmento[1]+contadorUltimoSegmento[2];
        default: return -1;
    }
    
  }
  */
  public int elegirPolitica(){
    int salida;
    Scanner scanner = new Scanner(System.in);
    System.out.println("Elige un numero de politica: \n");
    System.out.println("1. Balanceada \n");
    System.out.println("2. Izquierda favorecida \n");
    salida = scanner.nextInt();
    scanner.close();
    
    //falta manejo de errores
    return salida;
  }

  public int cual(double[] m){
    switch(tipoDePolitica){
        case 1: return balanceada(m);
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
    //int tamano=m.length;
    //int posicionM=0;
    //List<ArrayList<Integer>> lista= new ArrayList<ArrayList<Integer>>();
    /*System.out.println();
    System.out.println(Arrays.toString(m));
    System.out.println();*/
    List<ArrayList<Integer>> lista = new ArrayList<>();
    //posicion 0 van izq, posicion 1 van derecho, posicion 2 van ultimo
    lista.add(new ArrayList<Integer>());
    lista.add(new ArrayList<Integer>());
    lista.add(new ArrayList<Integer>());

    for (int i = 0; i < m.length; i++){
      if(m[i] == 1){
        if(i == 14 || i == 0 || i == 13){
          lista.get(2).add(i);
        } else if (i % 2 == 0){
          lista.get(0).add(i);
        } else {
          lista.get(1).add(i);
        }
      }
    }   
     //elimina los segmentos que no están sensiblizados (iz, der o ult)


    // for (double sensibilizada : m){
    //   if(m[(int)sensibilizada] == 1){
    //     if(sensibilizada == 14 || sensibilizada == 0 || sensibilizada == 13){
    //       lista.get(2).add((int)sensibilizada); //ultima/primera
    //     }else if(sensibilizada%2==0){
    //       lista.get(0).add((int)sensibilizada); //derecha
    //     }else{
    //       lista.get(1).add((int)sensibilizada); //izquierda
    //     }
    //   }
    // }
    int pos=0;
    while(pos<lista.size()){ //obtener lista depurada, 
     
      if(lista.get(pos).size()==0){
        lista.remove(pos);
      }else{
         pos++;
      }
    }

    Random random = new Random();
    int listSize =0 ;
    // System.out.printf("lista.size() %s\n",lista.size());
    if (lista.size() == 0){
      listSize = 1;
    }else{
      listSize = lista.size();
    }
    int eleccionTipoSegmento=random.nextInt(listSize); //[0,list.size())
    // System.out.printf("eleccionTipoSegmento %s\n",eleccionTipoSegmento);
    int posTransicion=(lista.get(eleccionTipoSegmento)).size();
    // System.out.printf("postransicion ANTES %s\n",posTransicion);
    posTransicion=random.nextInt(posTransicion);
    // System.out.printf("postransicion DESPUES %s\n",posTransicion);
    return lista.get(eleccionTipoSegmento).get(posTransicion);
  }
  
  private int izquierdaFavorecida(double[] m){
    int elegido = balanceada(m);
    
    if(elegido == 9 || elegido == 10){
      if(Math.random()>0.8){
        return 10;
      }else{
        return 9;
      }
    }

    return elegido;
  }
/*    int tamano=m.size();
    int posicionM=0;
    List<ArrayList<int>> lista=new List<ArrayList<int>>();
    //posicion 0 van izq, posicion 1 van derecho, posicion 2 van ultimo
    lista.add(new ArrayList<int>());
    lista.add(new ArrayList<int>());
    lista.add(new ArrayList<int>());
    for (double sensibilizada : m){
      if(m[sensibilizada] == 1){
        if(sensibilizada == 14 || sensibilizada == 0 || sensibilizada == 13){
          lista.get(2).add(sensibilizada); //ultima/primera
        }else if(sensibilizada%2==0){
          lista.get(0).add(sensibilizada); //derecha
        }else{
          lista.get(1).add(sensibilizada); //izquierda
        }
      }
    }
    //elimina los segmentos que no están sensiblizados (iz, der o ult)
    int pos=0;
    while(pos<lista.size()){ //obtener lista depurada, 

      if(lista.get(pos).size()==0){
        lista.remove(pos);
      }else{
         pos++;
      }
    }


    
    if(Math.Random()>0.8){
      //derecha

    }else{
      //izquierda
    }*/
  
}
