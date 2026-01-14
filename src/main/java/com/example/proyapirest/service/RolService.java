package com.example.proyapirest.service;

import com.example.proyapirest.dto.request.RolRequestDTO;
import com.example.proyapirest.dto.response.RolResponseDTO;
import com.example.proyapirest.entity.Rol;
import com.example.proyapirest.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    @Transactional
    public RolResponseDTO crearRol(RolRequestDTO request) {
        if (rolRepository.findByNombre(request.getNombre()).isPresent()) {
            throw new IllegalArgumentException("El rol ya existe");
        }

        Rol rol = new Rol();
        rol.setNombre(request.getNombre());

        Rol guardado = rolRepository.save(rol);
        return mapEntityToResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<RolResponseDTO> listarRoles() {
        return rolRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RolResponseDTO obtenerRolPorId(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + id));
        return mapEntityToResponse(rol);
    }

    @Transactional
    public void eliminarRol(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new EntityNotFoundException("Rol no encontrado con ID: " + id);
        }
        rolRepository.deleteById(id);
    }

    private RolResponseDTO mapEntityToResponse(Rol rol) {
        RolResponseDTO response = new RolResponseDTO();
        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        return response;
    }
}
