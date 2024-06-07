public abstract class Procesador {

    private String nombre_hilo;
    private Monitor miMonitor;
    private Log miLog;
    

  public Procesador(String nombre,Monitor monitor, Log log){
    this.miLog = log;
    this.miMonitor = monitor;
    this.nombre_hilo = nombre;
  }
  
  public String getNombre(){
    return nombre_hilo;
  }
  
}