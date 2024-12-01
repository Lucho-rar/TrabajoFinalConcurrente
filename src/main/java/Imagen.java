import java.util.ArrayList;

public class Imagen {
  
  private ArrayList<Integer> recorridoInvariante;

  public Imagen() {
    recorridoInvariante = new ArrayList<Integer>();
  }

  public void agregarTransicion(int transicion) {
    recorridoInvariante.add(Integer.valueOf(transicion));
  }
  
  public ArrayList<Integer> getInvariante() {
    return recorridoInvariante;
  }
  
  public String imprimirRecorrido() {
    String salida = "";
    for(int i = 0; i < recorridoInvariante.size(); i++) {
      salida = salida + recorridoInvariante.get(i) + "->";
    }
    return salida;
  }
}
