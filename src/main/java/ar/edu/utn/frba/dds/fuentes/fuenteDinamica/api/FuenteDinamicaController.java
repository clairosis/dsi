package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api;

import ar.edu.utn.frba.dds.hechos.HechoDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fuenteDinamica")
public class FuenteDinamicaController {
  private final FuenteDinamicaService servicio;

  // --- Constructor ---

  public FuenteDinamicaController(FuenteDinamicaService servicio) {
    this.servicio = servicio;
  }

  // --- Metodos ---

  @PostMapping
  public ResponseEntity<HechoDto> crearHecho(@RequestBody HechoDto dto) {
    HechoDto creado = servicio.crearHecho(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
  }

  @PostMapping("/{id}")
  public ResponseEntity<HechoDto> modificarHecho(@PathVariable Long id, @RequestBody HechoDto dto) {
    try {
      HechoDto modificado = servicio.modificarHecho(id, dto);
      return ResponseEntity.ok(modificado);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<HechoDto> obtenerHechoPorId(@PathVariable Long id) {
    try {
      HechoDto hecho = servicio.obtenerHechoPorId(id);
      return ResponseEntity.ok(hecho);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HechoDto> eliminarHecho(@PathVariable Long id) {
    try {
      HechoDto eliminado = servicio.eliminarHecho(id);
      return ResponseEntity.ok(eliminado);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @GetMapping()
  public ResponseEntity<List<HechoDto>> listarHechos() {
    List<HechoDto> hechos = servicio.listarHechos();
    return ResponseEntity.ok(hechos);
  }

  // TODO: IMPLEMENTAR OBTENER HECHOS POR UN DETERMINADO CRITERIO
}
