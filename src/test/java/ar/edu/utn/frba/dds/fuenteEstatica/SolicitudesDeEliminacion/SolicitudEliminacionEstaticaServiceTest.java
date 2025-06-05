package ar.edu.utn.frba.dds.fuenteEstatica.SolicitudesDeEliminacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api.FuenteEstaticaService;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.SolicitudEliminacionEstatica;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api.SolicitudEliminacionEstaticaDTO;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api.SolicitudEliminacionEstaticaRepository;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api.SolicitudEliminacionEstaticaService;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class SolicitudEliminacionEstaticaServiceTest {

  @Mock private SolicitudEliminacionEstaticaRepository repositorio;

  @Mock private FuenteEstaticaService fuenteEstaticaService;

  @InjectMocks private SolicitudEliminacionEstaticaService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testListarSolicitudes() {
    SolicitudEliminacionEstatica solicitud =
        new SolicitudEliminacionEstatica.Builder()
            .hecho(0L)
            .archivo("archivo.csv")
            .justificacion("Justificación válida".repeat(50))
            .build();

    when(repositorio.getSolicitudes()).thenReturn(List.of(solicitud));

    List<SolicitudEliminacionEstaticaDTO> resultado = service.listarSolicitudes();

    assertEquals(1, resultado.size());
    assertEquals("archivo.csv", resultado.get(0).getArchivo());
  }

  @Test
  void testCrearSolicitud_Valida() {
    SolicitudEliminacionEstaticaDTO dto = new SolicitudEliminacionEstaticaDTO();
    dto.setHecho(0L);
    dto.setArchivo("archivo.csv");
    dto.setJustificacion("Justificación válida con más de 500 caracteres ".repeat(10));

    SolicitudEliminacionEstaticaDTO creado = service.crearSolicitud(dto);

    verify(repositorio).guardar(any(SolicitudEliminacionEstatica.class));
    assertEquals(dto.getArchivo(), creado.getArchivo());
  }

  @Test
  void testCrearSolicitud_JustificacionMuyCorta() {
    SolicitudEliminacionEstaticaDTO dto = new SolicitudEliminacionEstaticaDTO();
    dto.setHecho(0L);
    dto.setArchivo("archivo.csv");
    dto.setJustificacion("x"); // Muy corta

    RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearSolicitud(dto));
    assertEquals("La justificación debe tener al menos 500 caracteres", ex.getMessage());
  }

  @Test
  void testProcesarSolicitudAceptar() {
    String archivo = "archivo.csv";

    SolicitudEliminacionEstatica solicitud =
        new SolicitudEliminacionEstatica.Builder()
            .hecho(0L)
            .archivo(archivo)
            .justificacion("Justificación válida".repeat(50))
            .build();

    when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(solicitud));

    service.procesarSolicitud(1L, true);

    verify(fuenteEstaticaService).eliminarHecho(0L, archivo);
    verify(repositorio).borrarSolicitudEliminacion(solicitud);
  }

  @Test
  void testProcesarSolicitudRechazar() {
    SolicitudEliminacionEstatica solicitud =
        new SolicitudEliminacionEstatica.Builder()
            .hecho(0L)
            .archivo("archivo.csv")
            .justificacion("Justificación válida".repeat(50))
            .build();

    when(repositorio.buscarPorId(2L)).thenReturn(Optional.of(solicitud));

    service.procesarSolicitud(2L, false);

    verify(fuenteEstaticaService, never()).eliminarHecho(any(), anyString());
    verify(repositorio).borrarSolicitudEliminacion(solicitud);
  }

  @Test
  void testProcesarSolicitudNoEncontrada() {
    when(repositorio.buscarPorId(99L)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> service.procesarSolicitud(99L, true));
  }
}
