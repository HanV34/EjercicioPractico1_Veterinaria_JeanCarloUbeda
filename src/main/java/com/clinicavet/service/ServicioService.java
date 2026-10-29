package com.clinicavet.service;

import com.clinicavet.domain.Servicio;
import java.util.List;
import java.util.Optional;

public interface ServicioService {

    List<Servicio> listarTodos();

    Optional<Servicio> buscarPorId(Integer id);

    Servicio guardar(Servicio datos);

    /** @throws IllegalStateException si el servicio ya tiene citas programadas. */
    void eliminar(Integer id);
}
