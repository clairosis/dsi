package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.HechosEliminados.GestorEliminadosFuenteEstatica;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.hechos.origen.Dataset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class IncendiosForestalesCsv extends FormatoCsv {
  long contador = 0;

  private static final Map<Integer, String> provincias =
      Map.ofEntries(
          Map.entry(1, "Álava"),
          Map.entry(2, "Albacete"),
          Map.entry(3, "Alicante"),
          Map.entry(4, "Almería"),
          Map.entry(5, "Ávila"),
          Map.entry(6, "Badajoz"),
          Map.entry(7, "Islas Baleares"),
          Map.entry(8, "Barcelona"),
          Map.entry(9, "Burgos"),
          Map.entry(10, "Cáceres"),
          Map.entry(11, "Cádiz"),
          Map.entry(12, "Castellón"),
          Map.entry(13, "Ciudad Real"),
          Map.entry(14, "Córdoba"),
          Map.entry(15, "A Coruña"),
          Map.entry(16, "Cuenca"),
          Map.entry(17, "Girona"),
          Map.entry(18, "Granada"),
          Map.entry(19, "Guadalajara"),
          Map.entry(20, "Guipúzcoa"),
          Map.entry(21, "Huelva"),
          Map.entry(22, "Huesca"),
          Map.entry(23, "Jaén"),
          Map.entry(24, "León"),
          Map.entry(25, "Lleida"),
          Map.entry(26, "La Rioja"),
          Map.entry(27, "Lugo"),
          Map.entry(28, "Madrid"),
          Map.entry(29, "Málaga"),
          Map.entry(30, "Murcia"),
          Map.entry(31, "Navarra"),
          Map.entry(32, "Ourense"),
          Map.entry(33, "Asturias"),
          Map.entry(34, "Palencia"),
          Map.entry(35, "Las Palmas"),
          Map.entry(36, "Pontevedra"),
          Map.entry(37, "Salamanca"),
          Map.entry(38, "Santa Cruz de Tenerife"),
          Map.entry(39, "Cantabria"),
          Map.entry(40, "Segovia"),
          Map.entry(41, "Sevilla"),
          Map.entry(42, "Soria"),
          Map.entry(43, "Tarragona"),
          Map.entry(44, "Teruel"),
          Map.entry(45, "Toledo"),
          Map.entry(46, "Valencia"),
          Map.entry(47, "Valladolid"),
          Map.entry(48, "Vizcaya"),
          Map.entry(49, "Zamora"),
          Map.entry(50, "Zaragoza"),
          Map.entry(51, "Ceuta"),
          Map.entry(52, "Melilla"));

  public static String obtenerNombreProvincia(int idProvincia) {
    return provincias.getOrDefault(idProvincia, "Provincia desconocida");
  }

  public IncendiosForestalesCsv() {
    this.campos =
        List.of(
            "id",
            "superficie",
            "fecha",
            "lat",
            "lng",
            "latlng_explicit",
            "idcomunidad",
            "idprovincia",
            "idmunicipio",
            "municipio",
            "causa",
            "causa_supuesta",
            "causa_desc",
            "muertos",
            "heridos",
            "time_ctrl",
            "time_ext",
            "personal",
            "medios",
            "gastos",
            "perdidas");
  }

  @Override
  public List<Hecho> importarHechos(CSVParser csvParser, String path) {
    List<Hecho> hechos = new ArrayList<>();
    List<Long> hechosEliminados =
        GestorEliminadosFuenteEstatica.getInstancia().getHechosEliminados(path);
    Dataset origen = new Dataset(path);

    for (CSVRecord record : csvParser) {
      if (hechosEliminados.contains(contador)) continue;

      Hecho hecho = construirHecho(record, origen, contador);

      if (!hechos.stream().map(Hecho::getTitulo).toList().contains(hecho.getTitulo())) {
        hechos.add(hecho);
      }

      contador++;
    }

    contador = 0;
    return new ArrayList<>(hechos);
  }

  private Hecho construirHecho(CSVRecord record, Dataset origen, long Contador) {
    String provinciaNombre =
        obtenerNombreProvincia(parsearEntero(record.get("idprovincia"), "idprovincia"));
    String superficie = record.get("superficie").isBlank() ? "?" : record.get("superficie").trim();

    String titulo = "Incendio de " + superficie + " hectáreas en " + provinciaNombre;

    String descripcion =
        "Incendio ocurrido en la provincia de "
            + provinciaNombre
            + " con una superficie afectada de "
            + superficie
            + " hectáreas.";
    String categoria = "Incendio Forestal";

    float latitud = record.get("lat").isBlank() ? 0f : parsearFloat(record.get("lat"), "lat");
    float longitud = record.get("lng").isBlank() ? 0f : parsearFloat(record.get("lng"), "lng");

    LocalDate fecha =
        record.get("fecha").isBlank() ? LocalDate.now() : parsearFecha(record.get("fecha"));

    Hecho.Builder builder = new Hecho.Builder();

    return builder
        .id(contador)
        .titulo(titulo)
        .descripcion(descripcion)
        .categoria(categoria)
        .latitud(latitud)
        .longitud(longitud)
        .fechaHecho(fecha)
        .build();
  }

  private String campoOpcional(CSVRecord record, String campo) {
    try {
      String val = record.get(campo).trim();
      return val.isEmpty() ? "N/D" : val;
    } catch (Exception e) {
      return "N/D";
    }
  }

  private float parsearFloatOpcional(String valor, float defecto) {
    try {
      return Float.parseFloat(valor.trim());
    } catch (Exception e) {
      return defecto;
    }
  }

  private int parsearIntOpcional(String valor, int defecto) {
    try {
      return Integer.parseInt(valor.trim());
    } catch (Exception e) {
      return defecto;
    }
  }

  private LocalDate parsearFechaOpcional(String valor, LocalDate defecto) {
    try {
      return LocalDate.parse(valor.trim());
    } catch (Exception e) {
      return defecto;
    }
  }
}
