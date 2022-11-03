package ar.edu.utn.frba.dds.impactoambiental.models.da;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LectorDeArchivos {
  private final List<String> lineas;

  public LectorDeArchivos(String path) {
    File archivo = new File(path);
    validarArchivo(archivo);

    this.lineas = archivoALineas(archivo);
  }

  public LectorDeArchivos(byte[] bytes) {
    this.lineas = Arrays.asList(new String(bytes).split("\n"));
  }

  private void validarArchivo(File f) {
    if (!f.exists()) {
      throw new IllegalArgumentException("El archivo no existe.");
    }
    if (!f.canRead()) {
      throw new IllegalArgumentException("No se puede leer el archivo.");
    }
    if (f.isDirectory()) {
      throw new IllegalArgumentException("La ruta especifiacada corresponde a un directorio.");
    }
  }

  private List<String> archivoALineas(File archivo) {
    List<String> nuevasLineas = new ArrayList<>();
    try {
      Scanner scannerArchivo = new Scanner(archivo);
      while (scannerArchivo.hasNextLine()) {
        nuevasLineas.add(scannerArchivo.nextLine());
      }

      scannerArchivo.close();
      return nuevasLineas;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("El archivo no existe.");
    }
  }

  public List<String> getLineas() {
    return lineas;
  }
}
