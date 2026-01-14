
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(20) UNIQUE NOT NULL
);


CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    correo_electronico VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    nombre_empresa VARCHAR(100),
    cargo_empresa VARCHAR(100),
    telefono VARCHAR(20),
    rol_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS proyectos (
    id BIGSERIAL PRIMARY KEY,
    codigo_proyecto VARCHAR(20) UNIQUE NOT NULL,
    nombre_proyecto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    icono_proyecto VARCHAR(10) DEFAULT '📋',
    fecha_inicio DATE,
    fecha_finalizacion DATE,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    usuario_creador_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_creador_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Insertar roles iniciales
INSERT INTO roles (nombre) 
VALUES ('ADMIN'), ('USER')
ON CONFLICT (nombre) DO NOTHING;

-- Insertar usuario administrador
-- Contraseña: password123
INSERT INTO usuarios (nombre, apellidos, correo_electronico, contrasena, nombre_empresa, cargo_empresa, telefono, rol_id)
VALUES (
    'Miguel Angel', 
    'Liberato Carmin', 
    'admin@proy.com', 
    '$2a$10$qsZZwOZMaFWAfDNSnkpQ/.yOt1Q/yJ5ZrJA0myhCiDzHciRNNOkVq', 
    'LVL Consulting', 
    'CEO', 
    '+51987654321', 
    (SELECT id FROM roles WHERE nombre = 'ADMIN')
)
ON CONFLICT (correo_electronico) DO NOTHING;

-- Insertar proyectos de ejemplo
INSERT INTO proyectos (codigo_proyecto, nombre_proyecto, descripcion, icono_proyecto, fecha_inicio, estado, usuario_creador_id)
VALUES 
    ('ATA-1', 'Proyecto de App', 'Desarrollo de aplicación móvil', '📱', '2026-01-01', 'ACTIVO', 1),
    ('PA-21', 'Diseño de RR.SS.', 'Diseño de redes sociales para marketing', '🎨', '2026-01-05', 'EN_CURSO', 1),
    ('PA-2', 'Programación de Backend', 'API REST con Spring Boot', '💻', '2025-12-15', 'ACTIVO', 1)
ON CONFLICT (codigo_proyecto) DO NOTHING;