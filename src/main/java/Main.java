
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;

public class Main {

	public static void main(String[] args) {
		   String ruta="log";
		   int contador=1;
	       int numEjecuciones = 50;
         long tiempoInicial = System.currentTimeMillis();
	        for (int i = 1; i <= numEjecuciones; i++) {
            long tiempoActual = System.currentTimeMillis();
            
	            try {
	                System.out.println("Ejecución " + i + ": ");
	                // Llama al método principal de tu programa
	                main2(ruta+contador+".txt");
	                contador++;
	                // Reemplaza con el nombre de tu clase principal
	                System.out.println("Completada con éxito.");
	            } catch(Exception e){

	                System.out.println("Error en la ejecución " + i + ": " + e.getMessage());
	                break;
	            }
              long tiempoFinal = System.currentTimeMillis();
              System.out.println("Tiempo de ejecución: " + (tiempoFinal - tiempoActual)/1000 + "segundos.");
	        }
          long tiempoFinal = System.currentTimeMillis();
          System.out.println("Tiempo promedio de ejecución: " + (tiempoFinal - tiempoInicial) / numEjecuciones/1000 + "segundos.");
	}
	            
	
	
	
  public static void main2(String ruta) {
	  
	
    ArrayList<Thread> hilos = new ArrayList<Thread>();
    FabricaDeHilos miFabrica = new FabricaDeHilos();
    Politica politica = new Politica();
    politica.setConTiempo(true);
    politica.setTipoPolitica(2);//2 es izquierda
    Log log = new Log(ruta, System.currentTimeMillis());
    RedDePetri redp = new RedDePetri(log);

    ArrayList<ColaImagenes> plazasImagen=new ArrayList<ColaImagenes>();
    for(int i=0;i<11;i++){
      plazasImagen.add(new ColaImagenes());
    }
    // 0  1  2  3  4  5   6   7    8   9  10
    //p0,p2,p4,p6,p8,p10,p12,p14,p15,p16,p17
    
    Monitor miMonitor = new Monitor(redp, politica, log);

    GeneradorDeImagenes generadorDeImagenes = new GeneradorDeImagenes("generadorDeImagenes", miMonitor, log, 0, redp);
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
    //System.out.println("");
    int h=0;
    for (Thread hilo : hilos) {
        try {
        	 System.out.println("join antes: "+h+"nombre"+hilo.getName()+" estado"+hilo.getState().toString());
          hilo.join(0);
          
          System.out.println("join despues: "+h+"nombre"+hilo.getName()+" estado"+hilo.getState().toString());
          h++;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("Todos los hilos terminaron");
  }
}
