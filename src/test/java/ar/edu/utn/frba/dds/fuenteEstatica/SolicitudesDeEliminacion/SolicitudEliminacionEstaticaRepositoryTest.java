package ar.edu.utn.frba.dds.fuenteEstatica.SolicitudesDeEliminacion;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.GestorSolicitudesEliminacionEstatica;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.SolicitudEliminacionEstatica;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api.SolicitudEliminacionEstaticaRepository;
import java.util.*;
import org.junit.jupiter.api.*;

class SolicitudEliminacionEstaticaRepositoryTest {

  private SolicitudEliminacionEstaticaRepository repository;
  private GestorSolicitudesEliminacionEstatica gestor;

  @BeforeEach
  void setUp() {
    repository = new SolicitudEliminacionEstaticaRepository();
    gestor = GestorSolicitudesEliminacionEstatica.getInstancia();
    gestor.getSolicitudes().clear(); // Resetear estado
  }

  @Test
  void testGuardarYBuscarPorId() {
    SolicitudEliminacionEstatica solicitud =
        new SolicitudEliminacionEstatica.Builder()
            .hecho(0L)
            .archivo("archivo.csv")
            .justificacion("justificación larga")
            .build();

    repository.guardar(solicitud);

    Optional<SolicitudEliminacionEstatica> resultado = repository.buscarPorId(solicitud.getId());

    assertTrue(resultado.isPresent());
    assertEquals("archivo.csv", resultado.get().getArchivo());
  }

  @Test
  void testBorrarSolicitud() {
    SolicitudEliminacionEstatica solicitud =
        new SolicitudEliminacionEstatica.Builder()
            .hecho(0L)
            .archivo("archivo.csv")
            .justificacion("justificación")
            .build();

    repository.guardar(solicitud);
    assertTrue(repository.buscarPorId(solicitud.getId()).isPresent());

    repository.borrarSolicitudEliminacion(solicitud);

    assertFalse(repository.buscarPorId(solicitud.getId()).isPresent());
  }

  @Test
  void testGetSolicitudes() {
    SolicitudEliminacionEstatica solicitud1 =
        new SolicitudEliminacionEstatica.Builder()
            .hecho(0L)
            .archivo("a.csv")
            .justificacion("justificación")
            .build();

    SolicitudEliminacionEstatica solicitud2 =
        new SolicitudEliminacionEstatica.Builder()
            .hecho(0L)
            .archivo("x.csv")
            .justificacion("justificación")
            .build();

    repository.guardar(solicitud1);
    repository.guardar(solicitud2);

    List<SolicitudEliminacionEstatica> solicitudes = repository.getSolicitudes();

    assertEquals(2, solicitudes.size());
    assertTrue(solicitudes.contains(solicitud1));
    assertTrue(solicitudes.contains(solicitud2));
  }
}
