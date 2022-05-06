package models.factory;

import models.Linea;
import models.TipoDeTransportePublico;

import static java.util.Arrays.asList;
import static models.factory.UbicacionFactory.alem;
import static models.factory.UbicacionFactory.medrano;

public class LineaFactory {
  public static Linea subteB() {
    return new Linea("Subte B", asList(medrano(), alem()), TipoDeTransportePublico.SUBTE);
  }
}