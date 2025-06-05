package ar.edu.utn.frba.dds.fuenteDinamica.SolicitudesDeEliminacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api.FuenteDinamicaService;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.SolicitudEliminacion;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.SolicitudesRepository;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.SolicitudesService;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.dtos.SolicitudEliminacionDTO;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolicitudEliminacionDinamicaServiceTest {

  private SolicitudesRepository repositorio;
  private FuenteDinamicaService fuenteDinamicaService;
  private SolicitudesService service;

  @BeforeEach
  void setUp() {
    repositorio = mock(SolicitudesRepository.class);
    fuenteDinamicaService = mock(FuenteDinamicaService.class);
    service = new SolicitudesService(repositorio, fuenteDinamicaService);
  }

  @Test
  void crearSolicitud_valida_deberiaPersistirYRetornarDTO() {
    SolicitudEliminacionDTO dto = new SolicitudEliminacionDTO();
    dto.setHecho(42L);
    dto.setJustificacion(
        "Justificación válida que supera los 500 caracteres mínimos...".repeat(10));

    SolicitudEliminacionDTO result = service.crearSolicitud(dto);

    assertEquals(42L, result.getHecho());
    assertEquals(dto.getJustificacion(), result.getJustificacion());
    verify(repositorio).guardar(any(SolicitudEliminacion.class));
  }

  @Test
  void crearSolicitud_justificacionCorta_deberiaLanzarExcepcion() {
    SolicitudEliminacionDTO dto = new SolicitudEliminacionDTO();
    dto.setHecho(99L);
    dto.setJustificacion("A");

    RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearSolicitud(dto));
    assertTrue(ex.getMessage().contains("justificación"));
    verifyNoInteractions(repositorio);
  }

  @Test
  void procesarSolicitud_aceptar_deberiaEliminarHechoYBorrarSolicitud() {
    SolicitudEliminacion solicitudMock = mock(SolicitudEliminacion.class);
    when(solicitudMock.getHecho()).thenReturn(77L);
    when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(solicitudMock));

    service.procesarSolicitud(1L, true);

    verify(solicitudMock).serAceptada();
    verify(fuenteDinamicaService).eliminarHecho(77L);
    verify(repositorio).borrarSolicitudEliminacion(solicitudMock);
  }

  @Test
  void procesarSolicitud_rechazar_deberiaSoloRechazarYBorrarSolicitud() {
    SolicitudEliminacion solicitudMock = mock(SolicitudEliminacion.class);
    when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(solicitudMock));

    service.procesarSolicitud(1L, false);

    verify(solicitudMock).serRechazada();
    verifyNoInteractions(fuenteDinamicaService);
    verify(repositorio).borrarSolicitudEliminacion(solicitudMock);
  }

  @Test
  void procesarSolicitud_noExiste_deberiaLanzarExcepcion() {
    when(repositorio.buscarPorId(1L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> service.procesarSolicitud(1L, true));
    verifyNoInteractions(fuenteDinamicaService);
    verify(repositorio, never()).borrarSolicitudEliminacion(any());
  }

  @Test
  void listarSolicitudes_deberiaConvertirATodosLosDTOs() {
    SolicitudEliminacion solicitud =
        new SolicitudEliminacion.Builder().hecho(11L).justificacion("Validísima").build();

    when(repositorio.getSolicitudes()).thenReturn(List.of(solicitud));

    List<SolicitudEliminacionDTO> result = service.listarSolicitudes();

    assertEquals(1, result.size());
    assertEquals(11L, result.get(0).getHecho());
    assertEquals("Validísima", result.get(0).getJustificacion());
  }
}
