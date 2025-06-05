package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.HechosEliminados;

public class HechoEliminado {
  private final Long id;
  private final String archivo;

  public HechoEliminado(Long id, String archivo) {
    this.id = id;
    this.archivo = archivo;
  }

  public Long getId() {
    return id;
  }

  public String getArchivo() {
    return archivo;
  }
}
