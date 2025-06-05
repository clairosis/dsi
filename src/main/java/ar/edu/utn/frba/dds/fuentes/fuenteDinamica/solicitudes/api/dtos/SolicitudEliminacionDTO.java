package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.dtos;

public class SolicitudEliminacionDTO {
  private Long hecho;
  private String justificacion;

  // --- Getters ---

  public Long getHecho() {
    return hecho;
  }

  public String getJustificacion() {
    return justificacion;
  }

  // --- Setters ---

  public void setJustificacion(String justificacion) {
    this.justificacion = justificacion;
  }

  public void setHecho(Long hecho) {
    this.hecho = hecho;
  }
}
