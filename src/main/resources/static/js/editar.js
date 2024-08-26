$(document).ready(function() {

    var usuarioId = sessionStorage.getItem('usuarioId');


    if (!usuarioId) {
        alert('Usuario no encontrado');
        window.location.href = 'home.html';
        return;
    }


    $.ajax({
        url: `/perfil/${usuarioId}`,
        type: 'GET',
        success: function(usuario) {
            $('#nombres').val(usuario.nombres);
            $('#apellidos').val(usuario.apellidos);
            $('#nombre_empresa').val(usuario.nombre_empresa);
            $('#cargo').val(usuario.cargo);
            $('#telefono').val(usuario.telefono);
            $('#email').val(usuario.email);
        },
        error: function(xhr, status, error) {
            alert('Error al obtener datos del usuario: ' + xhr.responseText);
        }
    });


    $('#editForm').on('submit', function(event) {
        event.preventDefault();

        var usuarioActualizado = {
            nombres: $('#nombres').val(),
            apellidos: $('#apellidos').val(),
            nombre_empresa: $('#nombre_empresa').val(),
            cargo: $('#cargo').val(),
            telefono: $('#telefono').val(),
            email: $('#email').val()
        };

        $.ajax({
            url: `/perfil/${usuarioId}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(usuarioActualizado),
            success: function(response) {

                sessionStorage.setItem('nombres', usuarioActualizado.nombres);
                sessionStorage.setItem('apellidos', usuarioActualizado.apellidos);
                sessionStorage.setItem('cargo', usuarioActualizado.cargo);

                alert('Usuario actualizado con éxito');
                window.location.href = 'home.html';
            },
            error: function(xhr, status, error) {
                alert('Error al actualizar el usuario: ' + xhr.responseText);
            }
        });
    });

    $('#btnCancelar').on('click', function() {
            window.location.href = 'home.html';
        });
});
