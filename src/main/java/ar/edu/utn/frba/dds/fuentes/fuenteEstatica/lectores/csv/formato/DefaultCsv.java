package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.HechosEliminados.GestorEliminadosFuenteEstatica;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.hechos.origen.Dataset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DefaultCsv extends FormatoCsv {

  public DefaultCsv() {
    this.campos =
        List.of("Titulo", "Descripcion", "Categoria", "Latitud", "Longitud", "FechaDelHecho");
  }

  @Override
  public List<Hecho> importarHechos(CSVParser csvParser, String path) {
    List<Hecho> hechos = new ArrayList<>();
    List<Long> hechosEliminados =
        GestorEliminadosFuenteEstatica.getInstancia().getHechosEliminados(path);
    Dataset origen = new Dataset(path);
    long contador = 0;

    for (CSVRecord record : csvParser) {
      if (!hechosEliminados.contains(contador)) {
        try {
          Hecho hecho = construirHecho(record, origen, contador);
          hecho.setId(contador);

          if (hechos.stream().noneMatch(h -> h.getTitulo().equals(hecho.getTitulo()))) {
            hechos.add(hecho);
          }

        } catch (IllegalArgumentException e) {
          throw new RuntimeException(
              "Error al procesar registro #" + record.getRecordNumber() + ". " + e.getMessage());
        }
      }
      contador++;
    }

    return hechos;
  }

  private Hecho construirHecho(CSVRecord record, Dataset origen, Long id) {
    Hecho.Builder builder = new Hecho.Builder();

    return builder
        .id(id)
        .titulo(obtenerCampoObligatorio(record, "Titulo"))
        .descripcion(obtenerCampoObligatorio(record, "Descripcion"))
        .categoria(obtenerCampoObligatorio(record, "Categoria"))
        .latitud(parsearFloat(record.get("Latitud"), "Latitud"))
        .longitud(parsearFloat(record.get("Longitud"), "Longitud"))
        .origen(origen)
        .fechaHecho(parsearFecha(record.get("FechaDelHecho")))
        .build();
  }
}
