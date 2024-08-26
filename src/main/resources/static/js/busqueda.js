$(document).ready(function() {
            $('#btnBuscar').on('click', function() {
                var codigo = $('#codigoProyecto').val();

                if (codigo) {
                    $.ajax({
                        url: `/api/proyectos/buscar/${codigo}`,
                        type: 'GET',
                        success: function(proyecto) {
                            $('#nombreProyecto').val(proyecto.nombre);
                            $('#estadoProyecto').val(proyecto.estado);
                            $('#fechaInicio').val(proyecto.fechaInicio);
                            $('#fechaFin').val(proyecto.fechaFin);
                        },
                        error: function(xhr) {
                            alert('Proyecto no encontrado');
                        }
                    });
                } else {
                    alert('Ingrese el código del proyecto');
                }
            });

            $('#btnLimpiar').on('click', function() {
                $('#codigoProyecto').val('');
                $('#nombreProyecto').val('');
                $('#estadoProyecto').val('');
                $('#fechaInicio').val('');
                $('#fechaFin').val('');
            });
        });