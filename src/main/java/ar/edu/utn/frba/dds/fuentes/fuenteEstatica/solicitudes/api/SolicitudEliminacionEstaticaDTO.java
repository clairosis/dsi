package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api;

public class SolicitudEliminacionEstaticaDTO {
  private Long id;
  private Long hecho;
  private String archivo;
  private String justificacion;

  // --- Getters ---

  public Long getId() {
    return id;
  }

  public Long getHecho() {
    return hecho;
  }

  public String getJustificacion() {
    return justificacion;
  }

  public String getArchivo() {
    return archivo;
  }

  // --- Setters ---

  public void setId(Long id) {
    this.id = id;
  }

  public void setHecho(Long hecho) {
    this.hecho = hecho;
  }

  public void setJustificacion(String justificacion) {
    this.justificacion = justificacion;
  }

  public void setArchivo(String archivo) {
    this.archivo = archivo;
  }
}
