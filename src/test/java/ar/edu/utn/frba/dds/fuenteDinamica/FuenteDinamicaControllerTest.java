package ar.edu.utn.frba.dds.fuenteDinamica;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api.FuenteDinamicaController;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api.FuenteDinamicaService;
import ar.edu.utn.frba.dds.hechos.HechoDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FuenteDinamicaController.class)
public class FuenteDinamicaControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private FuenteDinamicaService servicio;

  @Test
  void postCrearHecho() throws Exception {
    HechoDto dto = new HechoDto();
    dto.setTitulo("Título");

    when(servicio.crearHecho(any())).thenReturn(dto);

    mockMvc
        .perform(
            post("/api/fuenteDinamica")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Título\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.titulo").value("Título"));
  }

  @Test
  void eliminarHecho() throws Exception {
    HechoDto dto1 = new HechoDto();
    dto1.setTitulo("Hecho1");

    HechoDto dto2 = new HechoDto();
    dto2.setTitulo("Hecho2");

    List<HechoDto> hechosIniciales = List.of(dto1, dto2);
    List<HechoDto> hechosLuegoDeEliminar = List.of(dto2);

    when(servicio.listarHechos()).thenReturn(hechosIniciales);

    mockMvc
        .perform(get("/api/fuenteDinamica"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));

    when(servicio.eliminarHecho(1L)).thenReturn(dto1);

    mockMvc
        .perform(delete("/api/fuenteDinamica/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Hecho1"));

    when(servicio.listarHechos()).thenReturn(hechosLuegoDeEliminar);

    mockMvc
        .perform(get("/api/fuenteDinamica"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].titulo").value("Hecho2"));
  }

  @Test
  void obtenerHechoPorId_exitoso() throws Exception {
    HechoDto dto = new HechoDto();
    dto.setTitulo("Hecho encontrado");

    when(servicio.obtenerHechoPorId(1L)).thenReturn(dto);

    mockMvc
        .perform(get("/api/fuenteDinamica/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Hecho encontrado"));
  }

  @Test
  void obtenerHechoPorId_falla() throws Exception {
    when(servicio.obtenerHechoPorId(1L)).thenThrow(new RuntimeException());

    mockMvc.perform(get("/api/fuenteDinamica/1")).andExpect(status().isForbidden());
  }

  @Test
  void modificarHecho_exitoso() throws Exception {
    HechoDto dto = new HechoDto();
    dto.setTitulo("Hecho modificado");

    when(servicio.modificarHecho(eq(1L), any(HechoDto.class))).thenReturn(dto);

    mockMvc
        .perform(
            post("/api/fuenteDinamica/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Hecho modificado\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Hecho modificado"));
  }

  @Test
  void modificarHecho_falla() throws Exception {
    when(servicio.modificarHecho(eq(1L), any(HechoDto.class))).thenThrow(new RuntimeException());

    mockMvc
        .perform(
            post("/api/fuenteDinamica/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Hecho modificado\"}"))
        .andExpect(status().isForbidden());
  }

  @Test
  void getListarHechos() throws Exception {
    HechoDto dto1 = new HechoDto();
    dto1.setTitulo("Hecho1");
    HechoDto dto2 = new HechoDto();
    dto2.setTitulo("Hecho2");

    List<HechoDto> dtos = new ArrayList<>();
    dtos.add(dto1);
    dtos.add(dto2);

    when(servicio.listarHechos()).thenReturn(dtos);

    mockMvc
        .perform(get("/api/fuenteDinamica"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].titulo").value("Hecho1"))
        .andExpect(jsonPath("$[1].titulo").value("Hecho2"));
  }
}
