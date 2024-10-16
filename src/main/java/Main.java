
import java.util.ArrayList;

public class Main {
  // private static final int NUM_HILOS = 14;

  public static void main(String[] args) {
    // int contador = 1;

    ArrayList<Thread> hilos = new ArrayList<Thread>();

    FabricaDeHilos miFabrica = new FabricaDeHilos();
    Politica politica = new Politica();
    Log log = new Log("log.txt", System.currentTimeMillis());
    RedDePetri redp = new RedDePetri(log);


    ArrayList<Cola> plazasImagen=new ArrayList<Cola>();
    for(int i=0;i<11;i++){
      plazasImagen.add(new Cola());
    }
    // 0  1  2  3  4  5   6   7    8   9  10
    //p0,p2,p4,p6,p8,p10,p12,p14,p15,p16,p17
    
    // Procesador procesador = new Procesador();
    Monitor miMonitor = new Monitor(redp, politica, log);

    GeneradorDeImagenes generadorDeImagenes = new GeneradorDeImagenes("generadorDeImagenes", miMonitor, log, 0);
    // generadorDeImagenes.setTransicion(0);//transiciones t0
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

    // Monitor miMonitor=new Monitor(redp, politica, hilos);
	//miMonitor.setArrayProcesadores(arrayProcesadores);
	
	//System.out.println("cantidad hilos: " + hilos.size());
	
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