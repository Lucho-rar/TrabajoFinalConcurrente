public class Exportador extends Procesador implements Runnable {

  //private Log miLog;
  
  public Exportador(String nombre_hilo, Monitor monitor, Log log) {
    super(nombre_hilo,monitor,log);
  }
  
  @Override
  public void run(){}
}