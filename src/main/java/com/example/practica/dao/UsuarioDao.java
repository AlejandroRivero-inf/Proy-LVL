package com.example.practica.dao;

import com.example.practica.models.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@Transactional
public class UsuarioDao {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean validarUsuario(Usuario usuario) {


        String query = "FROM Usuario u WHERE u.email = :email AND u.password = :password";
        List<Usuario> lista = entityManager.createQuery(query, Usuario.class)
                .setParameter("email", usuario.getEmail())
                .setParameter("password", usuario.getPassword())
                .getResultList();

        return !lista.isEmpty();
    }

    public Usuario findByEmail(String email) {
        try {
            String query = "FROM Usuario u WHERE u.email = :email";
            return entityManager.createQuery(query, Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return entityManager.find(Usuario.class, id);
    }

    public void guardarUsuario(Usuario usuario) {
        entityManager.merge(usuario);
    }



}
