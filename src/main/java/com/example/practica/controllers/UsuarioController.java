package com.example.practica.controllers;

import com.example.practica.models.Usuario;
import com.example.practica.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            if (usuarioDao.validarUsuario(usuario)) {
                Usuario user = usuarioDao.findByEmail(usuario.getEmail());
                Map<String, Object> response = new HashMap<>();
                response.put("nombres", user.getNombres());
                response.put("apellidos",user.getApellidos());
                response.put("cargo", user.getCargo());
                response.put("id", user.getId());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR USUARIO CONTROLLER");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hubo un problema al procesar la solicitud.");
        }
    }


    //-----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/perfil/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable int id) {
        Usuario usuario = usuarioDao.obtenerUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/perfil/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable int id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioDao.obtenerUsuarioPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setNombres(usuarioActualizado.getNombres());
        usuario.setApellidos(usuarioActualizado.getApellidos());
        usuario.setNombre_empresa(usuarioActualizado.getNombre_empresa());
        usuario.setCargo(usuarioActualizado.getCargo());
        usuario.setTelefono(usuarioActualizado.getTelefono());
        usuario.setEmail(usuarioActualizado.getEmail());

        usuarioDao.guardarUsuario(usuario);

        return ResponseEntity.ok(usuario);
    }


}
