package ar.edu.utn.frba.dds.impactoambiental.models.usuario;

import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioDto;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("8caracteres")
public class Validar8Caracteres extends ValidacionDeUsuario {

  @Override
  public boolean test(UsuarioDto dto) {
    return dto.getPassword().length() >= 8;
  }

  public String getMensajeDeError() {
    return "La contraseña debe tener al menos 8 caracteres.";
  }
}
