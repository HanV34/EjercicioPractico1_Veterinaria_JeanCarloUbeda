package com.clinicavet.service;

import com.clinicavet.domain.Servicio;
import com.clinicavet.repository.CitaRepository;
import com.clinicavet.repository.ServicioRepository;
import com.clinicavet.repository.TipoServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repositorioServicios;
    private final TipoServicioRepository repositorioTipos;
    private final CitaRepository repositorioCitas;

    public ServicioServiceImpl(ServicioRepository repositorioServicios,
            TipoServicioRepository repositorioTipos,
            CitaRepository repositorioCitas) {
        this.repositorioServicios = repositorioServicios;
        this.repositorioTipos = repositorioTipos;
        this.repositorioCitas = repositorioCitas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> listarTodos() {
        return repositorioServicios.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> buscarPorId(Integer id) {
        return repositorioServicios.findById(id);
    }

    @Override
    @Transactional
    public Servicio guardar(Servicio datos) {
        Servicio destino;
        if (datos.getId() == null) {
            destino = new Servicio();
        } else {
            destino = repositorioServicios.findById(datos.getId())
                    .orElseThrow(() -> new IllegalArgumentException("El servicio no existe."));
        }
        destino.setNombre(datos.getNombre().trim());
        destino.setPrecio(datos.getPrecio());
        destino.setDuracionMinutos(datos.getDuracionMinutos());
        // Se enlaza con el tipo real de la base de datos, no con el objeto que llegó del formulario.
        destino.setTipoServicio(repositorioTipos.getReferenceById(datos.getTipoServicio().getId()));
        return repositorioServicios.save(destino);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!repositorioServicios.existsById(id)) {
            throw new IllegalArgumentException("El servicio no existe.");
        }
        if (repositorioCitas.existsByServicioId(id)) {
            throw new IllegalStateException("El servicio tiene citas programadas.");
        }
        try {
            repositorioServicios.deleteById(id);
            repositorioServicios.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("El servicio tiene datos asociados.", ex);
        }
    }
}
