package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static io.vavr.control.Either.left;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.UsuarioNoDisponibleExeption;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import io.vavr.control.Either;

import java.util.Optional;

public final class RepositorioUsuarios implements Repositorio<Usuario> {
  private static final RepositorioUsuarios instance = new RepositorioUsuarios();

  public static RepositorioUsuarios getInstance() {
    return instance;
  }

  private RepositorioUsuarios() {}

  public void agregarAdministrador(Usuario usuario) {
    if (existeAdministrador(usuario.getUsuario())) {
      throw new UsuarioNoDisponibleExeption("Nombre de usuario no disponible");
    }
    agregar(usuario);
  }

  public Either<String, Usuario> obtenerAdministrador(String usuario, String contrasena) {
    if (!existeAdministrador(usuario)) {
      return left("No existe el usuario: " + usuario);
    }
    Optional<Either<String, Usuario>> user = buscar("usuario", usuario, "contrasena", sha256Hex(contrasena)).map(Either::right);
    return user.orElseGet(() -> left("Usuaio o contraseña incorrecto"));
  }

  public boolean existeAdministrador(String usuario) {
    return buscar("usuario", usuario).isPresent();
  }

  @Override
  public Class<Usuario> clase() {
    return Usuario.class;
  }
}
