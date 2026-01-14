package com.example.proyapirest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "proyectos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo_proyecto", unique = true, nullable = false, length = 20)
    private String codigoProyecto;
    
    @Column(name = "nombre_proyecto", nullable = false, length = 100)
    private String nombreProyecto;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "icono_proyecto", length = 10)
    private String iconoProyecto = "📋";
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "ACTIVO";
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_creador_id", nullable = false)
    private Usuario usuarioCreador;
    
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}