package models.organizacion;

public enum EstadoVinculo {
  PENDIENTE {
    @Override
    public EstadoVinculo aceptar(Vinculacion vinculacion) {
      return EstadoVinculo.ACEPTADO;
    }

    @Override
    public EstadoVinculo rechazar(Vinculacion vinculacion) {
      return EstadoVinculo.RECHAZADO;
    }
  },
  ACEPTADO,
  RECHAZADO;

  public EstadoVinculo aceptar(Vinculacion vinculacion) {
    return this;
  }

  public EstadoVinculo rechazar(Vinculacion vinculacion) {
    return this;
  }
}