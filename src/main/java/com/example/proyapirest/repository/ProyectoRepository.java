package com.example.proyapirest.repository;

import com.example.proyapirest.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    
    List<Proyecto> findByCodigoProyectoContainingIgnoreCase(String codigo);
    List<Proyecto> findByNombreProyectoContainingIgnoreCase(String nombre);
    List<Proyecto> findByEstado(String estado);
    
    @Query("SELECT p FROM Proyecto p WHERE " +
           "LOWER(p.codigoProyecto) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(p.nombreProyecto) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Proyecto> busquedaAvanzada(@Param("texto") String texto);
    
    List<Proyecto> findByUsuarioCreadorId(Long usuarioId);
    
    @Query("SELECT p FROM Proyecto p WHERE p.usuarioCreador.empresa.id = :empresaId")
    List<Proyecto> findByEmpresaId(@Param("empresaId") Long empresaId);
}
