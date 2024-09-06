public abstract class Procesador {

  private String nombre;
  private String nombre_hilo;
  protected Monitor miMonitor;
  private Log miLog;
  private int transicion1;
    

  public Procesador(String nombre,Monitor monitor, Log log, int transicion){
    this.miLog = log;
    this.miMonitor = monitor;
    this.nombre = nombre;
    this.transicion1=transicion;
    this.nombre_hilo="";
  }
  
  public String getNombre(){
    return this.nombre;
  }
  
  public void setNombreHilo(String nombre) {
	  this.nombre_hilo = nombre;
  }
  public String getNombreHilo(){
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