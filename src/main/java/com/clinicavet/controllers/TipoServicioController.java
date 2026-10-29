package com.clinicavet.controllers;

import com.clinicavet.domain.TipoServicio;
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
@RequestMapping("/tipos")
public class TipoServicioController {

    private static final String SECCION = "tipos";

    private final TipoServicioService servicioTipos;

    public TipoServicioController(TipoServicioService servicioTipos) {
        this.servicioTipos = servicioTipos;
    }

    @GetMapping
    public String mostrarTipos(Model modelo) {
        modelo.addAttribute("seccionActiva", SECCION);
        modelo.addAttribute("tiposRegistrados", servicioTipos.listarTodos());
        return "tipos/lista";
    }

    @GetMapping("/nuevo")
    public String prepararAlta(Model modelo) {
        modelo.addAttribute("seccionActiva", SECCION);
        modelo.addAttribute("tipoServicio", new TipoServicio());
        return "tipos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String prepararEdicion(@PathVariable("id") Integer id, Model modelo, RedirectAttributes avisos) {
        var encontrado = servicioTipos.buscarPorId(id);
        if (encontrado.isEmpty()) {
            avisos.addFlashAttribute("mensajeError", "aviso.tipo.noExiste");
            return "redirect:/tipos";
        }
        modelo.addAttribute("seccionActiva", SECCION);
        modelo.addAttribute("tipoServicio", encontrado.get());
        return "tipos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarTipo(@Valid @ModelAttribute("tipoServicio") TipoServicio tipo,
            BindingResult resultado, Model modelo, RedirectAttributes avisos) {
        if (resultado.hasErrors()) {
            modelo.addAttribute("seccionActiva", SECCION);
            return "tipos/formulario";
        }
        boolean esAlta = tipo.getId() == null;
        try {
            servicioTipos.guardar(tipo);
        } catch (IllegalArgumentException ex) {
            avisos.addFlashAttribute("mensajeError", "aviso.tipo.noExiste");
            return "redirect:/tipos";
        }
        avisos.addFlashAttribute("mensajeExito", esAlta ? "aviso.tipo.agregado" : "aviso.tipo.modificado");
        return "redirect:/tipos";
    }

    @PostMapping("/eliminar")
    public String eliminarTipo(@RequestParam("id") Integer id, RedirectAttributes avisos) {
        try {
            servicioTipos.eliminar(id);
            avisos.addFlashAttribute("mensajeExito", "aviso.tipo.eliminado");
        } catch (IllegalArgumentException ex) {
            avisos.addFlashAttribute("mensajeError", "aviso.tipo.noExiste");
        } catch (IllegalStateException ex) {
            avisos.addFlashAttribute("mensajeError", "aviso.tipo.conServicios");
        }
        return "redirect:/tipos";
    }
}
