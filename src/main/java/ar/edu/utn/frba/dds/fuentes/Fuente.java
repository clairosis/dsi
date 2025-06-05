package ar.edu.utn.frba.dds.fuentes;

import ar.edu.utn.frba.dds.hechos.Hecho;
import java.util.List;

public interface Fuente {
  List<Hecho> leerHechos();
}
