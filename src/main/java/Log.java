import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

/**
* Clase Log  
*
* archivo: Archivo donde se guarda la información
* horaInicial: Hora inicial del proceso
* ruta: Ruta del archivo
* tLog: Thread que escribe en el archivo
*/
public class Log {
  
  private File archivo;
  private long horaInicial;
  private String ruta;
  private Semaphore mutex = new Semaphore(1);
  
  //constructor
  public Log(String ruta, long horaInicial){
    this.ruta = ruta;
    this.horaInicial = horaInicial;
    this.crearLog();
  }
  

  // Método para crear el log
  private void crearLog() {
    archivo = new File(ruta);  //chequea si el archivo existe en la ruta
    try {
      if (archivo.exists()&& !archivo.isDirectory()){
        archivo.delete();  //si existe lo borra para que comience de cero
      } 
      archivo.createNewFile();  //si no existe lo crea
     } catch (Exception e) {
        e.printStackTrace();
     }
  }

	public void escribirArchivo(String texto){
    try {
        mutex.acquire();
        FileWriter fw = new FileWriter(archivo, true); //agrega al final el nuevo texto cada vez que se llama
        fw.write(texto);
        fw.write("\n");
        fw.flush();
        fw.close();
     } catch (Exception e) {
        e.printStackTrace();
     }
     finally {
        mutex.release();
     }
  }
	
	
}
