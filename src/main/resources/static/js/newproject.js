$(document).ready(function() {
    $('#btnCrear').on('click', function(event) {
        event.preventDefault();

        var usuarioId = sessionStorage.getItem('usuarioId');



        var proyecto = {
            codigo_proyecto: $('#codigo_proyecto').val(),
            nombre: $('#nombre').val(),
            descripcion: $('#descripcion').val(),
            estado: $('#estado').val(),
            fecha_inicio:$('#fecha_inicio').val(),
            fecha_fin: $('#fecha_fin').val(),
            usuario: {
                id: usuarioId
            }
        };

        $.ajax({
            url: '/api/proyectos/crear',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(proyecto),
            success: function(response) {
                alert('Proyecto creado exitosamente');
                $('#crearProyectoForm')[0].reset();  // Limpia el formulario
                window.location.href = "home.html";
            },
            error: function(xhr, status, error) {
                alert('Error al crear el proyecto: ' + xhr.responseText);
            }
        });
    });
});
