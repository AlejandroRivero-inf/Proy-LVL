package com.example.practica.dao;


import com.example.practica.models.Proyecto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class ProyectoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void guardarProyecto(Proyecto proyecto) {
        if (proyecto.getId() == 0) {
            entityManager.persist(proyecto);
        } else {
            entityManager.merge(proyecto);
        }
    }

    public Proyecto buscarPorCodigoProyecto(String codigoProyecto) {
        try {
            return entityManager.createQuery("SELECT p FROM Proyecto p WHERE p.codigo_proyecto = :codigo_proyecto", Proyecto.class)
                    .setParameter("codigo_proyecto", codigoProyecto)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }



}
