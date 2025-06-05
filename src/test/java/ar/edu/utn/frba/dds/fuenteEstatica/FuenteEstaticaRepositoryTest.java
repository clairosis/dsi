package ar.edu.utn.frba.dds.fuenteEstatica;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api.FuenteEstaticaRepository;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.LectorCsv;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.FormatoCsv;
import ar.edu.utn.frba.dds.hechos.Hecho;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FuenteEstaticaRepositoryTest {

  @Mock private LectorCsv lectorCsv;

  private FuenteEstaticaRepository repository;

  private FormatoCsv formato =
      mock(FormatoCsv.class); // Podés reemplazar con un objeto real si es necesario
  private final String archivo = "hechos.csv";

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    repository = new FuenteEstaticaRepository(lectorCsv);
  }

  @Test
  public void testLeerHechos_devuelveListaDeHechos() {
    Hecho hecho1 = new Hecho.Builder().titulo("Titulo1").build();
    Hecho hecho2 = new Hecho.Builder().titulo("Titulo2").build();

    when(lectorCsv.importarHechos(archivo, formato)).thenReturn(List.of(hecho1, hecho2));

    List<Hecho> hechos = repository.leerHechos(formato, archivo);

    assertEquals(2, hechos.size());
    assertTrue(hechos.contains(hecho1));
    assertTrue(hechos.contains(hecho2));
  }

  @Test
  public void testBuscarPorId_devuelveHechoCorrecto() {
    Hecho hecho = new Hecho.Builder().id(42L).titulo("hecho importante").build();
    when(lectorCsv.importarHechos(archivo, formato)).thenReturn(List.of(hecho));

    Hecho resultado = repository.buscarPorId(42L, formato, archivo);

    assertNotNull(resultado);
    assertEquals(42L, resultado.getId());
    assertEquals("hecho importante", resultado.getTitulo());
  }

  @Test
  public void testEliminar_invocaGestor() {
    Long id = 99L;

    assertDoesNotThrow(() -> repository.eliminar(id, archivo));
  }
}
