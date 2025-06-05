package ar.edu.utn.frba.dds.fuenteEstatica;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.HechosEliminados.GestorEliminadosFuenteEstatica;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.LectorCsv;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.DefaultCsv;
// import ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion.SolicitudEliminacion;
import ar.edu.utn.frba.dds.hechos.GestorHechosEliminados;
import ar.edu.utn.frba.dds.hechos.Hecho;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LectorCsvTest {
  private final LectorCsv lector = new LectorCsv();
  private String path;

  @BeforeEach
  void setUp() {
    GestorHechosEliminados.limpiarLista();
  }

  @Test
  void importaCSVCorrectamente() {
    this.path = "tests/importarArchivoCSV.csv";

    List<Hecho> hechos = this.lector.importarHechos(this.path, new DefaultCsv());

    assertEquals(2, hechos.size());

    Hecho hecho = hechos.get(0);
    assertEquals("Foco de incendio", hecho.getTitulo());
    assertEquals("Bosque afectado", hecho.getDescripcion());
    assertEquals("Incendio forestal", hecho.getCategoria());
    assertEquals(LocalDate.of(2025, 3, 14), hecho.getFechaHecho());
    assertEquals("importarArchivoCSV.csv", hecho.getOrigen().getNombreFuente());

    hecho = hechos.get(1);
    assertEquals("Se inunda Bahia", hecho.getTitulo());
    assertEquals("Cuidad afectada", hecho.getDescripcion());
    assertEquals("Inundacion", hecho.getCategoria());
    assertEquals(LocalDate.of(2025, 3, 14), hecho.getFechaHecho());
    assertEquals("importarArchivoCSV.csv", hecho.getOrigen().getNombreFuente());
  }

  @Test
  void leerArchivoVacio() {
    this.path = "tests/leerArchivoVacio.csv";

    Exception excepcion =
        Assertions.assertThrows(
            Exception.class, () -> this.lector.importarHechos(this.path, new DefaultCsv()));

    assertEquals(
        "Error al leer CSV: El archivo CSV está vacío o no contiene registros.",
        excepcion.getMessage());
  }

  @Test
  void formatoDeTablaIncorrecto() {
    this.path = "tests/formatoIncorrecto.csv";

    Exception excepcion =
        Assertions.assertThrows(
            Exception.class, () -> this.lector.importarHechos(this.path, new DefaultCsv()));

    assertEquals(
        "Error al leer CSV: Las columnas del CSV no tienen el formato esperado.\nSe esperaba: [Titulo, Descripcion, Categoria, Latitud, Longitud, FechaDelHecho]",
        excepcion.getMessage());
  }

  @Test
  void tituloVacio() {
    this.path = "tests/tituloVacio.csv";

    Exception excepcion =
        Assertions.assertThrows(
            Exception.class, () -> this.lector.importarHechos(this.path, new DefaultCsv()));

    assertEquals(
        "Error al leer CSV: Error al procesar registro #1. El campo 'Titulo' está vacío",
        excepcion.getMessage());
  }

  @Test
  void latitudInvalida() {
    this.path = "tests/latitudInvalida.csv";

    Exception excepcion =
        Assertions.assertThrows(
            Exception.class, () -> this.lector.importarHechos(this.path, new DefaultCsv()));

    assertEquals(
        "Error al leer CSV: Error al procesar registro #1. Latitud inválida",
        excepcion.getMessage());
  }

  @Test
  void noDebeHaberDuplicadosPorTitulo() {
    this.path = "tests/duplicados.csv";

    List<Hecho> hechos = this.lector.importarHechos(this.path, new DefaultCsv());

    assertEquals(1, hechos.size());

    Hecho hecho = hechos.get(0);
    assertEquals("Corte de luz", hecho.getTitulo());
    assertEquals("Primera version", hecho.getDescripcion()); // Verificamos que pisó bien
  }

  @Test
  void noImportoLosHechosEliminados() {
    this.path = "tests/hechosEliminadosTest.csv";

    List<Hecho> hechos = lector.importarHechos(path, new DefaultCsv());

    assertEquals(10, hechos.size());

    GestorEliminadosFuenteEstatica.eliminarHecho(0L, this.path);
    GestorEliminadosFuenteEstatica.eliminarHecho(1L, this.path);
    GestorEliminadosFuenteEstatica.eliminarHecho(2L, this.path);
    GestorEliminadosFuenteEstatica.eliminarHecho(3L, this.path);
    GestorEliminadosFuenteEstatica.eliminarHecho(4L, this.path);

    hechos = lector.importarHechos(path, new DefaultCsv());

    assertEquals(5, hechos.size());
  }

  //  @Test
  //  void testImportacionDesdeArchivoReal() {
  //    this.path = "fuentesDeEjemplo/FuenteEjemplo2.csv";
  //    String pathSalida = "tests/testFuenteReal.csv";
  //
  //    List<Hecho> hechos = lector.importarHechos(path, new IncendiosForestalesCsv());
  //
  //    // Verificamos que se hayan importado hechos
  //    assertFalse(hechos.isEmpty());
  //
  //    for (Hecho hecho : hechos) {
  //      assertNotNull(hecho.getTitulo());
  //      assertFalse(hecho.getTitulo().isBlank());
  //
  //      assertNotNull(hecho.getLatitud());
  //      assertNotNull(hecho.getLongitud());
  //
  //      assertNotNull(hecho.getFechaHecho());
  //      assertNotNull(hecho.getDescripcion());
  //    }

  // COMENTO ESTA PARTE PARA NO ANDAR ESCRIBIENDO UN ARCHIVO NUEVO CADA VEZ QUE SE TESTEE
  //    // Exportamos los hechos al archivo CSV de salida
  //    try (Writer writer =
  //            new OutputStreamWriter(new FileOutputStream(pathSalida), StandardCharsets.UTF_8);
  //        CSVPrinter csvPrinter =
  //            new CSVPrinter(
  //                writer,
  //                CSVFormat.DEFAULT.withHeader(
  //                    "Titulo",
  //                    "Descripcion",
  //                    "Categoria",
  //                    "Latitud",
  //                    "Longitud",
  //                    "FechaDelHecho"))) {
  //      for (Hecho hecho : hechos) {
  //        csvPrinter.printRecord(
  //            hecho.getTitulo(),
  //            hecho.getDescripcion(),
  //            hecho.getCategoria(),
  //            hecho.getLatitud(),
  //            hecho.getLongitud(),
  //            hecho.getFechaHecho());
  //      }
  //      csvPrinter.flush();
  //    } catch (IOException e) {
  //      fail("Error al exportar los hechos al CSV: " + e.getMessage());
  //    }
  //
  //    File archivoExportado = new File(pathSalida);
  //    assertTrue(archivoExportado.exists());
  //  }
}
