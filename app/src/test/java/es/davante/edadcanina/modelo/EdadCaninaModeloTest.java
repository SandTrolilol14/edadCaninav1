package es.davante.edadcanina.modelo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class EdadCaninaModeloTest {

    private EdadCaninaModelo modelo;

    // Antes de cada prueba creamos un modelo nuevo para que no se mezclen estados
    @Before
    public void setUp() {
        modelo = new EdadCaninaModelo();
    }

    @Test
    public void edadValida_calculaEdadHumana() {
        modelo.setEdadTexto("6");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(42, modelo.getEdadHumana());
    }

    @Test
    public void limitesDelRango_sonValidos() {
        modelo.setEdadTexto("1");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(7, modelo.getEdadHumana());

        modelo.setEdadTexto("30");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(210, modelo.getEdadHumana());
    }

    @Test
    public void textoVacio_devuelveVacio() {
        modelo.setEdadTexto("");
        assertEquals(Validacion.VACIO, modelo.calcular());
    }

    @Test
    public void soloEspacios_devuelveVacio() {
        // trim() hace que "   " cuente como vacío
        modelo.setEdadTexto("   ");
        assertEquals(Validacion.VACIO, modelo.calcular());
    }

    @Test
    public void texto_devuelveNoNumero() {
        modelo.setEdadTexto("abc");
        assertEquals(Validacion.NO_NUMERO, modelo.calcular());
    }

    @Test
    public void decimal_devuelveNoNumero() {
        modelo.setEdadTexto("3.5");
        assertEquals(Validacion.NO_NUMERO, modelo.calcular());
    }

    @Test
    public void cero_estaFueraDeRango() {
        modelo.setEdadTexto("0");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
    }

    @Test
    public void treintaYUno_estaFueraDeRango() {
        modelo.setEdadTexto("31");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
    }

    @Test
    public void cambiarTexto_borraResultadoAnterior() {
        // Regla de negocio: si el texto cambia, el resultado anterior deja de valer
        modelo.setEdadTexto("6");
        modelo.calcular();
        modelo.setEdadTexto("7");
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
        assertEquals(0, modelo.getEdadHumana());
    }
}