package com.clinicavet.repository;

import com.clinicavet.domain.TipoServicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoServicioRepository extends JpaRepository<TipoServicio, Integer> {

    List<TipoServicio> findAllByOrderByNombreAsc();
}
