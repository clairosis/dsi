package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.gestores;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.SolicitudEliminacion;
import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GestorSolicitudesEliminacion {

  private static final GestorSolicitudesEliminacion instancia = new GestorSolicitudesEliminacion();
  private static final List<SolicitudEliminacion> solicitudes = new ArrayList<>();
  private static long contadorId = 0;

  public static GestorSolicitudesEliminacion getInstancia() {
    return instancia;
  }

  public List<SolicitudEliminacion> getSolicitudes() {
    return new ArrayList<SolicitudEliminacion>(solicitudes);
  }

  public List<SolicitudEliminacion> getSolicitudes(EstadoSolicitud estado) {
    return solicitudes.stream().filter(s -> s.getEstado() == estado).toList();
  }

  public Optional<SolicitudEliminacion> buscarPorId(Long id) {
    return solicitudes.stream().filter(s -> s.getId().equals(id)).findFirst();
  }

  public static SolicitudEliminacion agregarSolicitud(SolicitudEliminacion solicitud) {
    solicitud.setId(contadorId++);
    solicitudes.add(solicitud);
    return solicitud;
  }

  public void eliminarSolicitud(Long id) {
    SolicitudEliminacion solicitud =
        buscarPorId(id)
            .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada con id: " + id));
    solicitudes.remove(solicitud);
  }

  public static void eliminarSolicitudes() {
    contadorId = 0;
    solicitudes.clear();
  }
}
