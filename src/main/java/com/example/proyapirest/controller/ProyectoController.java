package com.example.proyapirest.controller;

import com.example.proyapirest.dto.request.ProyectoRequestDTO;
import com.example.proyapirest.dto.response.ProyectoResponseDTO;
import com.example.proyapirest.service.ProyectoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProyectoController {
    
    private final ProyectoService proyectoService;
    
    @PostMapping
    public ResponseEntity<ProyectoResponseDTO> crearProyecto(
            @Valid @RequestBody ProyectoRequestDTO requestDTO,
            @RequestParam Long usuarioCreadorId) {
        ProyectoResponseDTO proyecto = proyectoService.crearProyecto(requestDTO, usuarioCreadorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(proyecto);
    }
    
    @GetMapping
    public ResponseEntity<List<ProyectoResponseDTO>> listarProyectos() {
        List<ProyectoResponseDTO> proyectos = proyectoService.listarProyectos();
        return ResponseEntity.ok(proyectos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponseDTO> obtenerProyectoPorId(@PathVariable Long id) {
        ProyectoResponseDTO proyecto = proyectoService.obtenerProyectoPorId(id);
        return ResponseEntity.ok(proyecto);
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<ProyectoResponseDTO>> busquedaAvanzada(@RequestParam String texto) {
        List<ProyectoResponseDTO> proyectos = proyectoService.busquedaAvanzada(texto);
        return ResponseEntity.ok(proyectos);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponseDTO> actualizarProyecto(
            @PathVariable Long id,
            @Valid @RequestBody ProyectoRequestDTO requestDTO) {
        ProyectoResponseDTO proyecto = proyectoService.actualizarProyecto(id, requestDTO);
        return ResponseEntity.ok(proyecto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
