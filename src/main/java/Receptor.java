public class Receptor extends Procesador implements Runnable {

  //private String nombre_hilo;
  //private Log miLog;
  //private Monitor miMonitor;

  public Receptor(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo,monitor,log, transicion);
    // setTransicion(transicion);
  }
  
  @Override
  public void run(){
    while(true){
    	this.miMonitor.dispararTransicion(this.getTransicion1());
       this.listaImagenDestino.encolar(listaImagenOrigen.desencolar(), getTransicion1());
    }
  }
}