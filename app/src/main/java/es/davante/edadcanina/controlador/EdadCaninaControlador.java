package es.davante.edadcanina.controlador;

import es.davante.edadcanina.modelo.EdadCaninaModelo;

public class EdadCaninaControlador {

    // El modelo llega por el constructor: el controlador no lo crea, solo lo usa
    private final EdadCaninaModelo modelo;

    public EdadCaninaControlador(EdadCaninaModelo modelo) {
        this.modelo = modelo;
    }

    // Acción del botón Calcular. El resultado y el error los pinta la vista
    // sola cuando el modelo avisa, por eso aquí no hay que hacer nada más
    public void calcular() {
        modelo.calcular();
    }

    // Acción del botón Limpiar
    public void limpiar() {
        modelo.limpiar();
    }
}
