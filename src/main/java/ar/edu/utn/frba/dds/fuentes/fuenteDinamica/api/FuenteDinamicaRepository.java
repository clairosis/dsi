package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.GestorHechosSubidos;
import ar.edu.utn.frba.dds.hechos.Hecho;
import org.springframework.stereotype.Repository;

@Repository
public class FuenteDinamicaRepository {
  public Hecho guardar(Hecho hecho) {
    return GestorHechosSubidos.agregarHecho(hecho);
  }

  public void eliminar(Long id) {
    GestorHechosSubidos.eliminarHecho(id);
  }

  public Hecho buscarPorId(Long id) {
    return GestorHechosSubidos.getHecho(id);
  }
}
