package com.example.proyapirest.controller;

import com.example.proyapirest.dto.request.UsuarioLoginRequestDTO;
import com.example.proyapirest.dto.response.AuthResponseDTO;
import com.example.proyapirest.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {
    
    private final AuthService authService;

    
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> iniciarSesion(@Valid @RequestBody UsuarioLoginRequestDTO requestDTO) {
        log.info("Usuario autenticando: {}", requestDTO.getCorreoElectronico());       
        return ResponseEntity.ok(authService.iniciarSesion(requestDTO));
    }
}
