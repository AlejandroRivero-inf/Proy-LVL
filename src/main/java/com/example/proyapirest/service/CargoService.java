package com.example.proyapirest.service;

import com.example.proyapirest.dto.request.CargoRequestDTO;
import com.example.proyapirest.dto.response.CargoResponseDTO;
import com.example.proyapirest.entity.Cargo;
import com.example.proyapirest.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CargoService {
    
    private final CargoRepository cargoRepository;
    
    @Transactional
    public CargoResponseDTO crearCargo(CargoRequestDTO requestDTO) {
        Cargo cargo = new Cargo();
        cargo.setNombre(requestDTO.getNombre());
        cargo.setNivel(requestDTO.getNivel());
        
        Cargo cargoGuardado = cargoRepository.save(cargo);
        return convertirADTO(cargoGuardado);
    }
    
    @Transactional(readOnly = true)
    public List<CargoResponseDTO> listarCargos() {
        return cargoRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CargoResponseDTO obtenerCargoPorId(Long id) {
        Cargo cargo = cargoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));
        return convertirADTO(cargo);
    }
    
    @Transactional
    public CargoResponseDTO actualizarCargo(Long id, CargoRequestDTO requestDTO) {
        Cargo cargo = cargoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));
        
        cargo.setNombre(requestDTO.getNombre());
        cargo.setNivel(requestDTO.getNivel());
        
        Cargo cargoActualizado = cargoRepository.save(cargo);
        return convertirADTO(cargoActualizado);
    }
    
    @Transactional
    public void eliminarCargo(Long id) {
        if (!cargoRepository.existsById(id)) {
            throw new RuntimeException("Cargo no encontrado");
        }
        cargoRepository.deleteById(id);
    }
    
    private CargoResponseDTO convertirADTO(Cargo cargo) {
        CargoResponseDTO dto = new CargoResponseDTO();
        dto.setId(cargo.getId());
        dto.setNombre(cargo.getNombre());
        dto.setNivel(cargo.getNivel());
        return dto;
    }
}
