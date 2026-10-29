package com.clinicavet.controllers;

import com.clinicavet.domain.FormularioConsulta;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InicioController {

    @GetMapping("/")
    public String mostrarInicio(Model modelo) {
        modelo.addAttribute("seccionActiva", "inicio");
        modelo.addAttribute("consulta", new FormularioConsulta());
        return "inicio";
    }

    // Solo interfaz: no se guarda nada, se agradece y se vuelve a la misma página.
    @PostMapping("/consultas/enviar")
    public String recibirConsulta(@Valid @ModelAttribute("consulta") FormularioConsulta consulta,
            BindingResult resultado, Model modelo, RedirectAttributes avisos) {
        if (resultado.hasErrors()) {
            modelo.addAttribute("seccionActiva", "inicio");
            return "inicio";
        }
        avisos.addFlashAttribute("mensajeExito", "aviso.consulta.gracias");
        return "redirect:/#consulta";
    }
}
