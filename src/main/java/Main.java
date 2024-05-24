import java.util.Scanner;
import java.util.ArrayList;

  public class Main {
  private static final int NUM_HILOS = 14;
  
  
  public static void main(String[] args) {
    //Hilo hilo = new Hilo("Hilo");
    private int contador = 1;
    private ArrayList<Thread> hilos = new ArrayList<Thread>();
    
    Thread tlog = new Thread("hiloLog");
    

    for(int i = 0; i < NUM_HILOS; i++){
      hilos.add(newThread("hilo - " + contador));
      contador++;
      hilos.get(hilos.size()-1).start();
    }
    
    Log log = new Log("log.txt", System.currentTimeMillis(), tlog);

    Politica politica = elegirPolitica();
    
  }

  private Politica elegirPolitica(){
    Scanner scanner = new Scanner(System.in);
    System.out.println("Elige un numero de politica: \n");
    System.out.println("1. Balanceada \n");
    System.out.println("2. Izquierda favorecida \n");
    int numPolitica = scanner.nextInt();
    scanner.close();
    if (numPolitica == 1) return new Politica(balanceada);
    else return new Politica(izquierdaFavorecida);
    //falta manejo de errores
  }

}