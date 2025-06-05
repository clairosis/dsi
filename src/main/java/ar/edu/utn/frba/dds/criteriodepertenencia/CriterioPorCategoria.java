package ar.edu.utn.frba.dds.criteriodepertenencia;

import ar.edu.utn.frba.dds.hechos.Hecho;

public class CriterioPorCategoria implements CriterioDePertenencia {
  private String categoria;

  public CriterioPorCategoria(String categoria) {
    this.categoria = categoria;
  }

  @Override
  public boolean cumple(Hecho hecho) {
    return hecho.getCategoria().equalsIgnoreCase(categoria);
  }
}
