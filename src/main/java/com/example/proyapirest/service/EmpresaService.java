package com.example.proyapirest.service;

import com.example.proyapirest.dto.request.EmpresaRequestDTO;
import com.example.proyapirest.dto.response.EmpresaResponseDTO;
import com.example.proyapirest.entity.Empresa;
import com.example.proyapirest.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Transactional
    public EmpresaResponseDTO crearEmpresa(EmpresaRequestDTO requestDTO) {
        Empresa empresa = new Empresa();
        empresa.setNombre(requestDTO.getNombre());
        empresa.setSector(requestDTO.getSector());
        empresa.setPais(requestDTO.getPais());

        Empresa empresaGuardada = empresaRepository.save(empresa);
        return convertirADTO(empresaGuardada);
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listarEmpresas() {
        return empresaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDTO obtenerEmpresaPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        return convertirADTO(empresa);
    }

    @Transactional
    public EmpresaResponseDTO actualizarEmpresa(Long id, EmpresaRequestDTO requestDTO) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        empresa.setNombre(requestDTO.getNombre());
        empresa.setSector(requestDTO.getSector());
        empresa.setPais(requestDTO.getPais());

        Empresa empresaActualizada = empresaRepository.save(empresa);
        return convertirADTO(empresaActualizada);
    }

    @Transactional
    public void eliminarEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa no encontrada");
        }
        empresaRepository.deleteById(id);
    }

    private EmpresaResponseDTO convertirADTO(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(empresa.getId());
        dto.setNombre(empresa.getNombre());
        dto.setSector(empresa.getSector());
        dto.setPais(empresa.getPais());
        return dto;
    }
}
