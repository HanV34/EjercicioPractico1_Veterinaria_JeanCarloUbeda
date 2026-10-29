// Muestra los toasts que vengan en la página
document.querySelectorAll('.toast').forEach(function (aviso) {
    new bootstrap.Toast(aviso, { delay: 4500 }).show();
});

// Rellena la ventana modal de eliminación con los datos de la fila pulsada
var modalEliminar = document.getElementById('modalEliminar');
if (modalEliminar) {
    modalEliminar.addEventListener('show.bs.modal', function (evento) {
        var boton = evento.relatedTarget;
        document.getElementById('idPorEliminar').value = boton.getAttribute('data-id');
        document.getElementById('nombrePorEliminar').textContent = boton.getAttribute('data-nombre');
    });
}
