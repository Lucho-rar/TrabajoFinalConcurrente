import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    int tipoPolitica = elegirPolitica();
	  String ruta = "log";
    String ruta_regex = "log_regex";
    int contador = 1;
    int numEjecuciones = 5;
    long tiempoInicial = System.currentTimeMillis();
    long tiempoActual;
    long tiempoFinal;
    String tiempoEjecucion;
    String tiempoPromedio;
    Log log, log_regex;
    Log tiempos = new Log("tiempos.txt");
    for (int i = 1; i <= numEjecuciones; i++) {
      tiempoActual = System.currentTimeMillis();
      log = new Log(ruta + contador + ".txt");
      log_regex = new Log(ruta_regex + contador + ".txt");
      try {
        tiempos.escribirArchivo("Ejecución " + i + ": ");
          System.out.println("Ejecución " + i + ": ");
          // Llama al método principal de tu programa
          ejecucion(ruta+contador + ".txt", log, log_regex,true, tipoPolitica );
          contador++;
          // Reemplaza con el nombre de tu clase principal
          tiempos.escribirArchivo("Completada con éxito.");
          System.out.println("Completada con éxito.");
      } catch(Exception e) {
          System.out.println("Error en la ejecución " + i + ": " + e.getMessage());
          break;
      }
      tiempoFinal = System.currentTimeMillis();
      tiempoEjecucion = "Tiempo de ejecución: " + (tiempoFinal - tiempoActual) + " milisegundos.";
      tiempos.escribirArchivo(tiempoEjecucion);
      System.out.println(tiempoEjecucion);
    }
    tiempoFinal = System.currentTimeMillis();
    tiempoPromedio = "Tiempo promedio de ejecución: " + (tiempoFinal - tiempoInicial) / numEjecuciones + " milisegundos.";
    tiempos.escribirArchivo(tiempoPromedio);
    System.out.println(tiempoPromedio);
  }
    private static int elegirPolitica() {
      Scanner scanner = new Scanner(System.in);
      int salida;
      do {
        scanner.reset();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Elige un numero de politica: \n");
        System.out.println("1. Balanceada \n");
        System.out.println("2. Izquierda favorecida \n");
        salida = scanner.nextInt();
        if(!(salida==1||salida==2)){
          System.out.println("Elección incorrecta. Vuelva a elegir política. \n");
        }
      } while (!(salida == 1 || salida == 2));
      scanner.close();
      return salida;
    }

  public static void ejecucion(String ruta, Log log, Log log_regex, boolean conTiempo, int tipoPolitica) {
    ArrayList<Thread> hilos = new ArrayList<Thread>();
    FabricaDeHilos miFabrica = new FabricaDeHilos();
    RedDePetri redp = new RedDePetri(log, log_regex);
    if (tipoPolitica == 2) {
      redp.setAlfa(11, 4);
    }
    Politica politica = new Politica(redp);
    politica.setConTiempo(conTiempo);
    politica.setTipoPolitica(tipoPolitica);//2 es izquierda
    Monitor miMonitor = new Monitor(redp, politica);

    GeneradorDeImagenes generadorDeImagenes = new GeneradorDeImagenes("generadorDeImagenes", miMonitor, log, redp, 0, -1);
    
    CargaDeImagenes cargaDeImagenesIzq = new CargaDeImagenes("cargaDeImagenesIzq", miMonitor, log, redp, 1, 3);
   
    CargaDeImagenes cargaDeImagenesDer = new CargaDeImagenes("cargaDeImagenesDer", miMonitor, log, redp, 2, 4);
    
    AjustadorDeCalidad ajustadorDeCalidadIzq = new AjustadorDeCalidad("ajustadorDeCalidadIzq", miMonitor, log, redp, 5, 7);
   
    AjustadorDeCalidad ajustadorDeCalidadDer = new AjustadorDeCalidad("ajustadorDeCalidadDer", miMonitor, log, redp, 6, 8);
   
    Recortador recortadorIzq = new Recortador("recortadorIzq", miMonitor, log, redp, 9, 11);
    
    Recortador recortadorDer = new Recortador("recortadorDer", miMonitor, log, redp, 10, 12);
    
    Exportador exportador = new Exportador("exportador", miMonitor, log, redp, 13, 14);
    
    hilos.add(miFabrica.newThread(generadorDeImagenes));
    hilos.add(miFabrica.newThread(cargaDeImagenesIzq));
    hilos.add(miFabrica.newThread(cargaDeImagenesDer));
    hilos.add(miFabrica.newThread(ajustadorDeCalidadIzq));
    hilos.add(miFabrica.newThread(ajustadorDeCalidadDer));
    hilos.add(miFabrica.newThread(recortadorIzq));
    hilos.add(miFabrica.newThread(recortadorDer));
    hilos.add(miFabrica.newThread(exportador));
	
    hilos.forEach((hilo) -> hilo.start());

    for (Thread hilo : hilos) {
    	//Para asegurarse de que todos los hilos terminen antes de finalizar la ejecución
      try {
        hilo.join(0);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
