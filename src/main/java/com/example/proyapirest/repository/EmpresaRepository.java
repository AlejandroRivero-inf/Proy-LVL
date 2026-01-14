package com.example.proyapirest.repository;

import com.example.proyapirest.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByNombre(String nombre);
    List<Empresa> findByNombreContainingIgnoreCase(String nombre);
    List<Empresa> findBySector(String sector);
    List<Empresa> findByPais(String pais);
}
