public class GeneradorDeImagenes extends Procesador implements Runnable{
 
  public GeneradorDeImagenes(String nombre_hilo, Monitor monitor, Log log, int transicion){
    super(nombre_hilo, monitor, log, transicion);

  }
  
  public void run(){
    while(miMonitor.getCorriendo()){
    	this.miMonitor.dispararTransicion(this.getTransicion1(),this);
    }
  }
  
  @Override
  public void operar(int transicion) {
	 this.listaImagenDestino.encolar(new Imagen(), this.getTransicion1());
  }
}