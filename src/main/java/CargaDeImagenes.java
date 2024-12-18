public class CargaDeImagenes extends Procesador implements Runnable {

  public CargaDeImagenes(String nombre_hilo, Monitor monitor, Log log, int transicion_1, int transicion_2) {
    super(nombre_hilo, monitor, log, transicion_1, transicion_2);
  }

  @Override
  public void run() {
    while(getCorriendo()) {
      this.miMonitor.dispararTransicion(this.getTransicion1());
      this.miMonitor.dispararTransicion(this.getTransicion2());
    }
  }
}
