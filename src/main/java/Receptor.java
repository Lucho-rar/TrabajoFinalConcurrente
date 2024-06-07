public class Receptor extends Procesador implements Runnable {

  //private String nombre_hilo;
  //private Log miLog;
  //private Monitor miMonitor;

  public Receptor(String nombre_hilo, Monitor monitor, Log log) {
    super(nombre_hilo,monitor,log);
  }
  
  @Override
  public void run(){}
}