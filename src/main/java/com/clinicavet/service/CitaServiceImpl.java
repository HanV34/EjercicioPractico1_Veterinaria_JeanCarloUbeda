package com.clinicavet.service;

import com.clinicavet.domain.Cita;
import com.clinicavet.repository.CitaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository repositorioCitas;

    public CitaServiceImpl(CitaRepository repositorioCitas) {
        this.repositorioCitas = repositorioCitas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> listarProgramadas() {
        return repositorioCitas.findAllByOrderByFechaAsc();
    }
}
