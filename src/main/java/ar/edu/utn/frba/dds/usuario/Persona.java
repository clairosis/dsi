package ar.edu.utn.frba.dds.usuario;

import ar.edu.utn.frba.dds.criteriodepertenencia.CriterioDePertenencia;
import ar.edu.utn.frba.dds.hechos.Coleccion;
import ar.edu.utn.frba.dds.hechos.Hecho;
// import ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion.SolicitudEliminacion;
import java.util.List;

public class Persona {
  private String nombre = null;
  private String apellido = null;
  private int edad = 0;
  private boolean esContribuyente = false;

  // --- Setters ---

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  // --- Getters ---

  public String getNombre() {
    return this.nombre;
  }

  public String getApellido() {
    return this.apellido;
  }

  public int getEdad() {
    return this.edad;
  }

  // --- Metodos ---

  public void solicitarEliminacionDeHecho(Hecho unHecho, String justificacion) {
    this.verificarSiEsContribuyente();
    // new SolicitudEliminacion(unHecho, justificacion);
  }

  public List<Hecho> visualizarHechos(Coleccion coleccion) {
    return coleccion.getHechos();
  }

  public List<Hecho> visualizarHechos(Coleccion coleccion, CriterioDePertenencia criterio) {
    return coleccion.getHechos(criterio);
  }

  // TODO: PARA PROXIMAS ENTREGAS
  public void subirHecho(Hecho hecho) {
    this.verificarCamposObligatorios();
    // TODO: TERMINAR ACA
    esContribuyente = true;
  }

  public boolean esContribuyente() {
    return this.esContribuyente;
  }

  // --- Verificaciones ---

  private void verificarCamposObligatorios() {
    if (this.nombre == null) {
      throw new RuntimeException("El campo \"Nombre\" es obligatorio para subir hechos");
    }
  }

  private void verificarSiEsContribuyente() {
    if (!this.esContribuyente) {
      throw new RuntimeException("Solo los contribuyentes pueden realizar solicitudes");
    }
  }
}
