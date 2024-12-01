public class GeneradorDeImagenes extends Procesador implements Runnable {

  RedDePetri rdp;
  private int cont=0;

  //TODO: no se hace nada con la rdp, sacar?
  public GeneradorDeImagenes(String nombre_hilo, Monitor monitor, Log log, int transicion, RedDePetri redp) {
    super(nombre_hilo, monitor, log, transicion);
    this.rdp = redp;
  }
  
  public void run(){
    while(miMonitor.getCorriendo() && cont < 200) {
      cont++;
      this.miMonitor.dispararTransicion(this.getTransicion1(), this);
    }
  }
  
  //TODO: revisar si se puede sacar todo lo de operar
  @Override
  public void operar(int transicion) {
    this.listaImagenDestino.encolar(new Imagen(), this.getTransicion1());
  }
}
