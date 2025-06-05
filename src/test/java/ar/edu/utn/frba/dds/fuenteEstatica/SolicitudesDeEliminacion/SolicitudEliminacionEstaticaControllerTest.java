package ar.edu.utn.frba.dds.fuenteEstatica.SolicitudesDeEliminacion;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api.SolicitudEliminacionEstaticaController;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api.SolicitudEliminacionEstaticaDTO;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api.SolicitudEliminacionEstaticaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SolicitudEliminacionEstaticaController.class)
public class SolicitudEliminacionEstaticaControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private SolicitudEliminacionEstaticaService servicio;

  @Test
  void testListarSolicitudes() throws Exception {
    SolicitudEliminacionEstaticaDTO dto = new SolicitudEliminacionEstaticaDTO();
    // Setea campos del DTO si es necesario
    when(servicio.listarSolicitudes()).thenReturn(List.of(dto));

    mockMvc
        .perform(get("/api/solicitudes/estaticas/eliminacion"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void testCrearSolicitud() throws Exception {
    SolicitudEliminacionEstaticaDTO dto = new SolicitudEliminacionEstaticaDTO();
    dto.setJustificacion("Justificación válida");
    // Setea más campos si hace falta

    when(servicio.crearSolicitud(any())).thenReturn(dto);

    mockMvc
        .perform(
            post("/api/solicitudes/estaticas/eliminacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.justificacion").value("Justificación válida"));
  }

  @Test
  void testProcesarSolicitudAceptar() throws Exception {
    doNothing().when(servicio).procesarSolicitud(1L, true);

    mockMvc
        .perform(post("/api/solicitudes/estaticas/eliminacion/1").param("accion", "aceptar"))
        .andExpect(status().isOk());
  }

  @Test
  void testProcesarSolicitudRechazar() throws Exception {
    doNothing().when(servicio).procesarSolicitud(2L, false);

    mockMvc
        .perform(post("/api/solicitudes/estaticas/eliminacion/2").param("accion", "rechazar"))
        .andExpect(status().isOk());
  }

  @Test
  void testProcesarSolicitudAccionInvalida() throws Exception {
    mockMvc
        .perform(post("/api/solicitudes/estaticas/eliminacion/3").param("accion", "otra"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testProcesarSolicitudNoEncontrada() throws Exception {
    doThrow(NoSuchElementException.class).when(servicio).procesarSolicitud(99L, true);

    mockMvc
        .perform(post("/api/solicitudes/estaticas/eliminacion/99").param("accion", "aceptar"))
        .andExpect(status().isNotFound());
  }
}
