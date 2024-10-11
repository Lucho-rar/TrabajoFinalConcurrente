public class GeneradorDeImagenes extends Procesador implements Runnable{
 
  public GeneradorDeImagenes(String nombre_hilo, Monitor monitor, Log log, int transicion){
    super(nombre_hilo,monitor,log,transicion);
    //setTransicion(transicion);

  }
  
  public void run(){
    while(true){
    	
    	//if(this.miMonitor.cantidadTokensPlaza(0) < 5) {
    	    
    		this.miMonitor.dispararTransicion(this.getTransicion1(),this);
         
        //actualizar el recorrido de imagen 
    	//}
    }
  }
  
  @Override
  public void operar(int transicion) {
	 this.listaImagenDestino.encolar(new Imagen(), this.getTransicion1());
  }
}