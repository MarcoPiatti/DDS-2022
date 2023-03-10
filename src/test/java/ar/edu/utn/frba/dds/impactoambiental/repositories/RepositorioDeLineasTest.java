package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.impactoambiental.models.BaseTest;
import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.Linea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositorioDeLineasTest extends BaseTest implements PersistenceTest {
  RepositorioDeLineas repositorioDeLineas = RepositorioDeLineas.getInstance();

  @Test
  public void sePuedeObtenerUnaLineaPorId() {
    Linea unaLinea = crearLineaDeSubteVacia();
    repositorioDeLineas.agregar(unaLinea);
    assertEquals(unaLinea, repositorioDeLineas.obtenerPorID(unaLinea.getId()).orElse(null));
  }
  @Test
  public void sePuedeObtenerTodasLasLineas() {
    Linea unaLinea = crearLineaDeSubteVacia();
    Linea otraLinea = crearLineaDeSubteVacia();
    repositorioDeLineas.agregar(unaLinea);
    repositorioDeLineas.agregar(otraLinea);

    assertEquals(asList(unaLinea,otraLinea), repositorioDeLineas.obtenerTodos());
  }

  @BeforeEach
  private void cleanRepo() {
    repositorioDeLineas.limpiar();
    entityManager().flush();
    entityManager().clear();
  }
}
