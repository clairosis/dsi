package ar.edu.utn.frba.dds.fuenteEstatica;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api.FuenteEstaticaRepository;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api.FuenteEstaticaService;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.FormatoCsv;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.hechos.HechoDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FuenteEstaticaServiceTest {

  @Mock private FuenteEstaticaRepository repositorio;

  private FuenteEstaticaService service;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    service = new FuenteEstaticaService(repositorio);
  }

  @Test
  public void testEliminarHecho() {
    Long id = 1L;
    String archivo = "datos.csv";

    service.eliminarHecho(id, archivo);

    verify(repositorio).eliminar(id, archivo);
  }

  @Test
  public void testObtenerHechoPorId() {
    Long id = 1L;
    String archivo = "archivo.csv";
    FormatoCsv formato = mock(FormatoCsv.class);

    Hecho hecho =
        new Hecho.Builder()
            .id(id)
            .titulo("Título")
            .descripcion("Descripción")
            .categoria("Categoría")
            .build();

    when(repositorio.buscarPorId(id, formato, archivo)).thenReturn(hecho);

    HechoDto dto = service.obtenerHechoPorId(id, formato, archivo);

    assertEquals(hecho.getId(), dto.getId());
    assertEquals(hecho.getTitulo(), dto.getTitulo());
    assertEquals(hecho.getDescripcion(), dto.getDescripcion());
    assertEquals(hecho.getCategoria(), dto.getCategoria());
    assertEquals(hecho.getLatitud(), dto.getLatitud());
    assertEquals(hecho.getLongitud(), dto.getLongitud());
    assertEquals(hecho.getFechaHecho(), dto.getFechaHecho());
    assertEquals(hecho.getFechaCarga(), dto.getFechaCarga());
    assertEquals(hecho.getOrigen(), dto.getOrigen());
    assertEquals(hecho.getMultimedia(), dto.getMultimedia());
  }

  @Test
  public void testListarHechos() {
    String archivo = "hechos.csv";
    FormatoCsv formato = mock(FormatoCsv.class);

    Hecho hecho1 = new Hecho.Builder().id(1L).titulo("Uno").build();

    Hecho hecho2 = new Hecho.Builder().id(2L).titulo("Dos").build();

    when(repositorio.leerHechos(formato, archivo)).thenReturn(List.of(hecho1, hecho2));

    List<HechoDto> hechos = service.listarHechos(formato, archivo);

    assertEquals(2, hechos.size());
    assertEquals("Uno", hechos.get(0).getTitulo());
    assertEquals("Dos", hechos.get(1).getTitulo());
  }
}
