public abstract class Procesador {
  protected String nombre;
  protected String nombre_hilo;
  protected Monitor miMonitor;
  protected Log miLog;
  protected int transicion1;
  protected int transicion2;
  protected RedDePetri rdp;

  public Procesador(String nombre, Monitor monitor, Log log, RedDePetri r, int transicion_1, int transicion_2) {
    this.miLog = log;
    this.miMonitor = monitor;
    this.nombre = nombre;
    this.transicion1 = transicion_1;
    this.transicion2 = transicion_2;
    this.nombre_hilo = "";
    rdp=r;
  }
  
  public String getNombre() {
    return this.nombre;
  }
  
  public void setNombreHilo(String nombre) {
    this.nombre_hilo = nombre;
  }
  
  public int getTransicion1() {
    return this.transicion1;
  }
  
  public int getTransicion2(){
    return this.transicion2;
  }
  
  public boolean getCorriendo() {
	  
	  return rdp.getInvCompletados()<=200;
  }
 
}
