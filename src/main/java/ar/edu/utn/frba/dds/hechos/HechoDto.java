package ar.edu.utn.frba.dds.hechos;

import ar.edu.utn.frba.dds.hechos.origen.Origen;
import java.time.LocalDate;

public class HechoDto {
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

  // --- Constructor ---

  public HechoDto() {}

  public HechoDto(HechoDto hecho) {
    id = hecho.getId();
    titulo = hecho.getTitulo();
    descripcion = hecho.getDescripcion();
    categoria = hecho.getCategoria();
    latitud = hecho.getLatitud();
    longitud = hecho.getLongitud();
    fechaHecho = hecho.getFechaHecho();
    fechaCarga = hecho.getFechaCarga();
    origen = hecho.getOrigen();
    multimedia = hecho.getMultimedia();
  }

  // --- Getters ---

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getCategoria() {
    return categoria;
  }

  public Float getLatitud() {
    return latitud;
  }

  public Float getLongitud() {
    return longitud;
  }

  public LocalDate getFechaHecho() {
    return fechaHecho;
  }

  public LocalDate getFechaCarga() {
    return fechaCarga;
  }

  public Origen getOrigen() {
    return origen;
  }

  public String getMultimedia() {
    return multimedia;
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

  public void setOrigen(Origen origen) {
    this.origen = origen;
  }

  public void setMultimedia(String multimedia) {
    this.multimedia = multimedia;
  }
}
