public class Recortador extends Procesador implements Runnable {


  public Recortador(String nombre_hilo, Monitor monitor, Log log, int transicion) {
    super(nombre_hilo,monitor,log,transicion);
    // setTransicion(transicion);
  }
  
  @Override
  public void run(){
    while(true){
    this.miMonitor.dispararTransicion(this.getTransicion1(),this);
    //this.listaImagenDestino.encolar(listaImagenOrigen.desencolar(), getTransicion1());
    }
  }

}