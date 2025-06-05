package ar.edu.utn.frba.dds.fuenteDinamica.SolicitudesDeEliminacion;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.SolicitudesController;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.SolicitudesService;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.dtos.SolicitudEliminacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SolicitudesController.class)
class SolicitudEliminacionDinamicaControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private SolicitudesService servicio;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void listarSolicitudes_deberiaRetornar200YLista() throws Exception {
    SolicitudEliminacionDTO dto = new SolicitudEliminacionDTO();
    dto.setHecho(0L);
    dto.setJustificacion("Justificacion totalmente valida");

    when(servicio.listarSolicitudes()).thenReturn(List.of(dto));

    mockMvc
        .perform(get("/api/solicitudes/dinamicas/eliminacion"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].hecho").value(0L));
  }

  @Test
  void crearSolicitud_deberiaRetornar201YObjetoCreado() throws Exception {
    SolicitudEliminacionDTO solicitud = new SolicitudEliminacionDTO();
    solicitud.setHecho(0L);

    when(servicio.crearSolicitud(any())).thenReturn(solicitud);

    mockMvc
        .perform(
            post("/api/solicitudes/dinamicas/eliminacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitud)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.hecho").value(0L));
  }

  @Test
  void procesarSolicitud_aceptar_deberiaRetornar200() throws Exception {
    mockMvc
        .perform(post("/api/solicitudes/dinamicas/eliminacion/1").param("accion", "aceptar"))
        .andExpect(status().isOk());

    verify(servicio).procesarSolicitud(1L, true);
  }

  @Test
  void procesarSolicitud_rechazar_deberiaRetornar200() throws Exception {
    mockMvc
        .perform(post("/api/solicitudes/dinamicas/eliminacion/1").param("accion", "rechazar"))
        .andExpect(status().isOk());

    verify(servicio).procesarSolicitud(1L, false);
  }

  @Test
  void procesarSolicitud_accionInvalida_deberiaRetornar400() throws Exception {
    mockMvc
        .perform(post("/api/solicitudes/dinamicas/eliminacion/1").param("accion", "invalido"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void procesarSolicitud_noExiste_deberiaRetornar404() throws Exception {
    doThrow(new NoSuchElementException()).when(servicio).procesarSolicitud(1L, true);

    mockMvc
        .perform(post("/api/solicitudes/dinamicas/eliminacion/1").param("accion", "aceptar"))
        .andExpect(status().isNotFound());
  }
}
