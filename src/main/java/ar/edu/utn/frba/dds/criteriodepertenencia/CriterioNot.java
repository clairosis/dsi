package ar.edu.utn.frba.dds.criteriodepertenencia;

import ar.edu.utn.frba.dds.hechos.Hecho;

public class CriterioNot implements CriterioDePertenencia {
  private CriterioDePertenencia criterio;

  public CriterioNot(CriterioDePertenencia criterio) {
    this.criterio = criterio;
  }

  @Override
  public boolean cumple(Hecho hecho) {
    return !criterio.cumple(hecho);
  }
}
