public class Exportador extends Procesador implements Runnable {

   private int transicion2;
  
  public Exportador(String nombre_hilo, Monitor monitor, Log log, int transicion_1, int transicion_2) {
    super(nombre_hilo, monitor, log,transicion_1);
    transicion2 = transicion_2;
  }
  public int getTransicion2(){
    return transicion2;
  }
  
  @Override
  public void run(){
	  try{
	      Thread.sleep(100);
	      }
	      catch(InterruptedException e){
	        throw new RuntimeException(e);
	      }
    while(miMonitor.getCorriendo()){
    this.miMonitor.dispararTransicion(this.getTransicion1(),this);
    //this.listaImagenDestino.encolar(listaImagenOrigen.desencolar(), getTransicion1());
    this.miMonitor.dispararTransicion(transicion2,this);
   
    }
  }
  
  @Override
  public void operar(int transicion) {
	  if(transicion==transicion1) {
		  super.operar(transicion);
	  }else {
		  if(!this.listaImagenDestino.estaVacia()) {
			  Imagen aux=this.listaImagenDestino.desencolar();
			  aux.agregarTransicion(transicion2);
			  this.miMonitor.contadorInvariantes(aux);
		  }else {
			  miLog.escribirArchivo("Error Exportador. Lista Destino no tiene imagenes");
		  }
		  
	  }
	  
  }

  @Override
  public boolean perteneceTransicion(int transicion){
    return this.getTransicion1() == transicion || this.transicion2 == transicion;
  }
  
}