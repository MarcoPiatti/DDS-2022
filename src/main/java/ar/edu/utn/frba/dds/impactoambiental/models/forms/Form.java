package ar.edu.utn.frba.dds.impactoambiental.models.forms;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Arrays.asList;

import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Validador;
import io.vavr.control.Either;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import spark.Request;

public abstract class Form {
  public abstract Optional<String> getParam(String param);

  public List<Either<List<String>,String>> validateAndGetAllParams(Map<String, Validador<String>> parametersToGet) {
   return parametersToGet
       .entrySet()
       .stream()
       .map(x -> getParamOrError(x.getKey(), x.getValue()))
       .collect(Collectors.toList());
  }

  public Either<List<String>, String> getParamOrError(String param, Validador<String> validador) {
    Optional<Either<List<String>, String>> o = getParam(param).map(validador::validar);
    return o.orElseGet(() -> left(asList("The parameter " + param + " is not present in the form")));
  }

  public Either<String, String> getParamOrDefault(String param, String defaultValue) {
    Optional<Either<String, String>> o = getParam(param).map(Either::right);
    return o.orElseGet(() -> right(defaultValue));
  }

  public abstract Optional<byte[]> getFile(String param);

  static Form of(Request req) {
    return req.contentType().startsWith("multipart/form-data")
        ? new MultipartForm(req)
        : new UrlEncodedForm(req);
  }
}
