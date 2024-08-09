import java.util.concurrent.*;


/*
* Clase Cola
*
* lista: Array de imagenes
* mutex: Semaforo para sincronizar el acceso a la cola
*/
public class Cola2  {
  private double[] lista;
  private Semaphore mutex;

  // constructor
  public Cola2(){
    mutex = new Semaphore(1);
    lista = new double[15];
  }

  //Método para agregar una imagen a la cola
  public void encolar(int transicion){
    try{
      mutex.acquire(); //acquire del semaforo
      
    }catch (InterruptedException e){
       System.out.println("error al encolar");
    }
    lista[transicion]=1; //agrega a la lista
    mutex.release(); //release del semaforo
  }
  
  //Método para sacar una imagen de la cola
  public void desencolar(int transicion){
    try{
      mutex.acquire();  //adquiere el semaforo
    }catch (InterruptedException e){
       System.out.println("error al desencolar");
    }
    
    lista[transicion] = 0;
 /*   if(lista.length > 0){  // si la lista no esta vacia toma la imagen y la saca de la misma
      img=lista[0];
      lista.remove(0);
    }*/
    mutex.release(); // release del semaforo
    //return img;
  }

  //Método para saber si la cola esta vacia
  public boolean estaVacia(){
    return lista.length == 0;
  }

  //Método para obtener el tamaño de la cola
  public int tamano(){
    return lista.length;
  }
  public Semaphore getMutex(){
    return mutex;
  }
  
  //Metodo para obtener las transiciones encoladas
  public double[] quienesEstan(){
    return lista;
  }
}
