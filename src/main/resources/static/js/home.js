$(document).ready(function() {
    var nombres = sessionStorage.getItem("nombres");
    var apellidos = sessionStorage.getItem("apellidos");
    var cargo = sessionStorage.getItem("cargo");

    if (nombres && apellidos && cargo) {
            var primerNombre = nombres.split(' ')[0];
            var primerApellido = apellidos.split(' ')[0];

            $('#userName').text(primerNombre);
            $('#userApellido').text(primerApellido);
            $('#userPosition').text(cargo);
        } else {
            $('#userName').text("No se encontraron datos");
            $('#userApellido').text( "No se encontraron datos");
            $('#userPosition').text("No se encontraron datos");
        }

    $('#btnNewProject').on('click', function() {
        window.location.href = 'newproject.html';
    });

    $('#btnEditar').on('click', function() {
        window.location.href = 'editar.html';
    });

    $('#btnBusqueda').on('click', function() {
            window.location.href = 'busqueda.html';
    });
});
