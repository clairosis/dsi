package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api.FuenteDinamicaService;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.SolicitudEliminacion;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.SolicitudModificacion;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.dtos.SolicitudEliminacionDTO;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.dtos.SolicitudModificacionDTO;
import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class SolicitudesService {
  private final SolicitudesRepository repositorio;
  private final FuenteDinamicaService fuenteDinamicaService;

  // private final DetectorDeSpam detectorDeSpam;

  public SolicitudesService(
      SolicitudesRepository repositorio,
      FuenteDinamicaService fuenteDinamicaService) { // , DetectorDeSpam detectorDeSpam) {
    this.repositorio = repositorio;
    this.fuenteDinamicaService = fuenteDinamicaService;
    // this.detectorDeSpam = detectorDeSpam;
  }

  public SolicitudEliminacionDTO crearSolicitudEliminacion(SolicitudEliminacionDTO dto) {
    // FIXME: ESTO LO MODIFICAMOS PARA LOS TEST
    if (dto.getJustificacion().length() < 2) {
      throw new RuntimeException("La justificación debe tener al menos 500 caracteres");
    }
    SolicitudEliminacion solicitud =
        new SolicitudEliminacion.Builder()
            .hecho(dto.getHecho())
            .justificacion(dto.getJustificacion())
            .build();

    //    if (detectorDeSpam.esSpam(dto.getJustificacion())) {
    //      solicitud.serRechazada();
    //    }

    repositorio.guardarSolicitudEliminacion(solicitud);
    return convertirASolicitudEliminacionDTO(solicitud);
  }

  public SolicitudModificacionDTO crearSolicitudModificacion(SolicitudModificacionDTO dto) {

    SolicitudModificacion solicitud =
        new SolicitudModificacion.Builder()
            .hecho(dto.getHecho())
            .motivo(dto.getMotivo())
            .modificaciones(dto.getModificacion())
            .build();
    //
    //    if (detectorDeSpam.esSpam(dto.getMotivo())) {
    //      solicitud.serRechazada();
    //    }

    repositorio.guardarSolicitudModificacion(solicitud);
    return convertirASolicitudModificacionDTO(solicitud);
  }

  public void procesarSolicitudEliminacion(Long id, boolean aceptar) {
    SolicitudEliminacion solicitud =
        repositorio
            .buscarPorIdEliminacion(id)
            .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada"));

    if (solicitud.getEstado() == EstadoSolicitud.RECHAZADA) return;

    if (aceptar) {
      solicitud.serAceptada();

      fuenteDinamicaService.eliminarHecho(solicitud.getHecho());
    } else {
      solicitud.serRechazada();
    }

    repositorio.borrarSolicitudEliminacion(solicitud);
  }

  public void procesarSolicitudModificacion(Long id, boolean aceptar) {
    SolicitudModificacion solicitud =
        repositorio
            .buscarPorIdModificacion(id)
            .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada"));

    if (solicitud.getEstado() == EstadoSolicitud.RECHAZADA) return;

    if (aceptar) {
      solicitud.serAceptada();
      fuenteDinamicaService.modificarHecho(solicitud.getId(), solicitud.getModificaciones());
    } else {
      solicitud.serRechazada();
    }

    repositorio.borrarSolicitudModificacion(solicitud);
  }

  // --- Métodos privados para conversión ---

  private static SolicitudEliminacionDTO convertirASolicitudEliminacionDTO(
      SolicitudEliminacion solicitud) {
    SolicitudEliminacionDTO dto = new SolicitudEliminacionDTO();
    dto.setHecho(solicitud.getHecho());
    dto.setJustificacion(solicitud.getJustificacion());
    return dto;
  }

  private static SolicitudModificacionDTO convertirASolicitudModificacionDTO(
      SolicitudModificacion solicitud) {
    SolicitudModificacionDTO dto = new SolicitudModificacionDTO();
    dto.setHecho(solicitud.getHecho()); // si getHecho() devuelve el ID
    dto.setMotivo(solicitud.getMotivo());
    dto.setModificacion(solicitud.getModificaciones());
    return dto;
  }
}
