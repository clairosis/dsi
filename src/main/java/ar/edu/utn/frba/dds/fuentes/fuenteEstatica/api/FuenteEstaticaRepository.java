package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.HechosEliminados.GestorEliminadosFuenteEstatica;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.LectorCsv;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.FormatoCsv;
import ar.edu.utn.frba.dds.hechos.Hecho;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FuenteEstaticaRepository {
  private final LectorCsv lectorCsv;

  public FuenteEstaticaRepository(LectorCsv lectorCsv) {
    this.lectorCsv = lectorCsv;
  }

  public List<Hecho> leerHechos(FormatoCsv formato, String archivo) {
    return new ArrayList<Hecho>(lectorCsv.importarHechos(archivo, formato));
  }

  public Hecho buscarPorId(Long id, FormatoCsv formato, String archivo) {
    return new ArrayList<Hecho>(lectorCsv.importarHechos(archivo, formato))
        .stream().filter(h -> h.getId().equals(id)).toList().get(0);
  }

  public void eliminar(Long id, String archivo) {
    GestorEliminadosFuenteEstatica.eliminarHecho(id, archivo);
  }
}
