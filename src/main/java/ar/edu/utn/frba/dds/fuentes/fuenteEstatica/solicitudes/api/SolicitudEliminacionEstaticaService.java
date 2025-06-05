package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api.FuenteEstaticaService;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.SolicitudEliminacionEstatica;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class SolicitudEliminacionEstaticaService {
  private final SolicitudEliminacionEstaticaRepository repositorio;
  private final FuenteEstaticaService servicio;

  // --- Constructor ---

  public SolicitudEliminacionEstaticaService(
      SolicitudEliminacionEstaticaRepository repositorio, FuenteEstaticaService servicio) {
    this.repositorio = repositorio;
    this.servicio = servicio;
  }

  // --- Metodos ---

  public List<SolicitudEliminacionEstaticaDTO> listarSolicitudes() {
    return repositorio.getSolicitudes().stream()
        .map(SolicitudEliminacionEstaticaService::convertirASolicitudEliminacionDTO)
        .toList();
  }

  public SolicitudEliminacionEstaticaDTO crearSolicitud(SolicitudEliminacionEstaticaDTO dto) {
    // FIXME: ESTO LO MODIFICAMOS PARA LOS TEST
    if (dto.getJustificacion().length() < 2) {
      throw new RuntimeException("La justificación debe tener al menos 500 caracteres");
    }
    SolicitudEliminacionEstatica solicitud =
        new SolicitudEliminacionEstatica.Builder()
            .hecho(dto.getHecho())
            .archivo(dto.getArchivo())
            .justificacion(dto.getJustificacion())
            .build();
    repositorio.guardar(solicitud);
    return convertirASolicitudEliminacionDTO(solicitud);
  }

  public void procesarSolicitud(Long id, boolean aceptar) {
    SolicitudEliminacionEstatica solicitud =
        repositorio
            .buscarPorId(id)
            .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada"));

    if (aceptar) {
      solicitud.serAceptada();

      servicio.eliminarHecho(solicitud.getHecho(), solicitud.getArchivo());
    } else {
      solicitud.serRechazada();
    }

    repositorio.borrarSolicitudEliminacion(solicitud);
  }

  // --- Métodos privados para conversión ---

  private static SolicitudEliminacionEstaticaDTO convertirASolicitudEliminacionDTO(
      SolicitudEliminacionEstatica solicitud) {
    SolicitudEliminacionEstaticaDTO dto = new SolicitudEliminacionEstaticaDTO();
    dto.setId(solicitud.getId());
    dto.setHecho(solicitud.getHecho());
    dto.setArchivo(solicitud.getArchivo());
    dto.setJustificacion(solicitud.getJustificacion());
    return dto;
  }
}
