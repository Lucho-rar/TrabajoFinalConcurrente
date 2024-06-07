public class Recortador extends Procesador implements Runnable {


  public Recortador(String nombre_hilo, Monitor monitor, Log log) {
    super(nombre_hilo,monitor,log);
  }
  
  @Override
  public void run(){}

}