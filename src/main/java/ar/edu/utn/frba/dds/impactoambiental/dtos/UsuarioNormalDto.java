package ar.edu.utn.frba.dds.impactoambiental.dtos;

import ar.edu.utn.frba.dds.impactoambiental.ServiceLocator;
import ar.edu.utn.frba.dds.impactoambiental.models.forms.UrlEncodedForm;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import ar.edu.utn.frba.dds.impactoambiental.repositories.RepositorioUsuarios;
import io.vavr.control.Either;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class UsuarioNormalDto {
  private Optional<String> userName;
  private Optional<String> password;
  private Validador<UsuarioNormalDto> validador = new Validador<>();

  public UsuarioNormalDto(UrlEncodedForm form) {
    this.userName = form.getParam("usernme");
    this.password = form.getParam("password");
    validador.agregarValidacion((user) -> user.userName.isPresent(), "El username no debe ser nulo");
    validador.agregarValidacion(user -> user.password.isPresent(), "lA Contraseña no puede ser nula");
  }
  public Either<List<String>, Usuario> convertToModel() {
    Either<List<String>, UsuarioNormalDto> map = validador.validar(this);
    return map.flatMap(x -> RepositorioUsuarios.getInstance()
        .obtenerAdministrador(x.userName.get(), x.password.get()).mapLeft(y -> asList(y)));
  }
}
