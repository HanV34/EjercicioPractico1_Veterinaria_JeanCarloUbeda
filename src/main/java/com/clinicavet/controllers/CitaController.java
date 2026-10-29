package com.clinicavet.controllers;

import com.clinicavet.service.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CitaController {

    private final CitaService servicioCitas;

    public CitaController(CitaService servicioCitas) {
        this.servicioCitas = servicioCitas;
    }

    // Consulta de solo lectura: no hay formularios ni botones de modificación.
    @GetMapping("/citas")
    public String mostrarCitas(Model modelo) {
        modelo.addAttribute("seccionActiva", "citas");
        modelo.addAttribute("citasProgramadas", servicioCitas.listarProgramadas());
        return "citas/lista";
    }
}
