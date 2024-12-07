public class Exportador extends Procesador implements Runnable {

  private int transicion2;
  
  public Exportador(String nombre_hilo, Monitor monitor, Log log, int transicion_1, int transicion_2) {
    super(nombre_hilo, monitor, log,transicion_1);
    transicion2 = transicion_2;
  }
  
  @Override
  public void run(){
    while(miMonitor.getCorriendo()) {
      this.miMonitor.dispararTransicion(this.getTransicion1(), this);
      this.miMonitor.dispararTransicion(transicion2, this);
    }
  }
  
  @Override
  public void operar(int transicion) {
    if(transicion == transicion1) {
      super.operar(transicion);
    } else {
      if(!this.listaImagenDestino.estaVacia()) {
        Imagen aux = this.listaImagenDestino.desencolar();
        aux.agregarTransicion(transicion2);
        this.miMonitor.contadorInvariantes(aux);
      } else {
        miLog.escribirArchivo("Error Exportador. Lista Destino no tiene imagenes");
      }
    }
  }
}
