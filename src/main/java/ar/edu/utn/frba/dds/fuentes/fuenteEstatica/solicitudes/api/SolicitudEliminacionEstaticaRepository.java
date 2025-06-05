package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.GestorSolicitudesEliminacionEstatica;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.SolicitudEliminacionEstatica;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SolicitudEliminacionEstaticaRepository {

  public void guardar(SolicitudEliminacionEstatica solicitud) {
    GestorSolicitudesEliminacionEstatica.agregarSolicitud(solicitud);
  }

  public Optional<SolicitudEliminacionEstatica> buscarPorId(Long id) {
    return GestorSolicitudesEliminacionEstatica.getInstancia().buscarPorId(id);
  }

  public void borrarSolicitudEliminacion(SolicitudEliminacionEstatica solicitud) {
    GestorSolicitudesEliminacionEstatica.getInstancia().eliminarSolicitud(solicitud.getId());
  }

  public List<SolicitudEliminacionEstatica> getSolicitudes() {
    return GestorSolicitudesEliminacionEstatica.getInstancia().getSolicitudes();
  }
}
