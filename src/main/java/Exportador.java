public class Exportador extends Procesador implements Runnable {
  
  private RedDePetri rdp; //SE USARA LUEGO PARA LLAMAR A rdp.contadorInvariantes
  
  public Exportador(String nombre_hilo, Monitor monitor, Log log, RedDePetri rdp, int transicion_1, int transicion_2) {
    super(nombre_hilo, monitor, log, rdp, transicion_1, transicion_2);
    this.rdp = rdp;
  }
  
  @Override
  public void run(){
    while(getCorriendo()) {
      this.miMonitor.dispararTransicion(this.getTransicion1());
      this.miMonitor.dispararTransicion(this.getTransicion2());
      rdp.invCompletado();
    }
    rdp.escribirLog();
  }
}
