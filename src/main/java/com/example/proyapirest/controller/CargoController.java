package com.example.proyapirest.controller;

import com.example.proyapirest.dto.request.CargoRequestDTO;
import com.example.proyapirest.dto.response.CargoResponseDTO;
import com.example.proyapirest.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CargoController {
    
    private final CargoService cargoService;
    
    @PostMapping
    public ResponseEntity<CargoResponseDTO> crearCargo(@Valid @RequestBody CargoRequestDTO requestDTO) {
        CargoResponseDTO cargo = cargoService.crearCargo(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cargo);
    }
    
    @GetMapping
    public ResponseEntity<List<CargoResponseDTO>> listarCargos() {
        List<CargoResponseDTO> cargos = cargoService.listarCargos();
        return ResponseEntity.ok(cargos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> obtenerCargoPorId(@PathVariable Long id) {
        CargoResponseDTO cargo = cargoService.obtenerCargoPorId(id);
        return ResponseEntity.ok(cargo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> actualizarCargo(
            @PathVariable Long id,
            @Valid @RequestBody CargoRequestDTO requestDTO) {
        CargoResponseDTO cargo = cargoService.actualizarCargo(id, requestDTO);
        return ResponseEntity.ok(cargo);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCargo(@PathVariable Long id) {
        cargoService.eliminarCargo(id);
        return ResponseEntity.noContent().build();
    }
}

