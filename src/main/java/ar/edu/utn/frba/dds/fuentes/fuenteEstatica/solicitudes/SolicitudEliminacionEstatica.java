package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes;

import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;

public class SolicitudEliminacionEstatica {
  private Long id;
  private Long hecho;
  private String archivo;
  private String justificacion;
  private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  // --- Constructor ---

  private SolicitudEliminacionEstatica(Builder builder) {
    this.id = builder.id;
    this.hecho = builder.hecho;
    this.archivo = builder.archivo;
    this.justificacion = builder.justificacion;
    this.estado = builder.estado;
  }

  // --- Getters ---

  public Long getId() {
    return id;
  }

  public Long getHecho() {
    return hecho;
  }

  public String getArchivo() {
    return archivo;
  }

  public String getJustificacion() {
    return justificacion;
  }

  public EstadoSolicitud getEstado() {
    return estado;
  }

  // --- Setters ---

  public void setId(Long id) {
    this.id = id;
  }

  public void setHecho(Long hecho) {
    this.hecho = hecho;
  }

  public void setArchivo(String archivo) {
    this.archivo = archivo;
  }

  public void setJustificacion(String justificacion) {
    this.justificacion = justificacion;
  }

  // --- Metodos ----

  public void serAceptada() {
    this.estado = EstadoSolicitud.ACEPTADA;
  }

  public void serRechazada() {
    this.estado = EstadoSolicitud.RECHAZADA;
  }

  // --- Builder estático ---

  public static class Builder {
    private Long id = null;
    private Long hecho = null;
    private String archivo = null;
    private String justificacion = null;
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder hecho(Long hecho) {
      this.hecho = hecho;
      return this;
    }

    public Builder archivo(String archivo) {
      this.archivo = archivo;
      return this;
    }

    public Builder justificacion(String justificacion) {
      this.justificacion = justificacion;
      return this;
    }

    public Builder estado(EstadoSolicitud estado) {
      this.estado = estado;
      return this;
    }

    public SolicitudEliminacionEstatica build() {
      checkIfNotNull(this.hecho);
      return new SolicitudEliminacionEstatica(this);
    }

    private void checkIfNotNull(Object object) {
      if (object == null) {
        throw new IllegalStateException("No puede no tener un hecho asociado");
      }
    }
  }
}
