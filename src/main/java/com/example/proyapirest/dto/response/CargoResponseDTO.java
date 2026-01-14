package com.example.proyapirest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoResponseDTO {
    private Long id;
    private String nombre;
    private String nivel;
}
