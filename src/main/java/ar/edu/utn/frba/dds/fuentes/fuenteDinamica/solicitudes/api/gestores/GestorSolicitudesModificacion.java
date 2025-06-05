package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.gestores;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.SolicitudModificacion;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GestorSolicitudesModificacion {
  private static final GestorSolicitudesModificacion instancia =
      new GestorSolicitudesModificacion();
  private static final List<SolicitudModificacion> solicitudes = new ArrayList<>();
  private static long contadorId = 0;

  public static GestorSolicitudesModificacion getInstancia() {
    return instancia;
  }

  public Optional<SolicitudModificacion> buscarPorId(Long id) {
    return solicitudes.stream().filter(s -> s.getId().equals(id)).findFirst();
  }

  public List<SolicitudModificacion> getSolicitudes() {
    return new ArrayList<SolicitudModificacion>(solicitudes);
  }

  public static SolicitudModificacion agregarSolicitud(SolicitudModificacion solicitud) {
    solicitud.setId(contadorId++);
    solicitudes.add(solicitud);
    return solicitud;
  }

  public void eliminarSolicitud(Long id) {
    SolicitudModificacion solicitud =
        buscarPorId(id)
            .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada con id: " + id));
    solicitudes.remove(solicitud);
  }

  public static void eliminarSolicitudes() {
    contadorId = 0;
    solicitudes.clear();
  }
}
