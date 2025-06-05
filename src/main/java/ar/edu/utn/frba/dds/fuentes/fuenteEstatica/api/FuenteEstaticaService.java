package ar.edu.utn.frba.dds.fuentes.fuenteEstatica.api;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.GestorHechosSubidos;
import ar.edu.utn.frba.dds.fuentes.fuenteEstatica.lectores.csv.formato.FormatoCsv;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.hechos.HechoDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FuenteEstaticaService {
  private final FuenteEstaticaRepository repositorio;

  public FuenteEstaticaService(FuenteEstaticaRepository repositorio) {
    this.repositorio = repositorio;
  }

  public void eliminarHecho(Long id, String archivo) {
    repositorio.eliminar(id, archivo);
  }

  public HechoDto obtenerHechoPorId(Long id, FormatoCsv formato, String archivo) {
    Hecho hecho = repositorio.buscarPorId(id, formato, archivo);
    return convertirAHechoDTO(hecho);
  }

  private static HechoDto convertirAHechoDTO(Hecho hecho) {
    HechoDto dto = new HechoDto();
    dto.setId(hecho.getId());
    dto.setTitulo(hecho.getTitulo());
    dto.setDescripcion(hecho.getDescripcion());
    dto.setCategoria(hecho.getCategoria());
    dto.setLatitud(hecho.getLatitud());
    dto.setLongitud(hecho.getLongitud());
    dto.setFechaHecho(hecho.getFechaHecho());
    dto.setFechaCarga(hecho.getFechaCarga());
    dto.setOrigen(hecho.getOrigen());
    dto.setMultimedia(hecho.getMultimedia());
    return dto;
  }

  public List<HechoDto> listarHechos(FormatoCsv formato, String archivo) {
    return repositorio.leerHechos(formato, archivo).stream()
        .map(FuenteEstaticaService::convertirAHechoDTO)
        .toList();
  }

  public List<HechoDto> listarHechos() {
    return GestorHechosSubidos.getInstancia().getHechosSubidos().stream()
        .map(FuenteEstaticaService::convertirAHechoDTO)
        .toList();
  }

  public List<HechoDto> filtrarCategoria(String categoria) {
    return this.listarHechos().stream().filter(h -> h.getCategoria().equals(categoria)).toList();
  }

  public List<HechoDto> filtrarLatitud(Float latitud) {
    return this.listarHechos().stream().filter(h -> h.getLatitud().equals(latitud)).toList();
  }

  public List<HechoDto> filtrarLongitud(Float longitud) {
    return this.listarHechos().stream().filter(h -> h.getLongitud().equals(longitud)).toList();
  }

  public List<HechoDto> filtrarFechaHecho(LocalDate fechaHecho) {
    return this.listarHechos().stream().filter(h -> h.getFechaHecho().equals(fechaHecho)).toList();
  }

  public List<HechoDto> filtrarFechaCarga(LocalDate fechaCarga) {
    return this.listarHechos().stream().filter(h -> h.getFechaCarga().equals(fechaCarga)).toList();
  }
}
