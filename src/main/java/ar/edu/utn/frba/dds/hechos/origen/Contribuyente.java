package ar.edu.utn.frba.dds.hechos.origen;

public class Contribuyente implements Origen {
  private final String nombreFuente;
  private String apellidoFuente;
  private int edadFuente;

  public Contribuyente(String nombreFuente) {
    this.validarObjetoNoNulo(nombreFuente, "Nombre");
    this.nombreFuente = nombreFuente;
  }

  // --- Setters ---

  public void setApellidoFuente(String apellidoFuente) {
    this.validarObjetoNoNulo(apellidoFuente, "Apellido");
    this.apellidoFuente = apellidoFuente;
  }

  public void setEdadFuente(int edadFuente) {
    this.validarObjetoNoNulo(edadFuente, "Edad");
    this.edadFuente = edadFuente;
  }

  // -- Getters ---

  @Override
  public String getNombreFuente() {
    return this.nombreFuente;
  }

  public String getApellidoFuente() {
    return this.apellidoFuente;
  }

  public int getEdadFuente(int edadFuente) {
    return this.edadFuente;
  }

  // --- Validaciones ---
  private void validarObjetoNoNulo(Object object, String campo) {
    if (object == null) {
      throw new RuntimeException("El campo \"" + campo + "\" no debe estar vacío.");
    }
  }
}
