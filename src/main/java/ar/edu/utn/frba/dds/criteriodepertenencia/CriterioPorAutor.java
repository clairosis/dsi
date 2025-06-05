package ar.edu.utn.frba.dds.criteriodepertenencia;

import ar.edu.utn.frba.dds.hechos.Hecho;

public class CriterioPorAutor implements CriterioDePertenencia {
  private final String autor;

  public CriterioPorAutor(String autor) {
    this.autor = autor;
  }

  @Override
  public boolean cumple(Hecho hecho) {
    return hecho.getOrigen().getNombreFuente().equalsIgnoreCase(autor);
  }
}
