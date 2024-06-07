import java.util.Scanner;
import java.util.ArrayList;

  public class Main {
  private static final int NUM_HILOS = 14;
  
  
  public static void main(String[] args) {
    private int contador = 1;
    
    private ArrayList<Thread> hilos = new ArrayList<Thread>();
    
    FabricaDeHilos miFabrica=new FabricaDeHilos();
    Monitor miMonitor=new Monitor();
    //Procesador procesador = new Procesador();

    Log log = new Log("log.txt", System.currentTimeMillis());

    GeneradorDeImagenes generadorDeImagenes = new GeneradorDeImagenes("generadorDeImagenes",miMonitor,log);

    Receptor receptorIzq = new Receptor("receptorIzq",miMonitor,log);
    Receptor receptorDer = new Receptor("receptorDer",miMonitor,log);

    CargaDeImagenes cargaDeImagenesIzq = new CargaDeImagenes("cargaDeImagenesIzq",miMonitor,log);
    CargaDeImagenes cargaDeImagenesDer = new CargaDeImagenes("cargaDeImagenesDer",miMonitor,log);

    TransferirAjustador transferirAjustadorIzq = new TransferirAjustador("transferirAjustadorIzq",miMonitor,log);
    TransferirAjustador transferirAjustadorDer = new TransferirAjustador("transferirAjustadorDer",miMonitor,log);
    
    AjustadorDeCalidad ajustadorDeCalidadIzq = new AjustadorDeCalidad("ajustadorDeCalidadIzq",miMonitor,log);
    AjustadorDeCalidad ajustadorDeCalidadDer = new AjustadorDeCalidad("ajustadorDeCalidadDer",miMonitor,log);

    TransferirRecortador transferirRecortadorIzq = new TransferirRecortador("transferirRecortadorIzq",miMonitor,log);
    TransferirRecortador transferirRecortadorDer = new TransferirRecortador("transferirRecortadorDer",miMonitor,log);

    Recortador recortadorIzq = new Recortador("recortadorIzq",miMonitor,log);
    Recortador recortadorDer = new Recortador("recortadorDer",miMonitor,log);

    Exportador exportador = new Exportador("exportador",miMonitor,log);
    
    miFabrica.newThread(log).start();
    miFabrica.newThread(generadorDeImagenes).start();
    miFabrica.newThread(receptorIzq).start();
    miFabrica.newThread(receptorDer).start();

    miFabrica.newThread(cargaDeImagenesIzq).start();
    miFabrica.newThread(cargaDeImagenesDer).start();

    miFabrica.newThread(transferirAjustadorIzq).start();
    miFabrica.newThread(transferirAjustadorDer).start();
    
    miFabrica.newThread(ajustadorDeCalidadIzq).start();
    miFabrica.newThread(ajustadorDeCalidadDer).start();

    miFabrica.newThread(transferirRecortadorIzq).start();
    miFabrica.newThread(transferirRecortadorDer).start();

    miFabrica.newThread(recortadorIzq).start();
    miFabrica.newThread(recortadorDer).start();

    miFabrica.newThread(exportador).start();

    /*for(int i = 0; i < NUM_HILOS; i++){
      hilos.add(newThread("hilo - " + contador));
      contador++;
      hilos.get(hilos.size()-1).start();
    }*/

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