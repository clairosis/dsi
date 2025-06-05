package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api;

import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.FormatoCsv;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.FormatoCsvFactory;
import ar.edu.utn.frba.dds.hechos.HechoDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "/api/fuenteEstatica",
    params = {"archivo"})
public class FuenteEstaticaController {
  private final FuenteEstaticaService servicio;

  // --- Constructor ---

  public FuenteEstaticaController(FuenteEstaticaService servicio) {
    this.servicio = servicio;
  }

  // --- Metodos ---

  @GetMapping("/{id}")
  public ResponseEntity<HechoDto> obtenerHechoPorId(
      @PathVariable Long id, @RequestParam String formato, @RequestParam String archivo) {
    try {
      FormatoCsv formatoCsv = FormatoCsvFactory.fromNombre(formato);
      HechoDto hecho = servicio.obtenerHechoPorId(id, formatoCsv, archivo);
      return ResponseEntity.ok(hecho);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HechoDto> eliminarHecho(
      @PathVariable Long id, @RequestParam String archivo) {
    try {
      servicio.eliminarHecho(id, archivo);
      return ResponseEntity.ok(null);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @GetMapping()
  public ResponseEntity<List<HechoDto>> listarHechos(
      @RequestParam String formato, @RequestParam String archivo) {
    FormatoCsv formatoCsv = FormatoCsvFactory.fromNombre(formato);
    List<HechoDto> hechos = servicio.listarHechos(formatoCsv, archivo);
    return ResponseEntity.ok(hechos);
  }

  // TODO: IMPLEMENTAR OBTENER HECHOS POR UN DETERMINADO CRITERIO

  @GetMapping("/categoria")
  public ResponseEntity<List<HechoDto>> obtenerHechoPorCategoria(@RequestParam String value) {
    List<HechoDto> hechos = servicio.filtrarCategoria(value);
    return ResponseEntity.ok(hechos);
  }

  @GetMapping("/longitud")
  public ResponseEntity<List<HechoDto>> obtenerHechoPorLongitud(@RequestParam Float value) {
    List<HechoDto> hechos = servicio.filtrarLongitud(value);
    return ResponseEntity.ok(hechos);
  }

  @GetMapping("/latitud")
  public ResponseEntity<List<HechoDto>> obtenerHechoPorLatitud(@RequestParam Float value) {
    List<HechoDto> hechos = servicio.filtrarLatitud(value);
    return ResponseEntity.ok(hechos);
  }

  @GetMapping("/fechaHecho")
  public ResponseEntity<List<HechoDto>> obtenerHechoPorFechaHecho(@RequestParam LocalDate value) {
    List<HechoDto> hechos = servicio.filtrarFechaHecho(value);
    return ResponseEntity.ok(hechos);
  }

  @GetMapping("/fechaCarga")
  public ResponseEntity<List<HechoDto>> obtenerHechoPorFechaCarga(@RequestParam LocalDate value) {
    List<HechoDto> hechos = servicio.filtrarFechaCarga(value);
    return ResponseEntity.ok(hechos);
  }
}
