public class AjustadorDeCalidad extends Procesador implements Runnable {
  
  public AjustadorDeCalidad(String nombre_hilo, Monitor monitor, Log log,RedDePetri r, int transicion_1, int transicion_2) {
    super(nombre_hilo, monitor, log, r, transicion_1, transicion_2);
  }
  
  @Override
  public void run() {
	 // int cont=0;
    while(getCorriendo()) {
      this.miMonitor.dispararTransicion(this.getTransicion1());
      this.miMonitor.dispararTransicion(this.getTransicion2());
      //System.out.println("ajustadorCalidad:"+cont);
      //cont++;
    }
  }
}
