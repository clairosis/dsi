package ar.edu.utn.frba.dds.fuentes.fuenteDinamica;

import ar.edu.utn.frba.dds.hechos.Hecho;
import java.util.ArrayList;
import java.util.List;

public class GestorHechosSubidos {

  private static final GestorHechosSubidos instancia = new GestorHechosSubidos();
  private static final List<Hecho> hechosSubidos = new ArrayList<>();
  private static long contadorId = 0;

  public static GestorHechosSubidos getInstancia() {
    return instancia;
  }

  public List<Hecho> getHechosSubidos() {
    return new ArrayList<>(hechosSubidos);
  }

  public static Hecho getHecho(Long id) {
    return hechosSubidos.stream().filter(h -> h.getId().equals(id)).toList().get(0);
  }

  public static Hecho agregarHecho(Hecho hecho) {
    hecho.setId(contadorId++);
    hechosSubidos.add(hecho);
    return hecho;
  }

  public static void eliminarHecho(Long id) {
    Hecho hecho = getHecho(id);
    hechosSubidos.remove(hecho);
  }

  public static void eliminarHecho(Hecho hecho) {
    hechosSubidos.remove(hecho);
  }

  public static void limpiarLista() {
    hechosSubidos.clear();
    contadorId = 0;
  }
}
