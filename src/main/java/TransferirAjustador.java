public class TransferirAjustador extends Procesador implements Runnable {

  //String nombre_hilo;

  public TransferirAjustador(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo, monitor, log, transicion);
  }

  @Override
  public void run(){
    while(miMonitor.getCorriendo()){
      this.miMonitor.dispararTransicion(this.getTransicion1(), this);
    }
  }
}
