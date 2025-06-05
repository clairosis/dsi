package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes;

import ar.edu.utn.frba.dds.hechos.HechoDto;
import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;

public class SolicitudModificacion {
  private Long id;
  private Long hecho;
  private String motivo;
  private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;
  private HechoDto modificaciones;

  // --- Constructor ---

  private SolicitudModificacion(Builder builder) {
    this.id = builder.id;
    this.hecho = builder.hecho;
    this.motivo = builder.motivo;
    this.estado = builder.estado;
    this.modificaciones = builder.modificaciones;
  }

  // --- Getters ---

  public Long getId() {
    return id;
  }

  public Long getHecho() {
    return hecho;
  }

  public String getMotivo() {
    return motivo;
  }

  public EstadoSolicitud getEstado() {
    return estado;
  }

  public HechoDto getModificaciones() {
    return new HechoDto(modificaciones);
  }

  // --- Setters ---

  public void setId(Long id) {
    this.id = id;
  }

  public void setEstado(EstadoSolicitud estado) {
    this.estado = estado;
  }

  public void setHecho(Long hecho) {
    this.hecho = hecho;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public void setModificaciones(HechoDto modificaciones) {
    this.modificaciones = new HechoDto(modificaciones);
  }

  // --- Metodos ---

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
    private String motivo = null;
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;
    private HechoDto modificaciones = null;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder hecho(Long hecho) {
      this.hecho = hecho;
      return this;
    }

    public Builder motivo(String motivo) {
      this.motivo = motivo;
      return this;
    }

    public Builder estado(EstadoSolicitud estado) {
      this.estado = estado;
      return this;
    }

    public Builder modificaciones(HechoDto modificaciones) {
      this.modificaciones = new HechoDto(modificaciones);
      return this;
    }

    public SolicitudModificacion build() {
      checkIfNotNull(this.hecho);
      checkIfNotNull(this.modificaciones);
      return new SolicitudModificacion(this);
    }

    private void checkIfNotNull(Object o) {
      if (o == null) {
        throw new IllegalStateException("No se puede estar vacio");
      }
    }
  }
}
