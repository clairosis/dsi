package ar.edu.utn.frba.dds.fuenteDinamica;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.GestorHechosSubidos;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api.FuenteDinamicaRepository;
import ar.edu.utn.frba.dds.hechos.Hecho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuenteDinamicaRepositoryTest {

  private FuenteDinamicaRepository repositorio;

  @BeforeEach
  void setUp() {
    repositorio = new FuenteDinamicaRepository();
    GestorHechosSubidos.limpiarLista();
  }

  @Test
  void guardarHecho() {
    assertEquals(0, GestorHechosSubidos.getInstancia().getHechosSubidos().size());

    Hecho hecho = new Hecho.Builder().titulo("Test Hecho").build();
    repositorio.guardar(hecho);

    assertEquals(1, GestorHechosSubidos.getInstancia().getHechosSubidos().size());
  }

  @Test
  void buscarHecho() {
    Hecho hecho = new Hecho.Builder().titulo("Test Hecho").build();
    repositorio.guardar(hecho);

    Hecho recuperado = repositorio.buscarPorId(0L);
    assertEquals("Test Hecho", recuperado.getTitulo());
  }

  @Test
  void eliminarHecho() {
    repositorio.guardar(new Hecho.Builder().titulo("Titulo").build());
    repositorio.guardar(new Hecho.Builder().titulo("Titulo").build());
    repositorio.guardar(new Hecho.Builder().titulo("Titulo").build());

    assertEquals(3, GestorHechosSubidos.getInstancia().getHechosSubidos().size());

    repositorio.eliminar(0L);

    assertEquals(2, GestorHechosSubidos.getInstancia().getHechosSubidos().size());
  }
}
