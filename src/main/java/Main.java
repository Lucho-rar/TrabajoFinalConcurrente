
import java.util.ArrayList;

public class Main {
  // private static final int NUM_HILOS = 14;

  public static void main(String[] args) {
    // int contador = 1;

    ArrayList<Thread> hilos = new ArrayList<Thread>();
    ArrayList<Procesador> arrayProcesadores=new ArrayList<Procesador>();

    FabricaDeHilos miFabrica = new FabricaDeHilos();
    Politica politica = new Politica();
    RedDePetri redp = new RedDePetri();

    // Procesador procesador = new Procesador();
    Monitor miMonitor = new Monitor(redp, politica);

    Log log = new Log("log.txt", System.currentTimeMillis());

    GeneradorDeImagenes generadorDeImagenes = new GeneradorDeImagenes("generadorDeImagenes", miMonitor, log, 0);
    // generadorDeImagenes.setTransicion(0);//transiciones t0
    arrayProcesadores.add(generadorDeImagenes);
    
    Receptor receptorIzq = new Receptor("receptorIzq", miMonitor, log, 1);
    // transicion t1
    arrayProcesadores.add(receptorIzq);
    
    Receptor receptorDer = new Receptor("receptorDer", miMonitor, log, 2);
    // transicion t2
    arrayProcesadores.add(receptorDer);
    
    CargaDeImagenes cargaDeImagenesIzq = new CargaDeImagenes("cargaDeImagenesIzq", miMonitor, log, 3);
    // transicion t3
    arrayProcesadores.add(cargaDeImagenesIzq);
    
    CargaDeImagenes cargaDeImagenesDer = new CargaDeImagenes("cargaDeImagenesDer", miMonitor, log, 4);
    // transicion t4
    arrayProcesadores.add(cargaDeImagenesDer);
    
    TransferirAjustador transferirAjustadorIzq = new TransferirAjustador("transferirAjustadorIzq", miMonitor, log, 5);
    // transicion t5
    arrayProcesadores.add(transferirAjustadorIzq);
    
    TransferirAjustador transferirAjustadorDer = new TransferirAjustador("transferirAjustadorDer", miMonitor, log, 6);
    // transicion t6
    arrayProcesadores.add(transferirAjustadorDer);
    
    AjustadorDeCalidad ajustadorDeCalidadIzq = new AjustadorDeCalidad("ajustadorDeCalidadIzq", miMonitor, log, 7);
    // transicion t7
    arrayProcesadores.add(ajustadorDeCalidadIzq);
    
    AjustadorDeCalidad ajustadorDeCalidadDer = new AjustadorDeCalidad("ajustadorDeCalidadDer", miMonitor, log, 8);
    // transicion t8
    arrayProcesadores.add(ajustadorDeCalidadDer);
    
    TransferirRecortador transferirRecortadorIzq = new TransferirRecortador("transferirRecortadorIzq", miMonitor, log,
        9);
    // transicion t9
    arrayProcesadores.add(transferirRecortadorIzq);
    
    TransferirRecortador transferirRecortadorDer = new TransferirRecortador("transferirRecortadorDer", miMonitor, log,
        10);
    // transicion t10
    arrayProcesadores.add(transferirRecortadorDer);   
    
    Recortador recortadorIzq = new Recortador("recortadorIzq", miMonitor, log, 11);
    // transicion t11
    arrayProcesadores.add(recortadorIzq);
    
    Recortador recortadorDer = new Recortador("recortadorDer", miMonitor, log, 12);
    // transicion t12
    arrayProcesadores.add(recortadorDer);
    
    Exportador exportador = new Exportador("exportador", miMonitor, log, 13, 14);
    // transiciones t13 y t14
	arrayProcesadores.add(exportador);
	  
       
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

    // Monitor miMonitor=new Monitor(redp, politica, hilos);
	miMonitor.setArrayProcesadores(arrayProcesadores);
	
    hilos.forEach((hilo) -> hilo.start());

    
    /*
     * miMonitor.setHilos(hilos);
     * 
     * hilos.forEach((key, value) -> {
     * value.start();
     * });
     */

    /*
     * for(int i=0; i<hilos.size();i++){
     * hilos.get(i).start();
     * }
     */
    /*
     * for(int i = 0; i < NUM_HILOS; i++){
     * hilos.add(newThread("hilo - " + contador));
     * contador++;
     * hilos.get(hilos.size()-1).start();
     * }
     */

  }

}