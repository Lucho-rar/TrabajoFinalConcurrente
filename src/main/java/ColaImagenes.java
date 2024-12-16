import java.util.ArrayList;

public class ColaImagenes {
  private ArrayList<Imagen> lista;
  
  // constructor
  public ColaImagenes(){
    lista = new ArrayList<Imagen>();
  }

  //Método para agregar una imagen a la cola
  public void encolar(Imagen img, int transicion) {
    img.agregarTransicion(transicion);
    lista.add(img); //agrega a la lista
  }
  
  //Método para sacar una imagen de la cola
  public Imagen desencolar() {
    Imagen img = null;
    if(!lista.isEmpty()) {  // si la lista no esta vacia toma la imagen y la saca de la misma
      img = lista.get(0);
      lista.remove(0);
    }
    return img;
  }

  //Método para saber si la cola esta vacia
  public boolean estaVacia() {
    return lista.isEmpty();
  }
}
