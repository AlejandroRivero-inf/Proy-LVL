package com.example.proyapirest.service;

import com.example.proyapirest.dto.request.ProyectoRequestDTO;
import com.example.proyapirest.dto.response.ProyectoResponseDTO;
import com.example.proyapirest.entity.Proyecto;
import com.example.proyapirest.entity.Usuario;
import com.example.proyapirest.repository.ProyectoRepository;
import com.example.proyapirest.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public ProyectoResponseDTO crearProyecto(ProyectoRequestDTO requestDTO, Long usuarioCreadorId) {
        Usuario usuarioCreador = usuarioRepository.findById(usuarioCreadorId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Proyecto proyecto = new Proyecto();
        proyecto.setCodigoProyecto(requestDTO.getCodigoProyecto());
        proyecto.setNombreProyecto(requestDTO.getNombreProyecto());
        proyecto.setDescripcion(requestDTO.getDescripcion());
        proyecto.setIconoProyecto(requestDTO.getIconoProyecto() != null ? requestDTO.getIconoProyecto() : "📋");
        proyecto.setFechaInicio(requestDTO.getFechaInicio());
        proyecto.setFechaFinalizacion(requestDTO.getFechaFinalizacion());
        proyecto.setEstado(requestDTO.getEstado() != null ? requestDTO.getEstado() : "ACTIVO");
        proyecto.setUsuarioCreador(usuarioCreador);

        Proyecto proyectoGuardado = proyectoRepository.save(proyecto);
        return convertirADTO(proyectoGuardado);
    }

    @Transactional(readOnly = true)
    public List<ProyectoResponseDTO> listarProyectos() {
        return proyectoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProyectoResponseDTO obtenerProyectoPorId(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        return convertirADTO(proyecto);
    }

    @Transactional(readOnly = true)
    public List<ProyectoResponseDTO> busquedaAvanzada(String texto) {
        return proyectoRepository.busquedaAvanzada(texto).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProyectoResponseDTO actualizarProyecto(Long id, ProyectoRequestDTO requestDTO) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        proyecto.setNombreProyecto(requestDTO.getNombreProyecto());
        proyecto.setDescripcion(requestDTO.getDescripcion());
        proyecto.setIconoProyecto(requestDTO.getIconoProyecto());
        proyecto.setFechaInicio(requestDTO.getFechaInicio());
        proyecto.setFechaFinalizacion(requestDTO.getFechaFinalizacion());
        if (requestDTO.getEstado() != null) {
            proyecto.setEstado(requestDTO.getEstado());
        }

        Proyecto proyectoActualizado = proyectoRepository.save(proyecto);
        return convertirADTO(proyectoActualizado);
    }

    @Transactional
    public void eliminarProyecto(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado");
        }
        proyectoRepository.deleteById(id);
    }

    private ProyectoResponseDTO convertirADTO(Proyecto proyecto) {
        ProyectoResponseDTO dto = new ProyectoResponseDTO();
        dto.setId(proyecto.getId());
        dto.setCodigoProyecto(proyecto.getCodigoProyecto());
        dto.setNombreProyecto(proyecto.getNombreProyecto());
        dto.setDescripcion(proyecto.getDescripcion());
        dto.setIconoProyecto(proyecto.getIconoProyecto());
        dto.setFechaInicio(proyecto.getFechaInicio());
        dto.setFechaFinalizacion(proyecto.getFechaFinalizacion());
        dto.setEstado(proyecto.getEstado());
        dto.setNombreUsuarioCreador(
                proyecto.getUsuarioCreador().getNombre() + " " + proyecto.getUsuarioCreador().getApellidos());
        dto.setCorreoUsuarioCreador(proyecto.getUsuarioCreador().getCorreoElectronico());

        if (proyecto.getUsuarioCreador().getEmpresa() != null) {
            dto.setEmpresaUsuarioCreador(proyecto.getUsuarioCreador().getEmpresa().getNombre());
        }

        if (proyecto.getUsuarioCreador().getCargo() != null) {
            dto.setCargoUsuarioCreador(proyecto.getUsuarioCreador().getCargo().getNombre());
        }

        return dto;
    }
}
