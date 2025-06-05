package ar.edu.utn.frba.dds.fuenteEstatica;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api.FuenteEstaticaController;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api.FuenteEstaticaService;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.DefaultCsv;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.FormatoCsv;
import ar.edu.utn.frba.dds.hechos.HechoDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FuenteEstaticaController.class)
public class FuenteEstaticaControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private FuenteEstaticaService servicio;

  private final String archivo = "tests/hechosEliminadosTest.csv";
  private final FormatoCsv formatoCsv = new DefaultCsv();

  @Test
  void eliminarHecho() throws Exception {
    HechoDto dto = new HechoDto();
    dto.setTitulo("Hecho1");

    when(servicio.obtenerHechoPorId(1L, formatoCsv, archivo)).thenReturn(dto);
    doNothing().when(servicio).eliminarHecho(1L, archivo);

    mockMvc
        .perform(delete("/api/fuenteEstatica/1?formato=default&archivo=" + archivo))
        .andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  void obtenerHechoPorId_exitoso() throws Exception {
    HechoDto dto = new HechoDto();
    dto.setTitulo("Hecho encontrado");

    when(servicio.obtenerHechoPorId(eq(1L), any(), eq(archivo))).thenReturn(dto);

    mockMvc
        .perform(get("/api/fuenteEstatica/1?formato=default&archivo=" + archivo))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Hecho encontrado"));
  }

  @Test
  void obtenerHechoPorId_falla() throws Exception {
    when(servicio.obtenerHechoPorId(eq(1L), any(), eq(archivo))).thenThrow(new RuntimeException());

    mockMvc
        .perform(get("/api/fuenteEstatica/1?formato=default&archivo=" + archivo))
        .andExpect(status().isForbidden());
  }

  @Test
  void getListarHechos() throws Exception {
    HechoDto dto1 = new HechoDto();
    dto1.setTitulo("Hecho1");
    HechoDto dto2 = new HechoDto();
    dto2.setTitulo("Hecho2");

    List<HechoDto> dtos = List.of(dto1, dto2);

    when(servicio.listarHechos(any(), eq("pirulito.csv"))).thenReturn(dtos);

    mockMvc
        .perform(get("/api/fuenteEstatica?formato=default&archivo=pirulito.csv"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].titulo").value("Hecho1"))
        .andExpect(jsonPath("$[1].titulo").value("Hecho2"));
  }
}
