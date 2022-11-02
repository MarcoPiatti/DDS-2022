package ar.edu.utn.frba.dds.impactoambiental;

import static java.util.Arrays.asList;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.da.UnidadDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Unidad;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Parada;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.TipoDeTransporte;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.TipoDeDocumento;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.ClasificacionDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.TipoDeOrganizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.UsuarioMiembro;
import java.util.ArrayList;
import java.util.List;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class Bootstrap implements TransactionalOps, EntityManagerOps, WithGlobalEntityManager {

  public static void main(String[] args) {
    new Bootstrap().init();
  }

  public void init() {
    withTransaction(() -> {
      List<Trayecto> trayectos = new ArrayList<>();
      Parada parada = new Parada("Lo De Marco", new Distancia(10D, Unidad.KM), new Distancia(10D, Unidad.KM));
      Parada parada2 = new Parada("Lo De Uli", new Distancia(10D, Unidad.KM), new Distancia(10D, Unidad.KM));
      Linea linea = new Linea("Linea Functional", asList(parada, parada2), new MedioDeTransporte("Hola", 30D, new TipoDeConsumo("Consumo Faalopa", 30D, UnidadDeConsumo.KM), TipoDeTransporte.TRANSPORTE_PUBLICO));
      Miembro miembro = new Miembro("juan", "perroGato00", "42885123", TipoDeDocumento.DNI, trayectos);
      Vinculacion vinculacion = new Vinculacion(miembro);
      Sector sector = new Sector(asList(vinculacion));
      Organizacion organizacion = new Organizacion("PEPE SA", null, TipoDeOrganizacion.EMPRESA,
          ClasificacionDeOrganizacion.EMPRESA_PRIMARIA, asList(sector), null, null);
      UsuarioMiembro usuarioMiembro = new UsuarioMiembro("juan", "perroGato00", "juan", "juan", "42885123", TipoDeDocumento.DNI, asList(miembro));
      persist(miembro);
      persist(vinculacion);
      persist(sector);
      persist(organizacion);
      persist(usuarioMiembro);
      persist(parada);
      persist(parada2);
      persist(linea);
    });
  }
}
