package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class FormatoCsvFactory {
  private static final Map<String, Supplier<FormatoCsv>> formatos = new HashMap<>();

  static {
    formatos.put("default", DefaultCsv::new);
    formatos.put("incendiosforestales", IncendiosForestalesCsv::new);
  }

  public static FormatoCsv fromNombre(String nombre) {
    Supplier<FormatoCsv> proveedor = formatos.get(nombre.toLowerCase());
    if (proveedor == null) {
      throw new IllegalArgumentException("FormatoCsv desconocido: " + nombre);
    }
    return proveedor.get();
  }
}
