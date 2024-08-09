public class Exportador extends Procesador implements Runnable {

  //private Log miLog;
   private int transicion2;
  
  public Exportador(String nombre_hilo, Monitor monitor, Log log, int transicion_1, int transicion_2) {
    super(nombre_hilo, monitor, log,transicion_1);
    transicion2=transicion_2;
  }
  public int getTransicion2(){
    return transicion2;
  }
  
  @Override
  public void run(){
    while(true){
    this.miMonitor.dispararTransicion(this.getTransicion1());
    this.miMonitor.dispararTransicion(transicion2);
    }
  }
  @Override
  public boolean perteneceTransicion(int transicion){
    return this.getTransicion1()==transicion||this.transicion2==transicion;
  }
}