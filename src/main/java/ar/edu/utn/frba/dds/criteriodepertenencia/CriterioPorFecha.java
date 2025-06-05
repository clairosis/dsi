package ar.edu.utn.frba.dds.criteriodepertenencia;

import ar.edu.utn.frba.dds.hechos.Hecho;
import java.time.LocalDate;

public class CriterioPorFecha implements CriterioDePertenencia {
  private final LocalDate desde;
  private final LocalDate hasta;

  // --- Constructores ---

  public CriterioPorFecha(LocalDate dia) {
    this.desde = dia;
    this.hasta = dia;
  }

  public CriterioPorFecha(LocalDate desde, LocalDate hasta) {
    this.desde = desde;
    this.hasta = hasta;
  }

  @Override
  public boolean cumple(Hecho hecho) {
    LocalDate fechaHecho = hecho.getFechaHecho();
    return (fechaHecho.isEqual(desde) || fechaHecho.isAfter(desde))
        && (fechaHecho.isEqual(hasta) || fechaHecho.isBefore(hasta));
  }
}
