package ar.edu.utn.frba.dds.hechos;

import ar.edu.utn.frba.dds.hechos.origen.Origen;
import java.time.LocalDate;

public class Hecho {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private Float latitud;
  private Float longitud;
  private LocalDate fechaHecho;
  private LocalDate fechaCarga;
  private final Origen origen;
  private String multimedia;

  // --- Constructor para usar con builder ---

  public Hecho(Hecho hecho) {
    this.id = hecho.id;
    this.titulo = hecho.titulo;
    this.descripcion = hecho.descripcion;
    this.categoria = hecho.categoria;
    this.latitud = hecho.latitud;
    this.longitud = hecho.longitud;
    this.fechaHecho = hecho.fechaHecho;
    this.fechaCarga = hecho.fechaCarga;
    this.origen = hecho.origen;
    this.multimedia = hecho.multimedia;
  }

  private Hecho(Builder builder) {
    this.id = builder.id;
    this.titulo = builder.titulo;
    this.descripcion = builder.descripcion;
    this.categoria = builder.categoria;
    this.latitud = builder.latitud;
    this.longitud = builder.longitud;
    this.fechaHecho = builder.fechaHecho;
    this.fechaCarga = builder.fechaCarga;
    this.origen = builder.origen;
    this.multimedia = builder.multimedia;
  }

  // --- Getters ---

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public String getCategoria() {
    return this.categoria;
  }

  public Float getLatitud() {
    return this.latitud;
  }

  public Float getLongitud() {
    return this.longitud;
  }

  public LocalDate getFechaHecho() {
    return this.fechaHecho;
  }

  public LocalDate getFechaCarga() {
    return this.fechaCarga;
  }

  public Origen getOrigen() {
    return this.origen;
  }

  public String getMultimedia() {
    return this.multimedia;
  }

  // --- Setters ---

  public void setId(Long id) {
    this.id = id;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public void setLatitud(Float latitud) {
    this.latitud = latitud;
  }

  public void setLongitud(Float longitud) {
    this.longitud = longitud;
  }

  public void setFechaHecho(LocalDate fechaHecho) {
    this.fechaHecho = fechaHecho;
  }

  public void setFechaCarga(LocalDate fechaCarga) {
    this.fechaCarga = fechaCarga;
  }

  public void setMultimedia(String multimedia) {
    this.multimedia = multimedia;
  }

  // --- Builder estático ---

  public static class Builder {
    private Long id = null;
    private String titulo = null;
    private String descripcion = null;
    private String categoria = null;
    private Float latitud = null;
    private Float longitud = null;
    private LocalDate fechaHecho = null;
    private LocalDate fechaCarga = null;
    private Origen origen = null;
    private String multimedia = null;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder titulo(String titulo) {
      this.titulo = titulo;
      return this;
    }

    public Builder descripcion(String descripcion) {
      this.descripcion = descripcion;
      return this;
    }

    public Builder categoria(String categoria) {
      this.categoria = categoria;
      return this;
    }

    public Builder latitud(Float latitud) {
      this.latitud = latitud;
      return this;
    }

    public Builder longitud(Float longitud) {
      this.longitud = longitud;
      return this;
    }

    public Builder fechaHecho(LocalDate fechaHecho) {
      this.fechaHecho = fechaHecho;
      return this;
    }

    private void fechaCarga() {
      this.fechaCarga = LocalDate.now();
    }

    public Builder origen(Origen origen) {
      this.origen = origen;
      return this;
    }

    public Builder multimedia(String multimedia) {
      this.multimedia = multimedia;
      return this;
    }

    public Hecho build() {
      checkIfNotNull(this.titulo);

      this.fechaCarga();

      return new Hecho(this);
    }

    private void checkIfNotNull(Object object) {
      if (object == null) {
        throw new IllegalStateException("Titulo no puede ser null");
      }
    }
  }
}
