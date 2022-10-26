package ar.edu.utn.frba.dds.impactoambiental.models.chequeos;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ChequeoFallidoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Chequeador<T> {
  private List<Chequeo<T>> chequeos = new ArrayList<>();

  public Chequeador<T> agregarValidacion(Predicate<T> chequeo, String mensajeDeError) {
    chequeos.add(new Chequeo<T>() {
      @Override
      public boolean validar(T valor) {
        return chequeo.test(valor);
      }

      @Override
      public String getMensajeDeError() {
        return mensajeDeError;
      }
    });
    return this;
  }

  public Chequeador<T> agregarValidaciones(List<Chequeo<T>> chequeos) {
    this.chequeos.addAll(chequeos);
    return this;
  }

  public Try<T> validar(T valor) {
    List<String> errores = getErrores(valor);
    if (!errores.isEmpty()) {
      throw new ChequeoFallidoException(Try.fallido(errores));
    }
    return Try.exitoso(valor);
  }

  private List<String> getErrores(T valor) {
    return chequeos.stream()
        .map(chequeo -> chequeo.getError(valor))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

}
