package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import java.util.Optional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("8caracteres")
public class Validar8Caracteres extends Validacion {
  @Override
  public Optional<String> validar(String usuario, String contrasena) {
    Optional<String> error = Optional.empty();
    if (contrasena.length() < 8) {
      error = Optional.of("La contraseña debe tener al menos 8 caracteres.");
    }
    return error;
  }
}
