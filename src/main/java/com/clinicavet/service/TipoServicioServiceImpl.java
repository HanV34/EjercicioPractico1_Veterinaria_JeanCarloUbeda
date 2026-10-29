package com.clinicavet.service;

import com.clinicavet.domain.TipoServicio;
import com.clinicavet.repository.TipoServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoServicioServiceImpl implements TipoServicioService {

    private final TipoServicioRepository repositorioTipos;

    public TipoServicioServiceImpl(TipoServicioRepository repositorioTipos) {
        this.repositorioTipos = repositorioTipos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoServicio> listarTodos() {
        return repositorioTipos.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoServicio> buscarPorId(Integer id) {
        return repositorioTipos.findById(id);
    }

    @Override
    @Transactional
    public TipoServicio guardar(TipoServicio datos) {
        TipoServicio destino;
        if (datos.getId() == null) {
            destino = new TipoServicio();
        } else {
            destino = repositorioTipos.findById(datos.getId())
                    .orElseThrow(() -> new IllegalArgumentException("El tipo de servicio no existe."));
        }
        destino.setNombre(datos.getNombre().trim());
        destino.setDescripcion(datos.getDescripcion());
        return repositorioTipos.save(destino);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        TipoServicio tipo = repositorioTipos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El tipo de servicio no existe."));
        if (!tipo.getServicios().isEmpty()) {
            throw new IllegalStateException("El tipo tiene servicios asociados.");
        }
        try {
            repositorioTipos.delete(tipo);
            repositorioTipos.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("El tipo tiene datos asociados.", ex);
        }
    }
}
