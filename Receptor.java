public class Receptor extends Procesador implements Runnable {

  public Receptor(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo,monitor, log, transicion);
  }
  
  @Override
  public void run() {
    while(miMonitor.getCorriendo()) {
      this.miMonitor.dispararTransicion(this.getTransicion1(), this);
    }
  }
/*   
  public void run1(){
    if (this.getTransicion1() != 1) {
	    for(int i = 0; i < 20000; i++) {
	    	this.miMonitor.dispararTransicion(this.getTransicion1(), this);
	    } 
    }else{
      for(int i = 0; i < 5000; i++) {
        this.miMonitor.dispararTransicion(this.getTransicion1(), this);
      }
    }
  }*/
}