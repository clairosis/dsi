package ar.edu.utn.frba.dds.criteriodepertenencia;

import ar.edu.utn.frba.dds.hechos.Hecho;

public interface CriterioDePertenencia {
  boolean cumple(Hecho hecho);
}
