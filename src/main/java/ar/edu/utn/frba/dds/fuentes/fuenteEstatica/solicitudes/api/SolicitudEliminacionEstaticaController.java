package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.solicitudes.api;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes/estaticas/eliminacion")
public class SolicitudEliminacionEstaticaController {
  private final SolicitudEliminacionEstaticaService servicio;

  // --- Constructor ---

  public SolicitudEliminacionEstaticaController(SolicitudEliminacionEstaticaService servicio) {
    this.servicio = servicio;
  }

  // --- Metodos ---

  @GetMapping
  public ResponseEntity<List<SolicitudEliminacionEstaticaDTO>> listarSolicitudes() {
    List<SolicitudEliminacionEstaticaDTO> solicitudes = servicio.listarSolicitudes();
    return ResponseEntity.ok(solicitudes);
  }

  @PostMapping
  public ResponseEntity<SolicitudEliminacionEstaticaDTO> crearSolicitud(
      @RequestBody SolicitudEliminacionEstaticaDTO dto) {
    SolicitudEliminacionEstaticaDTO creado = servicio.crearSolicitud(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
  }

  @PostMapping("/{id}")
  public ResponseEntity<Void> procesarSolicitud(
      @PathVariable Long id, @RequestParam String accion) {
    try {
      if (accion.equalsIgnoreCase("aceptar")) {
        servicio.procesarSolicitud(id, true);
      } else if (accion.equalsIgnoreCase("rechazar")) {
        servicio.procesarSolicitud(id, false);
      } else {
        return ResponseEntity.badRequest().build(); // Acción inválida
      }

      return ResponseEntity.ok().build();

    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
