package ar.edu.utn.frba.dds.impactoambiental.controllers;
import ar.edu.utn.frba.dds.impactoambiental.dtos.UsuarioNormalDto;
import ar.edu.utn.frba.dds.impactoambiental.models.forms.UrlEncodedForm;
import ar.edu.utn.frba.dds.impactoambiental.models.usuario.Usuario;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validacion;
import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import ar.edu.utn.frba.dds.impactoambiental.utils.EitherUtil;
import io.vavr.control.Either;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class HomeController implements EitherUtil {

  public ModelAndView recomendaciones(Request req, Response resp) {
    System.out.println(req.queryParams("errors"));
    return new ModelAndView(null, "recomendaciones.html.hbs");
  }

  public ModelAndView login(Request req, Response resp) {
    if (req.cookie("SESSIONID") != null) {
      resp.redirect("/");
      return null;
    }
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("errors",req.queryParams("errors"));
    return new ModelAndView(modelo, "login.html.hbs");
  }

  public ModelAndView home(Request req, Response resp) {
    resp.redirect("/recomendaciones");
    return null;
  }

  public ModelAndView validarLogin(Request req, Response resp) {
    Either<List<String>, Usuario> usuarioLogueado = new  UsuarioNormalDto(
        new UrlEncodedForm(req)
    ).convertToModel();
    usuarioLogueado.map(usuario -> {
      resp.redirect("miembros/" + usuario.getId() + "/vinculaciones");
      return usuario;
    })
        .mapLeft(errores -> {
          resp.redirect("/login?errors=" +  String.join(", ", errores));
          return errores;
        });/*hago la magic de errores redircciono al form con un cartel con los errores*/
    return null;
  }

  public ModelAndView cerrarSesion(Request request, Response response) {
    response.removeCookie("SESSIONID");
    response.redirect("/");
    return null;
    // No se que tan bien esta esto la verdad
  }
}
