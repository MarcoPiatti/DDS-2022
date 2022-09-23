package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.UsuarioNoDisponibleExeption;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public final class Administradores implements WithGlobalEntityManager {
  private static final Administradores instance = new Administradores();

  public static Administradores getInstance() {
    return instance;
  }

  private Administradores() {
  }

  public void agregarAdministrador(Administrador administrador) {
    if (existeAdministrador(administrador.getUsuario())) {
      throw new UsuarioNoDisponibleExeption("Nombre de usuario no disponible");
    }
    entityManager().persist(administrador);
  }

  public Administrador obtenerAdministrador(String usuario, String contrasena) throws Throwable {
    if (!existeAdministrador(usuario)) {
      throw new UsuarioNoDisponibleExeption("No existe el usuario: " + usuario);
    }

    return entityManager().createQuery("SELECT administrador from Administrador administrador"
            + " where administrador.usuario = :u " + "and administrador.contrasena = :c", Administrador.class)
        .setParameter("u", usuario)
        .setParameter("c", sha256Hex(contrasena))
        .getResultList().stream()
        .findFirst()
        .orElseThrow(() ->
            new UsuarioNoDisponibleExeption("No se pudo validar que sea ese administrador"));
  }

  public boolean existeAdministrador(String usuario) {
    return !entityManager()
        .createQuery("SELECT administrador from Administrador administrador where administrador.usuario = ?1")
        .setParameter("1", usuario).getResultList().isEmpty();
  }

  public void limpiar() {
    entityManager().createQuery("from Administrador", Administrador.class)
        .getResultList()
        .forEach(it -> entityManager().remove(it));
  }
}
