package com.clinicavet.controllers;

import com.clinicavet.domain.Servicio;
import com.clinicavet.domain.TipoServicio;
import com.clinicavet.service.ServicioService;
import com.clinicavet.service.TipoServicioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    private static final String SECCION = "servicios";

    private final ServicioService servicioCatalogo;
    private final TipoServicioService servicioTipos;

    public ServicioController(ServicioService servicioCatalogo, TipoServicioService servicioTipos) {
        this.servicioCatalogo = servicioCatalogo;
        this.servicioTipos = servicioTipos;
    }

    @GetMapping
    public String mostrarServicios(Model modelo) {
        modelo.addAttribute("seccionActiva", SECCION);
        modelo.addAttribute("serviciosOfrecidos", servicioCatalogo.listarTodos());
        return "servicios/lista";
    }

    @GetMapping("/nuevo")
    public String prepararAlta(Model modelo) {
        Servicio vacio = new Servicio();
        vacio.setTipoServicio(new TipoServicio());
        cargarFormulario(modelo, vacio);
        return "servicios/formulario";
    }

    @GetMapping("/editar/{id}")
    public String prepararEdicion(@PathVariable("id") Integer id, Model modelo, RedirectAttributes avisos) {
        var encontrado = servicioCatalogo.buscarPorId(id);
        if (encontrado.isEmpty()) {
            avisos.addFlashAttribute("mensajeError", "aviso.servicio.noExiste");
            return "redirect:/servicios";
        }
        cargarFormulario(modelo, encontrado.get());
        return "servicios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarServicio(@Valid @ModelAttribute("servicio") Servicio servicio,
            BindingResult resultado, Model modelo, RedirectAttributes avisos) {
        // El desplegable puede llegar vacío: el objeto existe pero sin identificador.
        if (servicio.getTipoServicio() == null || servicio.getTipoServicio().getId() == null) {
            if (!resultado.hasFieldErrors("tipoServicio")) {
                resultado.rejectValue("tipoServicio", "tipo.obligatorio", "Debe seleccionar un tipo de servicio.");
            }
        }
        if (resultado.hasErrors()) {
            cargarFormulario(modelo, servicio);
            return "servicios/formulario";
        }
        boolean esAlta = servicio.getId() == null;
        try {
            servicioCatalogo.guardar(servicio);
        } catch (IllegalArgumentException ex) {
            avisos.addFlashAttribute("mensajeError", "aviso.servicio.noExiste");
            return "redirect:/servicios";
        }
        avisos.addFlashAttribute("mensajeExito", esAlta ? "aviso.servicio.agregado" : "aviso.servicio.modificado");
        return "redirect:/servicios";
    }

    @PostMapping("/eliminar")
    public String eliminarServicio(@RequestParam("id") Integer id, RedirectAttributes avisos) {
        try {
            servicioCatalogo.eliminar(id);
            avisos.addFlashAttribute("mensajeExito", "aviso.servicio.eliminado");
        } catch (IllegalArgumentException ex) {
            avisos.addFlashAttribute("mensajeError", "aviso.servicio.noExiste");
        } catch (IllegalStateException ex) {
            avisos.addFlashAttribute("mensajeError", "aviso.servicio.conCitas");
        }
        return "redirect:/servicios";
    }

    private void cargarFormulario(Model modelo, Servicio servicio) {
        modelo.addAttribute("seccionActiva", SECCION);
        modelo.addAttribute("servicio", servicio);
        modelo.addAttribute("tiposDisponibles", servicioTipos.listarTodos());
    }
}
