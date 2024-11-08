import java.util.ArrayList;
import java.util.concurrent.*;

/*
* Clase Cola
*
* lista: Array de imagenes
* mutex: Semaforo para sincronizar el acceso a la cola
*/
public class ColaImagenes  {
  private ArrayList<Imagen> lista;
  private Semaphore mutex;

  // constructor
  public ColaImagenes(){
    lista = new ArrayList<Imagen>();
    mutex = new Semaphore(1);
  }

  //Método para agregar una imagen a la cola
  public void encolar(Imagen img, int transicion){
    try{
      mutex.acquire(); //acquire del semaforo
    }catch (InterruptedException e){
       System.out.println("error al encolar");
    }
    img.agregarTransicion(transicion);
    lista.add(img); //agrega a la lista
    mutex.release(); //release del semaforo
  }
  
  //Método para sacar una imagen de la cola
  public Imagen desencolar(){
    try{
      mutex.acquire();  //adquiere el semaforo
    }catch (InterruptedException e){
       System.out.println("error al desencolar");
    }
    Imagen img=null;
    if(!lista.isEmpty()){  // si la lista no esta vacia toma la imagen y la saca de la misma
      img=lista.get(0);
      lista.remove(0);
    }
    mutex.release(); // release del semaforo
    return img;
  }

  //Método para saber si la cola esta vacia
  public boolean estaVacia(){
    return lista.isEmpty();
  }

  //Método para obtener el tamaño de la cola
  public int tamano(){
    return lista.size();
  }
  public Semaphore getMutex(){
    return mutex;
  }
}
