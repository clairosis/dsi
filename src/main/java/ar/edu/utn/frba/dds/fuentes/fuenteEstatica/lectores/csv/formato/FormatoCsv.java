package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato;

import ar.edu.utn.frba.dds.hechos.Hecho;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public abstract class FormatoCsv {
  protected List<String> campos;

  public List<String> getFormatoEsperado() {
    if (campos == null) {
      throw new IllegalStateException("Formato CSV no definido (campos == null)");
    }
    return new ArrayList<>(campos);
  }

  public abstract List<Hecho> importarHechos(CSVParser csvParser, String path);

  protected String obtenerCampoObligatorio(CSVRecord record, String nombreCampo) {
    String valor = record.get(nombreCampo).trim();
    if (valor.isEmpty()) {
      throw new IllegalArgumentException("El campo '" + nombreCampo + "' está vacío");
    }
    return valor;
  }

  protected float parsearFloat(String valor, String nombreCampo) {
    try {
      return Float.parseFloat(valor);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(nombreCampo + " inválida");
    }
  }

  protected LocalDate parsearFecha(String valor) {
    try {
      return LocalDate.parse(valor);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Fecha inválida");
    }
  }

  protected int parsearEntero(String valor, String nombreCampo) {
    try {
      return Integer.parseInt(valor.trim());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(nombreCampo + " inválido");
    }
  }
}
