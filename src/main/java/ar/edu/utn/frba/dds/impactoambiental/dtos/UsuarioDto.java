package ar.edu.utn.frba.dds.impactoambiental.dtos;

import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UsuarioDto {
  private String username;
  private String password;

  public UsuarioDto(String usuario, String password) {
    this.username = usuario;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  // TODO: Ver cómo crear distintos tipos de usuarios
  public Usuario toEntity() {
    throw new NotImplementedException();
  }
}
