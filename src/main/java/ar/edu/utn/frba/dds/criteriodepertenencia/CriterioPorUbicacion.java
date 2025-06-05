package ar.edu.utn.frba.dds.criteriodepertenencia;

import ar.edu.utn.frba.dds.hechos.Hecho;

public class CriterioPorUbicacion implements CriterioDePertenencia {
  private float latMin;
  private float latMax;
  private float lonMin;
  private float lonMax;

  // --- Constructores ---

  public CriterioPorUbicacion(float lat, float lon) {
    this.latMin = lat;
    this.latMax = lat;
    this.lonMin = lon;
    this.lonMax = lon;
  }

  public CriterioPorUbicacion(float latMin, float latMax, float lonMin, float lonMax) {
    this.latMin = latMin;
    this.latMax = latMax;
    this.lonMin = lonMin;
    this.lonMax = lonMax;
  }

  @Override
  public boolean cumple(Hecho hecho) {
    float lat = hecho.getLatitud();
    float lon = hecho.getLongitud();
    return lat >= latMin && lat <= latMax && lon >= lonMin && lon <= lonMax;
  }
}
