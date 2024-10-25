import java.util.ArrayList;

public abstract class Procesador {

  protected String nombre;
  protected String nombre_hilo;
  protected Monitor miMonitor;
  protected Log miLog;
  protected int transicion1;
  protected Cola listaImagenOrigen;
  protected Cola listaImagenDestino;
    

  public Procesador(String nombre,Monitor monitor, Log log, int transicion){
    this.miLog = log;
    this.miMonitor = monitor;
    this.nombre = nombre;
    this.transicion1=transicion;
    this.nombre_hilo="";
    listaImagenOrigen=null;
    listaImagenDestino=null;
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
  
  
  public void setOrigen(Cola origen) {
	  this.listaImagenOrigen = origen;
  }
   public void setDestino(Cola destino){
      this.listaImagenDestino=destino;
   }
   
   
   public void operar(int transicion) {
	   if(!this.listaImagenOrigen.estaVacia()) {
		   this.listaImagenDestino.encolar(this.listaImagenOrigen.desencolar(),transicion);
	   }else {
		   miLog.escribirArchivo("Error. Lista origen vacía transicion:"+transicion+" hilo: "+Thread.currentThread().getName());
	   }
	   
	   
	   
   
   }

}