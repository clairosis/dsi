package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.SolicitudEliminacion;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.SolicitudModificacion;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.gestores.GestorSolicitudesEliminacion;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.gestores.GestorSolicitudesModificacion;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SolicitudesRepository {

  // --- Solicitudes Eliminacion ---

  public void guardarSolicitudEliminacion(SolicitudEliminacion solicitud) {
    GestorSolicitudesEliminacion.agregarSolicitud(solicitud);
  }

  public Optional<SolicitudEliminacion> buscarPorIdEliminacion(Long id) {
    return GestorSolicitudesEliminacion.getInstancia().buscarPorId(id);
  }

  public void borrarSolicitudEliminacion(SolicitudEliminacion solicitud) {
    GestorSolicitudesEliminacion.getInstancia().eliminarSolicitud(solicitud.getId());
  }

  // --- Solicitudes Modificacion ---

  public Optional<SolicitudModificacion> buscarPorIdModificacion(Long id) {
    return GestorSolicitudesModificacion.getInstancia().buscarPorId(id);
  }

  public void guardarSolicitudModificacion(SolicitudModificacion solicitud) {
    GestorSolicitudesModificacion.agregarSolicitud(solicitud);
  }

  public void borrarSolicitudModificacion(SolicitudModificacion solicitud) {
    GestorSolicitudesModificacion.getInstancia().eliminarSolicitud(solicitud.getId());
  }
}
