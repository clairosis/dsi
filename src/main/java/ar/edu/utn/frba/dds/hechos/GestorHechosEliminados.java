package ar.edu.utn.frba.dds.hechos;

import java.util.ArrayList;
import java.util.List;

public class GestorHechosEliminados {

  private static final GestorHechosEliminados instancia = new GestorHechosEliminados();
  private static final List<Hecho> hechosEliminados = new ArrayList<>();

  public static GestorHechosEliminados getInstancia() {
    return instancia;
  }

  public List<Hecho> getHechosEliminados() {
    return new ArrayList<Hecho>(hechosEliminados);
  }

  public static void eliminarHecho(Hecho hecho) {
    hechosEliminados.add(hecho);
  }

  public static void limpiarLista() {
    hechosEliminados.clear();
  }
}
