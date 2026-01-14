package com.example.proyapirest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String correoElectronico;
    private String telefono;
    private String rol;
    private EmpresaResponseDTO empresa;
    private CargoResponseDTO cargo;
}
