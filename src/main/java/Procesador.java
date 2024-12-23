public abstract class Procesador {
  protected String nombre;
  protected String nombre_hilo;
  protected Monitor miMonitor;
  protected Log miLog;
  protected int transicion1;
  protected ColaImagenes listaImagenOrigen;
  protected ColaImagenes listaImagenDestino;

  public Procesador(String nombre, Monitor monitor, Log log, int transicion) {
    this.miLog = log;
    this.miMonitor = monitor;
    this.nombre = nombre;
    this.transicion1 = transicion;
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
}
