package com.example.proyapirest.controller;

import com.example.proyapirest.dto.request.EmpresaRequestDTO;
import com.example.proyapirest.dto.response.EmpresaResponseDTO;
import com.example.proyapirest.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmpresaController {
    
    private final EmpresaService empresaService;
    
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> crearEmpresa(@Valid @RequestBody EmpresaRequestDTO requestDTO) {
        EmpresaResponseDTO empresa = empresaService.crearEmpresa(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }
    
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listarEmpresas() {
        List<EmpresaResponseDTO> empresas = empresaService.listarEmpresas();
        return ResponseEntity.ok(empresas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> obtenerEmpresaPorId(@PathVariable Long id) {
        EmpresaResponseDTO empresa = empresaService.obtenerEmpresaPorId(id);
        return ResponseEntity.ok(empresa);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> actualizarEmpresa(
            @PathVariable Long id,
            @Valid @RequestBody EmpresaRequestDTO requestDTO) {
        EmpresaResponseDTO empresa = empresaService.actualizarEmpresa(id, requestDTO);
        return ResponseEntity.ok(empresa);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}
