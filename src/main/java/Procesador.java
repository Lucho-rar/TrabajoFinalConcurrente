public abstract class Procesador {


  private String nombre_hilo;
  protected Monitor miMonitor;
  private Log miLog;
  private int transicion1;
    

  public Procesador(String nombre,Monitor monitor, Log log, int transicion){
    this.miLog = log;
    this.miMonitor = monitor;
    this.nombre_hilo = nombre;
    this.transicion1=transicion;
  }
  
  public String getNombre(){
    return this.nombre_hilo;
  }
  
  /*public void setTransicion(int posicion){
    transiciones[posicion] = 1;
  }*/
  
  public int getTransicion1(){
    return this.transicion1;
  }

  public boolean perteneceTransicion(int transicion){
    return this.transicion1==transicion;
  }
  
}