package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.FormatoCsv;
import ar.edu.utn.frba.dds.hechos.Hecho;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;

@Component
public class LectorCsv {

  public List<Hecho> importarHechos(String path, FormatoCsv formato) {
    path = "./inc/fuentesEstaticas/" + path;

    try (CSVParser csvParser = crearParser(path)) {

      if (!csvParser.iterator().hasNext()) {
        throw new Exception("El archivo CSV está vacío o no contiene registros.");
      }

      List<String> columnasEsperadas = formato.getFormatoEsperado();
      if (!csvParser.getHeaderNames().equals(columnasEsperadas)) {
        throw new Exception(
            "Las columnas del CSV no tienen el formato esperado.\nSe esperaba: "
                + columnasEsperadas);
      }

      List<Hecho> hechos = formato.importarHechos(csvParser, path);
      return hechos;

    } catch (Exception e) {
      throw new RuntimeException("Error al leer CSV: " + e.getMessage());
    }
  }

  private CSVParser crearParser(String path) throws IOException {
    Reader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
    return new CSVParser(
        reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
  }
}
