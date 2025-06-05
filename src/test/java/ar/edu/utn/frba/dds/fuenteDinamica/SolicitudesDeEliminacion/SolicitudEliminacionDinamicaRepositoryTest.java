package ar.edu.utn.frba.dds.fuenteDinamica.SolicitudesDeEliminacion;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.SolicitudEliminacion;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.SolicitudesRepository;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.gestores.GestorSolicitudesEliminacion;
import java.util.*;
import org.junit.jupiter.api.*;

class SolicitudEliminacionDinamicaRepositoryTest {

  private SolicitudesRepository repositorio;

  @BeforeEach
  void setUp() {
    repositorio = new SolicitudesRepository();
    GestorSolicitudesEliminacion.eliminarSolicitudes();
  }

  @Test
  void guardar_deberiaAgregarLaSolicitud() {
    SolicitudEliminacion solicitud =
        new SolicitudEliminacion.Builder().hecho(1L).justificacion("Solicitud test").build();

    repositorio.guardar(solicitud);

    Optional<SolicitudEliminacion> encontrada =
        GestorSolicitudesEliminacion.getInstancia().buscarPorId(solicitud.getId());

    assertTrue(encontrada.isPresent());
    assertEquals(solicitud.getJustificacion(), encontrada.get().getJustificacion());
  }

  @Test
  void buscarPorId_existente_deberiaRetornarOptionalConSolicitud() {
    SolicitudEliminacion solicitud =
        new SolicitudEliminacion.Builder().hecho(2L).justificacion("Buscarme").build();

    GestorSolicitudesEliminacion.agregarSolicitud(solicitud);

    Optional<SolicitudEliminacion> resultado = repositorio.buscarPorId(solicitud.getId());

    assertTrue(resultado.isPresent());
    assertEquals("Buscarme", resultado.get().getJustificacion());
  }

  @Test
  void buscarPorId_inexistente_deberiaRetornarOptionalEmpty() {
    Optional<SolicitudEliminacion> resultado = repositorio.buscarPorId(999L);

    assertTrue(resultado.isEmpty());
  }

  @Test
  void borrarSolicitudEliminacion_deberiaEliminarLaSolicitud() {
    SolicitudEliminacion solicitud =
        new SolicitudEliminacion.Builder().hecho(3L).justificacion("Borrame").build();

    GestorSolicitudesEliminacion.agregarSolicitud(solicitud);

    repositorio.borrarSolicitudEliminacion(solicitud);

    assertTrue(
        GestorSolicitudesEliminacion.getInstancia().buscarPorId(solicitud.getId()).isEmpty());
  }

  @Test
  void getSolicitudes_deberiaRetornarTodasLasSolicitudes() {
    SolicitudEliminacion s1 =
        new SolicitudEliminacion.Builder().hecho(4L).justificacion("Una").build();
    SolicitudEliminacion s2 =
        new SolicitudEliminacion.Builder().hecho(5L).justificacion("Dos").build();

    GestorSolicitudesEliminacion.agregarSolicitud(s1);
    GestorSolicitudesEliminacion.agregarSolicitud(s2);

    List<SolicitudEliminacion> solicitudes = repositorio.getSolicitudes();

    assertEquals(2, solicitudes.size());
    assertTrue(solicitudes.stream().anyMatch(s -> s.getHecho().equals(4L)));
    assertTrue(solicitudes.stream().anyMatch(s -> s.getHecho().equals(5L)));
  }
}
