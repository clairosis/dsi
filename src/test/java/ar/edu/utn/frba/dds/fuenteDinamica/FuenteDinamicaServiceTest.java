package ar.edu.utn.frba.dds.fuenteDinamica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api.FuenteDinamicaRepository;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api.FuenteDinamicaService;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.hechos.HechoDto;
import java.lang.reflect.Field;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FuenteDinamicaServiceTest {

  @Mock private FuenteDinamicaRepository repositorio;
  @InjectMocks private FuenteDinamicaService servicio;

  @Test
  void crearHecho_deberiaGuardarYRetornarDTO() {
    HechoDto dto = new HechoDto();
    dto.setTitulo("Título");

    Hecho hecho = new Hecho.Builder().titulo("Título").build();

    doAnswer(
            invocation -> {
              Hecho arg = invocation.getArgument(0);
              assertEquals("Título", arg.getTitulo());
              return arg;
            })
        .when(repositorio)
        .guardar(any(Hecho.class));

    HechoDto resultado = servicio.crearHecho(dto);
    assertEquals("Título", resultado.getTitulo());
  }

  @Test
  void obtenerHechoPorId_deberiaRetornarDTO() {
    Hecho hecho = new Hecho.Builder().titulo("Titulo").build();
    when(repositorio.buscarPorId(1L)).thenReturn(hecho);

    HechoDto dto = servicio.obtenerHechoPorId(1L);
    assertEquals("Titulo", dto.getTitulo());
  }

  @Test
  void eliminarHecho_deberiaEliminarYRetornarDTO() {
    Hecho hecho = new Hecho.Builder().titulo("Titulo").build();
    when(repositorio.buscarPorId(1L)).thenReturn(hecho);

    HechoDto eliminado = servicio.eliminarHecho(1L);
    verify(repositorio).eliminar(1L);
    assertEquals("Titulo", eliminado.getTitulo());
  }

  @Test
  void modificarHecho_pasadaUnaSemana_deberiaFallar()
      throws IllegalAccessException, NoSuchFieldException {
    Hecho hecho = new Hecho.Builder().titulo("Viejo hecho").build();

    // 👉 Forzamos la fecha con reflection
    Field field = Hecho.class.getDeclaredField("fechaCarga");
    field.setAccessible(true);
    field.set(hecho, LocalDate.now().minusWeeks(2));

    when(repositorio.buscarPorId(1L)).thenReturn(hecho);

    assertThrows(
        RuntimeException.class,
        () -> {
          servicio.modificarHecho(1L, new HechoDto());
        });
  }
}
