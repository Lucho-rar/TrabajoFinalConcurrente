import java.io.*;
import java.util.concurrent.ThreadFactory;

/**
  * Clase Hilo que implementa Thread Factory
  * 
  * counter: Contador de hilos
  * name: Nombre del hilo
  */
public class Hilo implements ThreadFactory{

  private int counter;
  private String name;

  // constructor
  public Hilo(String name){
    counter = 0;
    this.name = name;
  }

  // Metodo que retorna el nombre del hilo
  public String getName(){
    return this.name;
  }

  // Metodo que retorna el contador
  public int getCounter(){
    return this.counter;
  }

  // Metodo que crea un nuevo hilo
  @Override
  public Thread newThread(Runnable r){
    Thread t = new Thread(r, name + counter);
    counter++;    //aumenta el contador de hilos
    return t;     //retorna el hilo creado
  }
  
}