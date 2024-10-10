public class Receptor extends Procesador implements Runnable {

  //private String nombre_hilo;
  //private Log miLog;
  //private Monitor miMonitor;

  public Receptor(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo,monitor,log, transicion);
    // setTransicion(transicion);
  }
  
  @Override
  public void run(){
    
	    while(true){
	    	this.miMonitor.dispararTransicion(this.getTransicion1(),this);
	       //this.listaImagenDestino.encolar(listaImagenOrigen.desencolar(), getTransicion1());
	    } 
   
  }
   
  public void run1(){
    if (this.getTransicion1() != 1){
	    for(int i=0;i<20000;i++){
	    	this.miMonitor.dispararTransicion(this.getTransicion1(),this);
	       //this.listaImagenDestino.encolar(listaImagenOrigen.desencolar(), getTransicion1());
	    } 
    }else{
    	 for(int i=0;i<5000;i++){
 	    	this.miMonitor.dispararTransicion(this.getTransicion1(),this);
 	       //this.listaImagenDestino.encolar(listaImagenOrigen.desencolar(), getTransicion1());
 	    } 
    }
  }
}