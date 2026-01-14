package com.example.proyapirest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoResponseDTO {
    private Long id;
    private String codigoProyecto;
    private String nombreProyecto;
    private String descripcion;
    private String iconoProyecto;
    private LocalDate fechaInicio;
    private LocalDate fechaFinalizacion;
    private String estado;
    private String nombreUsuarioCreador;
    private String correoUsuarioCreador;
    private String empresaUsuarioCreador;
    private String cargoUsuarioCreador;
}
