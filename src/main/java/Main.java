
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

  public static void ejecucion(String ruta,Log log, Log log_regex, boolean conTiempo, int tipoPolitica) {
    ArrayList<Thread> hilos = new ArrayList<Thread>();
    FabricaDeHilos miFabrica = new FabricaDeHilos();
    Politica politica = new Politica();
    politica.setConTiempo(conTiempo);
    politica.setTipoPolitica(tipoPolitica);//2 es izquierda
    RedDePetri redp = new RedDePetri(log);
    ArrayList<ColaImagenes> plazasImagen = new ArrayList<ColaImagenes>();
    for(int i = 0; i < 11; i++) {
      plazasImagen.add(new ColaImagenes());
    }
    // 0  1  2  3  4  5   6   7    8   9  10
    //p0,p2,p4,p6,p8,p10,p12,p14,p15,p16,p17
    Monitor miMonitor = new Monitor(redp, politica, log , log_regex);
    GeneradorDeImagenes generadorDeImagenes = new GeneradorDeImagenes("generadorDeImagenes", miMonitor, log, 0);
    generadorDeImagenes.setDestino(plazasImagen.get(0));
    Receptor receptorIzq = new Receptor("receptorIzq", miMonitor, log, 1);
    // transicion t1
    receptorIzq.setOrigen(plazasImagen.get(0));  //plaza 0
    receptorIzq.setDestino(plazasImagen.get(1)); //plaza 2
    Receptor receptorDer = new Receptor("receptorDer", miMonitor, log, 2);
    receptorDer.setOrigen(plazasImagen.get(0)); //plaza 0
    receptorDer.setDestino(plazasImagen.get(2)); //plaza 4
    // transicion t2
    CargaDeImagenes cargaDeImagenesIzq = new CargaDeImagenes("cargaDeImagenesIzq", miMonitor, log, 3);
   	cargaDeImagenesIzq.setOrigen(plazasImagen.get(1)); //plaza 2
    cargaDeImagenesIzq.setDestino(plazasImagen.get(3)); //plaza 6
    // transicion t3
    CargaDeImagenes cargaDeImagenesDer = new CargaDeImagenes("cargaDeImagenesDer", miMonitor, log, 4);
    cargaDeImagenesDer.setOrigen(plazasImagen.get(2)); //plaza 4
    cargaDeImagenesDer.setDestino(plazasImagen.get(3)); //plaza 6
    // transicion t4
    TransferirAjustador transferirAjustadorIzq = new TransferirAjustador("transferirAjustadorIzq", miMonitor, log, 5);
    transferirAjustadorIzq.setOrigen(plazasImagen.get(3)); //plaza 6
    transferirAjustadorIzq.setDestino(plazasImagen.get(4)); //plaza 8
    // transicion t5
    TransferirAjustador transferirAjustadorDer = new TransferirAjustador("transferirAjustadorDer", miMonitor, log, 6);
    transferirAjustadorDer.setOrigen(plazasImagen.get(3));		//plaza 6
    transferirAjustadorDer.setDestino(plazasImagen.get(5));		//plaza 10
    // transicion t6
    AjustadorDeCalidad ajustadorDeCalidadIzq = new AjustadorDeCalidad("ajustadorDeCalidadIzq", miMonitor, log, 7);
    // transicion t7
    ajustadorDeCalidadIzq.setOrigen(plazasImagen.get(4));		//plaza 8
    ajustadorDeCalidadIzq.setDestino(plazasImagen.get(6));		//plaza 12
    AjustadorDeCalidad ajustadorDeCalidadDer = new AjustadorDeCalidad("ajustadorDeCalidadDer", miMonitor, log, 8);
    // transicion t8
    ajustadorDeCalidadDer.setOrigen(plazasImagen.get(5));		//plaza 10
    ajustadorDeCalidadDer.setDestino(plazasImagen.get(6));		//plaza 12
    TransferirRecortador transferirRecortadorIzq = new TransferirRecortador("transferirRecortadorIzq", miMonitor, log,
        9);
    // transicion t9
    transferirRecortadorIzq.setOrigen(plazasImagen.get(6));		//plaza 12
    transferirRecortadorIzq.setDestino(plazasImagen.get(7));	//plaza 14
    TransferirRecortador transferirRecortadorDer = new TransferirRecortador("transferirRecortadorDer", miMonitor, log,
        10);
    // transicion t10
    transferirRecortadorDer.setOrigen(plazasImagen.get(6));		//plaza 12
    transferirRecortadorDer.setDestino(plazasImagen.get(8)); 	//plaza 15
    Recortador recortadorIzq = new Recortador("recortadorIzq", miMonitor, log, 11);
    // transicion t11
    recortadorIzq.setOrigen(plazasImagen.get(7));	//plaza 14
    recortadorIzq.setDestino(plazasImagen.get(9));	//plaza 16
    Recortador recortadorDer = new Recortador("recortadorDer", miMonitor, log, 12);
    // transicion t12
    recortadorDer.setOrigen(plazasImagen.get(8));	//plaza 15
    recortadorDer.setDestino(plazasImagen.get(9));	//plaza 16
    Exportador exportador = new Exportador("exportador", miMonitor, log, 13, 14);
    exportador.setOrigen(plazasImagen.get(9));	//plaza 16
    exportador.setDestino(plazasImagen.get(10)); //plaza 17
    // transiciones t13 y t14

    hilos.add(miFabrica.newThread(generadorDeImagenes));

    hilos.add(miFabrica.newThread(receptorIzq));
    hilos.add(miFabrica.newThread(receptorDer));

    hilos.add(miFabrica.newThread(cargaDeImagenesIzq));
    hilos.add(miFabrica.newThread(cargaDeImagenesDer));

    hilos.add(miFabrica.newThread(transferirAjustadorIzq));
    hilos.add(miFabrica.newThread(transferirAjustadorDer));

    hilos.add(miFabrica.newThread(ajustadorDeCalidadIzq));
    hilos.add(miFabrica.newThread(ajustadorDeCalidadDer));

    hilos.add(miFabrica.newThread(transferirRecortadorIzq));
    hilos.add(miFabrica.newThread(transferirRecortadorDer));

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
