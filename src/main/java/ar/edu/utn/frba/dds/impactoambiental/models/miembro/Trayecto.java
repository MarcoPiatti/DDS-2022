package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class Trayecto extends EntidadPersistente {
  @OneToMany
  @JoinColumn(name = "trayecto_id")
  private final List<Tramo> tramos;
  private final LocalDate fecha;

  private final UUID codigoInvite;

  public Trayecto(LocalDate fecha, List<Tramo> tramos) {
    this.tramos = tramos;
    this.fecha = fecha;
    this.codigoInvite = UUID.randomUUID();
  }

  public List<Tramo> getTramos() {
    return tramos;
  }

  public boolean estaEnPeriodo(Periodo periodo) {
    return periodo.contieneFecha(fecha);
  }

  public Distancia getDistancia() {
    return tramos.stream()
        .map(Tramo::getDistancia)
        .reduce(Distancia.CERO, Distancia::sumar);
  }

  public boolean esCompartible() {
    return tramos.stream().allMatch(Tramo::esCompartible);
  }

  public Double carbonoEquivalente() {
    return tramos.stream().mapToDouble(Tramo::carbonoEquivalente).sum();
  }

  public Double carbonoEquivalenteSegunConsumo(TipoDeConsumo tipoDeConsumo) {
    return tramos.stream()
        .filter(tramo -> tramo.tieneTipoDeConsumo(tipoDeConsumo))
        .mapToDouble(Tramo::carbonoEquivalente)
        .sum();
  }
}
