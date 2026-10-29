package com.clinicavet.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Datos del formulario de consultas. No es una entidad: no se guarda en la base de datos. */
public class FormularioConsulta {

    @NotBlank(message = "Escriba su nombre.")
    @Size(max = 100, message = "El nombre es demasiado largo.")
    private String nombre;

    @NotBlank(message = "Escriba su correo electrónico.")
    @Email(message = "El correo electrónico no es válido.")
    private String correo;

    @NotBlank(message = "Escriba el nombre de su mascota.")
    @Size(max = 100, message = "El nombre de la mascota es demasiado largo.")
    private String nombreMascota;

    @NotBlank(message = "Escriba su mensaje.")
    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres.")
    private String mensaje;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreMascota() { return nombreMascota; }
    public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
