package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes;

import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GestorSolicitudesEliminacionEstatica {

  private static final GestorSolicitudesEliminacionEstatica instancia =
      new GestorSolicitudesEliminacionEstatica();
  private static final List<SolicitudEliminacionEstatica> solicitudes = new ArrayList<>();
  private static long contadorId = 0;

  public static GestorSolicitudesEliminacionEstatica getInstancia() {
    return instancia;
  }

  public List<SolicitudEliminacionEstatica> getSolicitudes() {
    return new ArrayList<SolicitudEliminacionEstatica>(solicitudes);
  }

  public List<SolicitudEliminacionEstatica> getSolicitudes(EstadoSolicitud estado) {
    return solicitudes.stream().filter(s -> s.getEstado() == estado).toList();
  }

  public Optional<SolicitudEliminacionEstatica> buscarPorId(Long id) {
    return solicitudes.stream().filter(s -> s.getId().equals(id)).findFirst();
  }

  public static SolicitudEliminacionEstatica agregarSolicitud(
      SolicitudEliminacionEstatica solicitud) {
    solicitud.setId(contadorId++);
    solicitudes.add(solicitud);
    return solicitud;
  }

  public void eliminarSolicitud(Long id) {
    SolicitudEliminacionEstatica solicitud =
        buscarPorId(id)
            .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada con id: " + id));
    solicitudes.remove(solicitud);
  }

  public static void eliminarSolicitudes() {
    contadorId = 0;
    solicitudes.clear();
  }
}
