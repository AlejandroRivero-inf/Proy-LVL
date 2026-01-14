package com.example.proyapirest.service;

import com.example.proyapirest.dto.request.UsuarioLoginRequestDTO;
import com.example.proyapirest.dto.response.AuthResponseDTO;
import com.example.proyapirest.dto.response.CargoResponseDTO;
import com.example.proyapirest.dto.response.EmpresaResponseDTO;
import com.example.proyapirest.dto.response.UsuarioResponseDTO;
import com.example.proyapirest.entity.Usuario;
import com.example.proyapirest.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO iniciarSesion(UsuarioLoginRequestDTO requestDTO) {

        Usuario usuario = usuarioRepository.findByCorreoElectronico(requestDTO.getCorreoElectronico())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getCorreoElectronico(),
                        requestDTO.getContrasena()));

        String token = jwtService.generarToken(requestDTO.getCorreoElectronico());

        UsuarioResponseDTO usuarioDTO = convertirUsuarioADTO(usuario);

        return new AuthResponseDTO(token, usuarioDTO);
    }

    private UsuarioResponseDTO convertirUsuarioADTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setCorreoElectronico(usuario.getCorreoElectronico());
        dto.setTelefono(usuario.getTelefono());
        dto.setRol(usuario.getRol().getNombre());

        if (usuario.getEmpresa() != null) {
            EmpresaResponseDTO empresaDTO = new EmpresaResponseDTO();
            empresaDTO.setId(usuario.getEmpresa().getId());
            empresaDTO.setNombre(usuario.getEmpresa().getNombre());
            empresaDTO.setSector(usuario.getEmpresa().getSector());
            empresaDTO.setPais(usuario.getEmpresa().getPais());
            dto.setEmpresa(empresaDTO);
        }

        if (usuario.getCargo() != null) {
            CargoResponseDTO cargoDTO = new CargoResponseDTO();
            cargoDTO.setId(usuario.getCargo().getId());
            cargoDTO.setNombre(usuario.getCargo().getNombre());
            cargoDTO.setNivel(usuario.getCargo().getNivel());
            dto.setCargo(cargoDTO);
        }

        return dto;
    }
}
