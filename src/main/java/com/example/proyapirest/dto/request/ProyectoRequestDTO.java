package com.example.proyapirest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoRequestDTO {
    
    @NotBlank(message = "El código del proyecto es obligatorio")
    @Size(max = 20, message = "El código no puede exceder 20 caracteres")
    private String codigoProyecto;
    
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreProyecto;
    
    private String descripcion;
    
    @Size(max = 10, message = "El icono no puede exceder 10 caracteres")
    private String iconoProyecto;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFinalizacion;
    
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    private String estado;
}
