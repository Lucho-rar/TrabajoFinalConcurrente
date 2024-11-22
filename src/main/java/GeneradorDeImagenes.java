public class GeneradorDeImagenes extends Procesador implements Runnable{
 RedDePetri rdp;
 private int cont=0;
  public GeneradorDeImagenes(String nombre_hilo, Monitor monitor, Log log, int transicion, RedDePetri redp){
    super(nombre_hilo, monitor, log, transicion);
    this.rdp = redp;

  }
  
  public void run(){
   /* try{
      Thread.sleep(10);
      }
      catch(InterruptedException e){
        throw new RuntimeException(e);
      }*/
    while(miMonitor.getCorriendo()&&cont<200){
    	cont++;
    	this.miMonitor.dispararTransicion(this.getTransicion1(),this);
    }
  }
  
  @Override
  public void operar(int transicion) {
	 this.listaImagenDestino.encolar(new Imagen(), this.getTransicion1());
  }
}