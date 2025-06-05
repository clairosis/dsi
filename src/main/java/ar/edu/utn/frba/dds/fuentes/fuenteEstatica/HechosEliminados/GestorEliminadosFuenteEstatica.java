package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.HechosEliminados;

import java.util.ArrayList;
import java.util.List;

public class GestorEliminadosFuenteEstatica {
  private static final GestorEliminadosFuenteEstatica instancia =
      new GestorEliminadosFuenteEstatica();
  private static final List<HechoEliminado> hechosEliminados = new ArrayList<>();

  public static GestorEliminadosFuenteEstatica getInstancia() {
    return instancia;
  }

  public List<HechoEliminado> getHechosEliminados() {
    return new ArrayList<>(hechosEliminados);
  }

  public List<Long> getHechosEliminados(String archivo) {
    String path = archivo.replaceFirst("^\\./inc/fuentesEstaticas/", "");

    return hechosEliminados.stream()
        .filter(h -> h.getArchivo().equals(path))
        .map(HechoEliminado::getId)
        .toList();
  }

  public static void eliminarHecho(Long id, String archivo) {
    hechosEliminados.add(new HechoEliminado(id, archivo));
  }

  public static void limpiarLista() {
    hechosEliminados.clear();
  }
}
