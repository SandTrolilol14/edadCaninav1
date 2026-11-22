package es.davante.edadcanina.controlador;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

public class EdadCaninaControladorTest {

    private EdadCaninaModelo modelo;
    private EdadCaninaControlador controlador;

    @Before
    public void setUp() {
        modelo = new EdadCaninaModelo();
        controlador = new EdadCaninaControlador(modelo);
    }

    @Test
    public void clicCalcular_calculaEnElModelo() {
        modelo.setEdadTexto("6");   // lo que haría el EditText con @={...}
        controlador.calcular();     // lo que haría el botón Calcular
        assertEquals(Validacion.OK, modelo.getValidacion());
        assertEquals(42, modelo.getEdadHumana());
    }

    @Test
    public void clicLimpiar_trasCalcular_borraTodo() {
        modelo.setEdadTexto("6");
        controlador.calcular();
        controlador.limpiar();
        assertEquals("", modelo.getEdadTexto());
        assertEquals(0, modelo.getEdadHumana());
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
    }

    @Test
    public void clicLimpiar_trasErrorVacio_quitaElError() {
        controlador.calcular();
        assertEquals(Validacion.VACIO, modelo.getValidacion());
        controlador.limpiar();
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
    }
}