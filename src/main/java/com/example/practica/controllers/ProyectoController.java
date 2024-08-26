package com.example.practica.controllers;

import com.example.practica.dao.ProyectoDao;
import com.example.practica.dao.UsuarioDao;
import com.example.practica.models.Proyecto;

import com.example.practica.models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoDao proyectoDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @PostMapping("/crear")
    public ResponseEntity<String> crearProyecto(@RequestBody Proyecto proyecto) {
        if (proyecto.getUsuario() == null || proyecto.getUsuario().getId() == 0) {
            return ResponseEntity.badRequest().body("ID de usuario no proporcionado");
        }
        Usuario usuario = usuarioDao.obtenerUsuarioPorId(proyecto.getUsuario().getId());
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
        proyecto.setUsuario(usuario);
        try {
            proyectoDao.guardarProyecto(proyecto);
            return ResponseEntity.ok("Proyecto creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el proyecto: " + e.getMessage());
        }
    }


    @GetMapping("/buscar/{codigoProyecto}")
    public ResponseEntity<?> buscarProyectoPorCodigo(@PathVariable String codigoProyecto) {
        Proyecto proyecto = proyectoDao.buscarPorCodigoProyecto(codigoProyecto);
        if (proyecto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }
        return ResponseEntity.ok(proyecto);
    }

}

