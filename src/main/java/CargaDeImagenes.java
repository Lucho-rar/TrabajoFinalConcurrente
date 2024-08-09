public class CargaDeImagenes extends Procesador implements Runnable {

  //String nombre_hilo;
  //private Log miLog;

  public CargaDeImagenes(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo,monitor,log,transicion);
    // setTransicion(transicion);
  }

  @Override
  public void run(){
    while(true){
    this.miMonitor.dispararTransicion(this.getTransicion1());
    }
  }

}
