package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.dtos;

import ar.edu.utn.frba.dds.hechos.HechoDto;

public class SolicitudModificacionDTO {
  private Long hecho;
  private String motivo;
  private HechoDto modificaciones;

  // --- Getters ---

  public Long getHecho() {
    return hecho;
  }

  public String getMotivo() {
    return motivo;
  }

  public HechoDto getModificacion() {
    return new HechoDto(modificaciones);
  }

  // --- Setters ---

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public void setHecho(Long hecho) {
    this.hecho = hecho;
  }

  public void setModificacion(HechoDto modificaciones) {
    this.modificaciones = new HechoDto(modificaciones);
  }
}
