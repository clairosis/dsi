package ar.edu.utn.frba.dds.hechos;

import ar.edu.utn.frba.dds.criteriodepertenencia.CriterioDePertenencia;
import ar.edu.utn.frba.dds.fuentes.Fuente;
import java.util.List;
import java.util.stream.Collectors;

public class Coleccion {
  private String identificador; // handle alfanumerico unico
  private final String titulo;
  private final String descripcion;
  private final CriterioDePertenencia criterio;
  private final Fuente fuente;

  public Coleccion(
      String identificador,
      String titulo,
      String descripcion,
      Fuente fuente,
      CriterioDePertenencia criterio) {
    this.identificador = identificador;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.fuente = fuente;
    this.criterio = criterio;
  }

  // --- Getters ---
  public String getTitulo() {
    return titulo;
  }

  public String getIdentificador() {
    return identificador;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public Fuente getFuente() {
    return fuente;
  }

  public CriterioDePertenencia getCriterio() {
    return criterio;
  }

  // aca obtengo hechos de TODAS las fuentes aplicando el criterio
  public List<Hecho> getHechos() {
    return fuente.leerHechos().stream().filter(criterio::cumple).collect(Collectors.toList());
  }

  // Versión con criterio adicional
  public List<Hecho> getHechos(CriterioDePertenencia criterioAdicional) {
    return fuente.leerHechos().stream()
        .filter(h -> criterio.cumple(h) && criterioAdicional.cumple(h))
        .collect(Collectors.toList());
  }
}
