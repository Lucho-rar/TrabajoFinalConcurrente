public class GeneradorDeImagenes extends Procesador implements Runnable {

  private int cont=0;

  public GeneradorDeImagenes(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo, monitor, log, transicion, -1);
  }
  
  public void run() {
    while(getCorriendo() && cont < 200) {
      cont++;
      this.miMonitor.dispararTransicion(this.getTransicion1());
    }
  }
  /*
  @Override
  public void operar(int transicion) {
    this.listaImagenDestino.encolar(new Imagen(), this.getTransicion1());
  }
  */
}
