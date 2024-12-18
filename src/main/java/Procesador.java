public abstract class Procesador {
  protected String nombre;
  protected String nombre_hilo;
  protected Monitor miMonitor;
  protected Log miLog;
  protected int transicion1;
  protected int transicion2;
  protected ColaImagenes listaImagenOrigen;
  protected ColaImagenes listaImagenDestino;
  protected static int invCompletados=0;

  public Procesador(String nombre, Monitor monitor, Log log, int transicion_1, int transicion_2) {
    this.miLog = log;
    this.miMonitor = monitor;
    this.nombre = nombre;
    this.transicion1 = transicion_1;
    this.transicion2 = transicion_2;
    this.nombre_hilo = "";
    listaImagenOrigen = null;
    listaImagenDestino = null;
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
  public void invCompletado() {
	  invCompletados++;
	  
  }
  public boolean getCorriendo() {
	  return invCompletados<200;
  }
 
  /*
  public void setOrigen(ColaImagenes origen) {
    this.listaImagenOrigen = origen;
  }
  
  public void setDestino(ColaImagenes destino) {
    this.listaImagenDestino = destino;
  }

  public void operar(int transicion) {
    if(!this.listaImagenOrigen.estaVacia()) {
      this.listaImagenDestino.encolar(this.listaImagenOrigen.desencolar(),transicion);
    }else {
      miLog.escribirArchivo("Error. Lista origen vacía transicion:"+transicion+" hilo: "+Thread.currentThread().getName());
    }
  }
  */
}
