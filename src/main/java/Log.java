import java.io.File;
import java.io.FileWriter;

/**
* Clase Log  
*
* archivo: Archivo donde se guarda la información
* horaInicial: Hora inicial del proceso
* ruta: Ruta del archivo
* tLog: Thread que escribe en el archivo
*/
public class Log() extends Thread {
  
  private File archivo;
  private long horaInicial;
  private String ruta;
  private Thread tlog;
  
  //constructor
  public Log(String ruta, long horaInicial, Thread tlog){
    this.ruta = ruta;
    this.horaInicial = horaInicial;
    this.tlog = tlog;
  }
  
  @Override 
  public void run(){
    this.crearLog();
    boolean condicion = true;
    while(condicion){
      try{
        
        //Thread.sleep(1000);
      }catch(InterruptedException e){
        System.out.println("error al escribir en el log");
      }
      this.escribirLog(tlog);
    }
  }

  // Método para crear el log
  private void crearLog() {
    archivo = new File(ruta);  //chequea si el archivo existe en la ruta
    if (archivo.exists()&& !archivo.isDirectory()){
      archivo.delete();  //si existe lo borra para que comience de cero
    } 
    archivo.createNewFile();  //si no existe lo crea
  }
  
  // Método para escribir en el log
  private void escribirLog(Thread hilo) {
    FileWriter fw = new FileWriter(archivo, true); //agrega al final el nuevo texto cada vez que se llama
    long horaFinal = System.currentTimeMillis();
    fw.write("Hilo: " + hilo.getName() + "\n");
    fw.write("Tiempo de ejecucion: " + (horaFinal - horaInicial) + " ms" + "\n"); 
    fw.write(); //mas contenido del log a definir: transiciones, etc
    fw.write("\n");
    fw.flush();
    fw.close();
  }
  
}
