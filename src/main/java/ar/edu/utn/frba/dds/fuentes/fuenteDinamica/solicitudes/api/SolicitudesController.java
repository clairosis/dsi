package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.dtos.SolicitudEliminacionDTO;
import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.solicitudes.api.dtos.SolicitudModificacionDTO;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes/eliminacion")
public class SolicitudesController {
  private final SolicitudesService servicio;

  // --- Constructor ---

  public SolicitudesController(SolicitudesService servicio) {
    this.servicio = servicio;
  }

  // --- Metodos ---

  @PostMapping
  public ResponseEntity<SolicitudEliminacionDTO> crearSolicitudEliminacion(
      @RequestBody SolicitudEliminacionDTO dto) {
    SolicitudEliminacionDTO creado = servicio.crearSolicitudEliminacion(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
  }

  @PostMapping
  public ResponseEntity<SolicitudModificacionDTO> crearSolicitudModificacion(
      @RequestBody SolicitudModificacionDTO dto) {
    SolicitudModificacionDTO creado = servicio.crearSolicitudModificacion(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
  }

  @PostMapping("/eliminacion/{id}")
  public ResponseEntity<Void> procesarSolicitudEliminacion(
      @PathVariable Long id, @RequestParam String accion) {
    try {
      if (accion.equalsIgnoreCase("aceptar")) {
        servicio.procesarSolicitudEliminacion(id, true);
      } else if (accion.equalsIgnoreCase("rechazar")) {
        servicio.procesarSolicitudEliminacion(id, false);
      } else {
        return ResponseEntity.badRequest().build(); // Acción inválida
      }

      return ResponseEntity.ok().build();

    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/modificar/{id}")
  public ResponseEntity<Void> procesarSolicitudModificacion(
      @PathVariable Long id, @RequestBody String accion) {
    try {
      if (accion.equalsIgnoreCase("aceptar")) {
        servicio.procesarSolicitudModificacion(id, true);
      } else if (accion.equalsIgnoreCase("rechazar")) {
        servicio.procesarSolicitudModificacion(id, false);
      } else {
        return ResponseEntity.badRequest().build();
      }

      return ResponseEntity.ok().build();

    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
