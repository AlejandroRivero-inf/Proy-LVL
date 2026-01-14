package com.example.proyapirest.service;

import com.example.proyapirest.dto.request.UsuarioRequestDTO;
import com.example.proyapirest.dto.response.CargoResponseDTO;
import com.example.proyapirest.dto.response.EmpresaResponseDTO;
import com.example.proyapirest.dto.response.UsuarioResponseDTO;
import com.example.proyapirest.entity.Cargo;
import com.example.proyapirest.entity.Empresa;
import com.example.proyapirest.entity.Rol;
import com.example.proyapirest.entity.Usuario;
import com.example.proyapirest.repository.CargoRepository;
import com.example.proyapirest.repository.EmpresaRepository;
import com.example.proyapirest.repository.RolRepository;
import com.example.proyapirest.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final EmpresaRepository empresaRepository;
    private final CargoRepository cargoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
        if (usuarioRepository.findByCorreoElectronico(request.getCorreoElectronico()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        Usuario usuario = new Usuario();
        mapRequestToEntity(request, usuario);
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        Usuario guardado = usuarioRepository.save(usuario);
        return mapEntityToResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
        return mapEntityToResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        if (!usuario.getCorreoElectronico().equals(request.getCorreoElectronico()) &&
                usuarioRepository.findByCorreoElectronico(request.getCorreoElectronico()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso por otro usuario");
        }

        mapRequestToEntity(request, usuario);

        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        Usuario actualizado = usuarioRepository.save(usuario);
        return mapEntityToResponse(actualizado);
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private void mapRequestToEntity(UsuarioRequestDTO request, Usuario usuario) {
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setCorreoElectronico(request.getCorreoElectronico());
        usuario.setTelefono(request.getTelefono());

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + request.getRolId()));
        usuario.setRol(rol);

        Empresa empresa = empresaRepository.findById(request.getEmpresaId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Empresa no encontrada con ID: " + request.getEmpresaId()));
        usuario.setEmpresa(empresa);

        Cargo cargo = cargoRepository.findById(request.getCargoId())
                .orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado con ID: " + request.getCargoId()));
        usuario.setCargo(cargo);
    }

    private UsuarioResponseDTO mapEntityToResponse(Usuario usuario) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setApellidos(usuario.getApellidos());
        response.setCorreoElectronico(usuario.getCorreoElectronico());
        response.setTelefono(usuario.getTelefono());

        if (usuario.getRol() != null) {
            response.setRol(usuario.getRol().getNombre());
        }

        if (usuario.getEmpresa() != null) {
            EmpresaResponseDTO empresaDTO = new EmpresaResponseDTO();
            empresaDTO.setId(usuario.getEmpresa().getId());
            empresaDTO.setNombre(usuario.getEmpresa().getNombre());
            empresaDTO.setSector(usuario.getEmpresa().getSector());
            empresaDTO.setPais(usuario.getEmpresa().getPais());
            response.setEmpresa(empresaDTO);
        }

        if (usuario.getCargo() != null) {
            CargoResponseDTO cargoDTO = new CargoResponseDTO();
            cargoDTO.setId(usuario.getCargo().getId());
            cargoDTO.setNombre(usuario.getCargo().getNombre());
            cargoDTO.setNivel(usuario.getCargo().getNivel());
            response.setCargo(cargoDTO);
        }

        return response;
    }
}
