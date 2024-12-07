public class TransferirRecortador extends Procesador implements Runnable {

  public TransferirRecortador(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo, monitor, log, transicion);
  }

  @Override
  public void run() {
    while (miMonitor.getCorriendo()) {
      this.miMonitor.dispararTransicion(this.getTransicion1(), this);
    }
  }
}
