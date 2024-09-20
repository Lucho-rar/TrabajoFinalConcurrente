public class GeneradorDeImagenes extends Procesador implements Runnable{
 
  public GeneradorDeImagenes(String nombre_hilo, Monitor monitor, Log log, int transicion){
    super(nombre_hilo,monitor,log,transicion);
    //setTransicion(transicion);

  }
  
  public void run(){
    while(true){
    	this.miMonitor.dispararTransicion(this.getTransicion1());
    }
  }
}