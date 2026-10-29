package com.clinicavet.service;

import com.clinicavet.domain.TipoServicio;
import java.util.List;
import java.util.Optional;

public interface TipoServicioService {

    List<TipoServicio> listarTodos();

    Optional<TipoServicio> buscarPorId(Integer id);

    TipoServicio guardar(TipoServicio datos);

    /** @throws IllegalStateException si el tipo todavía tiene servicios asociados. */
    void eliminar(Integer id);
}
