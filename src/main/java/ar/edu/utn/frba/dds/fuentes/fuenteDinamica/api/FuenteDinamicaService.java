package ar.edu.utn.frba.dds.fuentes.fuenteDinamica.api;

import ar.edu.utn.frba.dds.fuentes.fuenteDinamica.GestorHechosSubidos;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.hechos.HechoDto;
import ar.edu.utn.frba.dds.hechos.origen.Contribuyente;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
public class FuenteDinamicaService {
  private final FuenteDinamicaRepository repositorio;

  public FuenteDinamicaService(FuenteDinamicaRepository repositorio) {
    this.repositorio = repositorio;
  }

  public HechoDto crearHecho(HechoDto dto) {
    Hecho hecho =
        repositorio.guardar(
            new Hecho.Builder()
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .categoria(dto.getCategoria())
                .latitud(dto.getLatitud())
                .longitud(dto.getLongitud())
                .fechaHecho(dto.getFechaHecho())
                .multimedia(dto.getMultimedia())
                // TODO: RECUPERAR EL NOMBRE DEL AUTOR DEL HECHO PARA PONER EN EL ORIGEN
                .origen(new Contribuyente("HARDCODE"))
                .build());
    return convertirAHechoDTO(hecho);
  }

  public HechoDto eliminarHecho(Long id) {
    Hecho eliminado = repositorio.buscarPorId(id);
    repositorio.eliminar(id);
    return convertirAHechoDTO(eliminado);
  }

  public HechoDto modificarHecho(Long id, HechoDto dto) {
    Hecho hecho = repositorio.buscarPorId(id);

    this.verificarSiEsModificable(hecho);

    actualizarCampo(dto.getTitulo(), hecho::setTitulo);
    actualizarCampo(dto.getDescripcion(), hecho::setDescripcion);
    actualizarCampo(dto.getCategoria(), hecho::setCategoria);
    actualizarCampo(dto.getLatitud(), hecho::setLatitud);
    actualizarCampo(dto.getLongitud(), hecho::setLongitud);
    actualizarCampo(dto.getFechaHecho(), hecho::setFechaHecho);
    actualizarCampo(dto.getMultimedia(), hecho::setMultimedia);

    return convertirAHechoDTO(hecho);
  }

  public HechoDto obtenerHechoPorId(Long id) {
    Hecho hecho = repositorio.buscarPorId(id);
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

  private <T> void actualizarCampo(T valor, Consumer<T> setter) {
    if (valor != null) {
      setter.accept(valor);
    }
  }

  public List<HechoDto> listarHechos() {
    return GestorHechosSubidos.getInstancia().getHechosSubidos().stream()
        .map(FuenteDinamicaService::convertirAHechoDTO)
        .toList();
  }

  // --- Verificaciones ---

  private void verificarSiEsModificable(Hecho hecho) {
    if (hecho.getFechaCarga().isBefore(LocalDate.now().minusWeeks(1))) {
      throw new RuntimeException("No se puede modificar después de una semana");
    }
  }
}
