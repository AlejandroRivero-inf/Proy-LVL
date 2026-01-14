package com.example.proyapirest.controller;

import com.example.proyapirest.dto.request.RolRequestDTO;
import com.example.proyapirest.dto.response.RolResponseDTO;
import com.example.proyapirest.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RolController {

    private final RolService rolService;

    @PostMapping
    public ResponseEntity<RolResponseDTO> crearRol(@Valid @RequestBody RolRequestDTO requestDTO) {
        RolResponseDTO rol = rolService.crearRol(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rol);
    }

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> listarRoles() {
        List<RolResponseDTO> roles = rolService.listarRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> obtenerRolPorId(@PathVariable Long id) {
        RolResponseDTO rol = rolService.obtenerRolPorId(id);
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
