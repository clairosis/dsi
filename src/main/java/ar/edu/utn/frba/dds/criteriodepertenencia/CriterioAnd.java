package ar.edu.utn.frba.dds.criteriodepertenencia;

import ar.edu.utn.frba.dds.hechos.Hecho;
import java.util.Arrays;
import java.util.List;

public class CriterioAnd implements CriterioDePertenencia {
  private List<CriterioDePertenencia> criterios;

  public CriterioAnd(CriterioDePertenencia... criterios) {
    this.criterios = Arrays.asList(criterios);
  }

  @Override
  public boolean cumple(Hecho hecho) {
    return criterios.stream().allMatch(c -> c.cumple(hecho));
  }
}
