package com.clinicavet.repository;

import com.clinicavet.domain.Cita;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findAllByOrderByFechaAsc();

    boolean existsByServicioId(Integer servicioId);
}
