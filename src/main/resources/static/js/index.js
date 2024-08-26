$(document).ready(function(e) {
    $('#btnIniciar').click(function(event) {

    event.preventDefault();
        var email = $('#txtEmail').val();
        var password = $('#txtPassword').val();

        var usuario = {
            email: email,
            password: password
        };

        $.ajax({
            url: '/api/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(usuario),
            success: function(response) {
                if (response.nombres && response.cargo) {
                    sessionStorage.setItem("nombres", response.nombres);
                    sessionStorage.setItem('apellidos', response.apellidos);
                    sessionStorage.setItem("cargo", response.cargo);
                    sessionStorage.setItem('usuarioId', response.id);


                    window.location.href = 'home.html';
                } else {
                    alert('Correo o contraseña incorrectos');
                }
            },
            error: function(xhr, status, error) {
                console.error('Error en la solicitud:', {
                    status: status,
                    error: error,
                    responseText: xhr.responseText
                });
                alert('Hubo un problema al intentar iniciar sesión. Por favor, inténtalo de nuevo.');
            }
        });
    });
});
