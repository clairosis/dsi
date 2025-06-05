package ar.edu.utn.frba.dds.hechos.origen;

import java.io.File;

public class Dataset implements Origen {
  private String nombreFuente;
  private String path;

  public Dataset(String path) {
    this.path = path;
    this.setNombreFuente();
  }

  // --- Setters ---

  private void setNombreFuente() {
    this.nombreFuente = new File(path).getName();
  }

  public void setPath(String path) {
    this.path = path;
  }

  // --- Getters ---

  public String getPath() {
    return this.path;
  }

  @Override
  public String getNombreFuente() {
    return this.nombreFuente;
  }
}
