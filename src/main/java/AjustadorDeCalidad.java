public class AjustadorDeCalidad extends Procesador implements Runnable {
  
  public AjustadorDeCalidad(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo, monitor, log, transicion);
  }
  
  @Override
  public void run() {
    try {
      Thread.sleep(100);
    } catch(InterruptedException e) {
      throw new RuntimeException(e);
    }
    while(miMonitor.getCorriendo()) {
      this.miMonitor.dispararTransicion(this.getTransicion1(), this);
    }
  }
}
